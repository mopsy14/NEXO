package mopsy.productions.nexo.ModBlocks.entities.energyStorage;

import mopsy.productions.nexo.interfaces.IEnergyStorage;
import mopsy.productions.nexo.registry.ModdedBlockEntities;
import mopsy.productions.nexo.screen.battery.BatteryMK1ScreenHandler;
import mopsy.productions.nexo.util.InvUtils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.ArrayList;

import static mopsy.productions.nexo.networking.PacketManager.ENERGY_CHANGE_PACKET;

@SuppressWarnings("UnstableApiUsage")
public class BatteryMK1Entity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory, IEnergyStorage {

    private final Inventory inventory = new SimpleInventory(2);
    public long previousPower = 0;
    public static final long POWER_CAPACITY = 1000;
    public static final long POWER_MAX_INSERT = 10;
    public static final long POWER_MAX_EXTRACT = 20;
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(POWER_CAPACITY, POWER_MAX_INSERT, POWER_MAX_EXTRACT) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    public BatteryMK1Entity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.BATTERY_MK1, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Battery MK1");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(this.pos);
        buf.writeLong(getPower());
        ServerPlayNetworking.send((ServerPlayerEntity) player, ENERGY_CHANGE_PACKET, buf);
        return new BatteryMK1ScreenHandler(syncId, inv, this, pos);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }


    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        InvUtils.writeInv(inventory,nbt);
        nbt.putLong("power", energyStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        InvUtils.readInv(inventory,nbt);
        energyStorage.amount = nbt.getLong("power");
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, BatteryMK1Entity entity) {
        if(world.isClient)return;

        long importLeft = POWER_MAX_INSERT;
        long exportLeft = POWER_MAX_EXTRACT;

        //Try item energy transactions
        importLeft -= tryItemEnergyCollectTransaction(entity.getStack(0), entity.energyStorage, importLeft);
        exportLeft -= tryItemEnergyExportTransaction(entity.getStack(1), entity.energyStorage, exportLeft);

        //Try block export transactions
        exportLeft -= tryExportPowerToBlocks(entity,exportLeft);

        if(entity.energyStorage.amount!=entity.previousPower){
            entity.previousPower = entity.energyStorage.amount;
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(blockPos);
            buf.writeLong(entity.getPower());
            PlayerLookup.tracking(entity).forEach(player -> ServerPlayNetworking.send(player, ENERGY_CHANGE_PACKET, buf));
        }
    }

    private static long tryItemEnergyCollectTransaction(ItemStack stack, EnergyStorage storage, long limit){
        EnergyStorage itemStorage = EnergyStorage.ITEM.find(stack, null);
        if(itemStorage!=null){
            try (Transaction transaction = Transaction.openOuter()) {
                long moved = EnergyStorageUtil.move(itemStorage,storage,limit,transaction);
                if(moved>0){
                    transaction.commit();
                    return moved;
                }
            }
        }
        return 0;
    }
    private static long tryItemEnergyExportTransaction(ItemStack stack, EnergyStorage storage, long limit){
        EnergyStorage itemStorage = EnergyStorage.ITEM.find(stack, null);
        if(itemStorage!=null){
            try (Transaction transaction = Transaction.openOuter()) {
                long moved = EnergyStorageUtil.move(storage,itemStorage,limit,transaction);
                if(moved>0){
                    transaction.commit();
                    return moved;
                }
            }
        }
        return 0;
    }

    protected static long tryExportPowerToBlocks(BatteryMK1Entity entity, long maxMove) {
        if(entity.energyStorage.amount==0) return 0;

        ArrayList<EnergyStorage> adjacentStorages = new ArrayList<>();
        Direction direction = Direction.NORTH;
        for (int i = 0; i < 6; i++) {
            EnergyStorage storage;
            direction = direction.rotateYClockwise();
            if (i < 4) {
                storage = EnergyStorage.SIDED.find(entity.world, entity.pos.offset(direction), direction.getOpposite());
            } else if (i == 4) {
                storage = EnergyStorage.SIDED.find(entity.world, entity.pos.offset(Direction.UP), Direction.DOWN);
            } else {
                storage = EnergyStorage.SIDED.find(entity.world, entity.pos.offset(Direction.DOWN), Direction.UP);
            }
            if (storage != null) adjacentStorages.add(storage);
        }
        for (int i = 0; i < adjacentStorages.size();) {
            if(!adjacentStorages.get(i).supportsInsertion()||adjacentStorages.get(i).getCapacity()-adjacentStorages.get(i).getAmount()==0)
                adjacentStorages.remove(adjacentStorages.get(i));
            else i++;
        }
        return doExportPowerTransactions(adjacentStorages, entity, maxMove);
    }
    private static long doExportPowerTransactions(ArrayList<EnergyStorage> adjacentStorages, BatteryMK1Entity entity, long maxMove){
        long totalMoved = 0;
        while (totalMoved<maxMove){
            if(adjacentStorages.isEmpty()) return totalMoved;
            int divider = adjacentStorages.size();
            int maxTransaction = Math.round(((maxMove-totalMoved)/(float)divider));
            ArrayList<EnergyStorage> toRemove = new ArrayList<>();
            for(EnergyStorage storage : adjacentStorages) {
                long amountMoved = EnergyStorageUtil.move(
                        entity.energyStorage,
                        storage,
                        maxTransaction==0&&maxTransaction<(maxMove-totalMoved)/(float)divider?1:maxTransaction,
                        null
                );
                totalMoved +=amountMoved;
                if(amountMoved<maxTransaction)
                    toRemove.add(storage);
                if(totalMoved>=maxMove)
                    return totalMoved;
            }
            for(EnergyStorage storage : toRemove)
                adjacentStorages.remove(storage);
        }
        return totalMoved;
    }

    @Override
    public long getPower() {
        return energyStorage.amount;
    }

    @Override
    public void setPower(long power) {
        energyStorage.amount = power;
    }

    //Inventory Code:
    @Override
    public int[] getAvailableSlots(Direction side) {
        switch(side){
            case UP -> {return new int[]{0};}
            default -> {return new int[]{1};}
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return inventory.removeStack(slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return inventory.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.setStack(slot,stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    @Override
    public void clear() {
        inventory.clear();
    }
}
