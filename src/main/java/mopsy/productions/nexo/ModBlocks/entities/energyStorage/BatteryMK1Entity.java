package mopsy.productions.nexo.ModBlocks.entities.energyStorage;

import mopsy.productions.nexo.interfaces.IEnergyStorage;
import mopsy.productions.nexo.networking.payloads.EnergyChangePayload;
import mopsy.productions.nexo.registry.ModdedBlockEntities;
import mopsy.productions.nexo.screen.DefaultSHPayload;
import mopsy.productions.nexo.screen.battery.BatteryMK1ScreenHandler;
import mopsy.productions.nexo.util.InvUtils;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;
import team.reborn.energy.api.base.SimpleEnergyStorage;


public class BatteryMK1Entity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory, IEnergyStorage {

    private final Inventory inventory = new SimpleInventory(2);
    public long previousPower = 0;
    public static final long POWER_CAPACITY = 10000;
    public static final long POWER_MAX_INSERT = 10;
    public static final long POWER_MAX_EXTRACT = 20;
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(POWER_CAPACITY, POWER_MAX_INSERT, POWER_MAX_EXTRACT) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };
    public final EnergyStorage outputEnergyStorage = new EnergyStorage() {
        @Override
        public long insert(long maxAmount, TransactionContext transaction) {
            return 0;
        }

        @Override
        public boolean supportsInsertion() {
            return false;
        }

        @Override
        public long extract(long maxAmount, TransactionContext transaction) {
            return energyStorage.extract(maxAmount, transaction);
        }

        @Override
        public long getAmount() {
            return energyStorage.getAmount();
        }

        @Override
        public long getCapacity() {
            return energyStorage.getCapacity();
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
        ServerPlayNetworking.send((ServerPlayerEntity) player, new EnergyChangePayload(pos,getPower()));
        return new BatteryMK1ScreenHandler(syncId, inv, this, pos);
    }

    @Override
    public Object getScreenOpeningData(ServerPlayerEntity player) {
        return new DefaultSHPayload(pos);
    }


    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries){
        super.writeNbt(nbt,registries);
        InvUtils.writeInv(registries, inventory,nbt);
        nbt.putLong("power", energyStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries){
        super.readNbt(nbt,registries);
        InvUtils.readInv(registries, inventory,nbt);
        energyStorage.amount = nbt.getLong("power");
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, BatteryMK1Entity entity) {
        if(world.isClient)return;

        long importLeft = POWER_MAX_INSERT;
        long exportLeft = POWER_MAX_EXTRACT;

        //Try item energy transactions
        importLeft -= tryItemEnergyCollectTransaction(entity.inventory, 0, entity.energyStorage, importLeft);
        exportLeft -= tryItemEnergyExportTransaction(entity.inventory, 1, entity.energyStorage, exportLeft);

        //Try block export transaction
        exportLeft -= tryExportPowerToBlock(entity, exportLeft, blockState, blockPos, world);

        if(entity.energyStorage.amount!=entity.previousPower){
            entity.previousPower = entity.energyStorage.amount;
            PlayerLookup.tracking(entity).forEach(player -> ServerPlayNetworking.send(player, new EnergyChangePayload(blockPos,entity.getPower())));
        }
    }

    private static long tryItemEnergyCollectTransaction(Inventory inventory, int slotIndex, EnergyStorage storage, long limit){
        try (Transaction transaction = Transaction.openOuter()) {
            long moved = EnergyStorageUtil.move(
                    ContainerItemContext.ofSingleSlot(InventoryStorage.of(inventory,null).getSlot(slotIndex)).find(EnergyStorage.ITEM),
                    storage,
                    limit,
                    transaction);
            if (moved > 0) {
                transaction.commit();
                return moved;
            }
        }
        return 0;
    }
    private static long tryItemEnergyExportTransaction(Inventory inventory, int slotIndex, EnergyStorage storage, long limit){
        try (Transaction transaction = Transaction.openOuter()) {
            long moved = EnergyStorageUtil.move(
                    storage,
                    ContainerItemContext.ofSingleSlot(InventoryStorage.of(inventory,null).getSlot(slotIndex)).find(EnergyStorage.ITEM),
                    limit,
                    transaction);
            if (moved > 0) {
                transaction.commit();
                return moved;
            }
        }
        return 0;
    }

    protected static long tryExportPowerToBlock(BatteryMK1Entity entity, long maxMove, BlockState state, BlockPos pos, World world) {
        if(entity.energyStorage.amount==0) return 0;


        EnergyStorage targetStorage = EnergyStorage.SIDED.find(world, pos.offset(state.get(Properties.FACING)), state.get(Properties.FACING));

        if(targetStorage==null || !targetStorage.supportsInsertion()) return 0;

        try(Transaction transaction = Transaction.openOuter()){
            long moved = EnergyStorageUtil.move(entity.energyStorage,targetStorage, maxMove, transaction);
            if(moved > 0 && moved <= maxMove){
                transaction.commit();
                return moved;
            }
            transaction.abort();
        }
        return 0;
    }

    public EnergyStorage getEnergyStorageFromDirection(Direction direction){
        if(direction==getCachedState().get(Properties.FACING).getOpposite())
            return outputEnergyStorage;
        return energyStorage;
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
