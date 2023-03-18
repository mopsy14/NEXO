package mopsy.productions.nucleartech.ModBlocks.entities.machines;

import mopsy.productions.nucleartech.ModBlocks.blocks.multiblocks.smallReactor.ControlRodsBlock;
import mopsy.productions.nucleartech.interfaces.IFluidStorage;
import mopsy.productions.nucleartech.interfaces.IItemRadiation;
import mopsy.productions.nucleartech.registry.ModdedBlockEntities;
import mopsy.productions.nucleartech.registry.ModdedFluids;
import mopsy.productions.nucleartech.screen.smallReactor.SmallReactorScreenHandler;
import mopsy.productions.nucleartech.util.FluidTransactionUtils;
import mopsy.productions.nucleartech.util.NTFluidStorage;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
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
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static mopsy.productions.nucleartech.networking.PacketManager.ADVANCED_FLUID_CHANGE_PACKET;
import static mopsy.productions.nucleartech.util.InvUtils.readInv;
import static mopsy.productions.nucleartech.util.InvUtils.writeInv;

@SuppressWarnings("UnstableApiUsage")
public class SmallReactorEntity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory, IFluidStorage {

    private final Inventory inventory = new SimpleInventory(8);
    public final List<SingleVariantStorage<FluidVariant>> fluidStorages = new ArrayList<>();
    protected final PropertyDelegate propertyDelegate;
    private int coreHeat;
    private int waterHeat;
    public int active;
    private int prevActive = 0;

    public SmallReactorEntity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.SMALL_REACTOR, pos, state);
        fluidStorages.add(new NTFluidStorage(32* FluidConstants.BUCKET ,this, true , 0));
        fluidStorages.add(new NTFluidStorage(32* FluidConstants.BUCKET ,this, true, 1));
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index){
                    case 0: return SmallReactorEntity.this.coreHeat;
                    case 1: return SmallReactorEntity.this.waterHeat;
                    case 2: return SmallReactorEntity.this.active;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0: SmallReactorEntity.this.coreHeat = value; break;
                    case 1: SmallReactorEntity.this.waterHeat = value; break;
                    case 2: SmallReactorEntity.this.active = value; break;
                }
            }

            @Override
            public int size() {
                return 3;
            }
        };
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Small Reactor");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new SmallReactorScreenHandler(syncId, inv, this, this.propertyDelegate, pos);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        writeInv(inventory, nbt);
        nbt.putInt("centrifuge.core_heat", coreHeat);
        nbt.putInt("centrifuge.water_heat", waterHeat);
        nbt.putInt("centrifuge.active", active);
        for (int i = 0; i < fluidStorages.size(); i++) {
            nbt.putLong("fluid_amount_"+i, fluidStorages.get(i).amount);
            nbt.put("fluid_variant_"+i, fluidStorages.get(i).variant.toNbt());
        }
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        readInv(inventory, nbt);
        coreHeat = nbt.getInt("centrifuge.core_heat");
        waterHeat = nbt.getInt("centrifuge.water_heat");
        active = nbt.getInt("centrifuge.active");
        for (int i = 0; i < fluidStorages.size(); i++) {
            fluidStorages.get(i).amount = nbt.getLong("fluid_amount_"+i);
            fluidStorages.get(i).variant = FluidVariant.fromNbt(nbt.getCompound("fluid_variant_"+i));
        }
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, SmallReactorEntity entity) {
        if(world.isClient)return;

        if(entity.active!= entity.prevActive){
            world.setBlockState(blockPos.up(2),
                    world.getBlockState(blockPos.up(2)).with(ControlRodsBlock.ACTIVE, entity.active != 0));
            entity.prevActive = entity.active;
        }

        entity.waterHeat = Math.max(entity.waterHeat-1, 0);

        if(entity.active!=0) {
            entity.coreHeat += Math.round(getHeat(entity.inventory));
            int change = Math.max(0, Math.min(15, Math.max(0, entity.coreHeat - 100)));
            if (entity.coreHeat > entity.waterHeat) {
                entity.waterHeat += change;
                entity.coreHeat -= change;
            }
            if (entity.waterHeat > 100) {
                try (Transaction transaction = Transaction.openOuter()) {
                    long inputAmount = entity.fluidStorages.get(1).insert(FluidVariant.of(ModdedFluids.SUPER_DENSE_STEAM), 81L * (entity.waterHeat - 100), transaction);
                    if (inputAmount > 0) {
                        transaction.commit();
                        entity.waterHeat -= inputAmount / 81;
                    }
                }
            }
            if (getHeat(entity.inventory) < 0.01)
                entity.coreHeat = Math.max(entity.coreHeat - 5, 0);
        }else
            entity.coreHeat = Math.max(entity.coreHeat - 5, 0);
        markDirty(world,blockPos,blockState);

        tryFabricTransactions(entity);

        if(tryTransactions(entity)){
            sendFluidUpdate(entity);
        }
    }
    public static float getHeat(Inventory inv){
        float total = 0;
        for (int i = 4; i < 8; i++) {
            if(inv.getStack(i).getItem() instanceof IItemRadiation item){
                if(item.hasHeat())
                    total += item.getHeat();
            }
        }
        return total;
    }

    private static boolean tryFabricTransactions(SmallReactorEntity entity) {
        boolean didSomething = FluidTransactionUtils.doFabricImportTransaction(entity.inventory, 0, 1, entity.fluidStorages.get(0));
        if(FluidTransactionUtils.doFabricExportTransaction(entity.inventory, 0, 1, entity.fluidStorages.get(0)))
            didSomething = true;
        if(FluidTransactionUtils.doFabricExportTransaction(entity.inventory, 2, 3, entity.fluidStorages.get(1)))
            didSomething = true;

        return didSomething;
    }

    //Slots: 0=FluidInputInput, 1=FluidInputOutput, 2=FluidOutput1Input, 3=FluidOutput1Output
    private static boolean tryTransactions(SmallReactorEntity entity){
        boolean didSomething = FluidTransactionUtils.tryImportFluid(entity.inventory, 0, 1, entity.fluidStorages.get(0));
        if(FluidTransactionUtils.tryExportFluid(entity.inventory, 0, 1, entity.fluidStorages.get(0)))
            didSomething = true;
        if(FluidTransactionUtils.tryExportFluid(entity.inventory, 2, 3, entity.fluidStorages.get(1)))
            didSomething = true;

        return didSomething;
    }

    private static void sendFluidUpdate(SmallReactorEntity entity){
        for (int i = 0; i < entity.fluidStorages.size(); i++) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(entity.pos);
            buf.writeInt(i);
            entity.fluidStorages.get(i).variant.toPacket(buf);
            buf.writeLong(entity.fluidStorages.get(i).amount);
            PlayerLookup.tracking(entity).forEach(player -> ServerPlayNetworking.send(player, ADVANCED_FLUID_CHANGE_PACKET, buf));
        }
    }

    //Inventory code:
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
        return inventory.removeStack(slot,amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return inventory.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.setStack(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] res = new int[inventory.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = i;
        }

        return res;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return slot==0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot==1;
    }

    //IFluidStorage Code:
    @Override
    public FluidVariant getFluidType() {
        return FluidVariant.blank();
    }

    @Override
    public void setFluidType(FluidVariant fluidType) {

    }

    @Override
    public long getFluidAmount() {
        return 0;
    }

    @Override
    public void setFluidAmount(long amount) {

    }

    @Override
    public List<SingleVariantStorage<FluidVariant>> getFluidStorages() {
        return fluidStorages;
    }
}
