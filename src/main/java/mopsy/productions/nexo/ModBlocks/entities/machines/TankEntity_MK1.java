package mopsy.productions.nexo.ModBlocks.entities.machines;

import mopsy.productions.nexo.interfaces.IFluidStorage;
import mopsy.productions.nexo.registry.ModdedBlockEntities;
import mopsy.productions.nexo.registry.ModdedFluids;
import mopsy.productions.nexo.registry.ModdedItems;
import mopsy.productions.nexo.screen.tank.TankScreenHandler_MK1;
import mopsy.productions.nexo.util.FluidTransactionUtils;
import mopsy.productions.nexo.util.FluidUtils;
import mopsy.productions.nexo.util.NTFluidStorage;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
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
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static mopsy.productions.nexo.networking.PacketManager.FLUID_CHANGE_PACKET;
import static mopsy.productions.nexo.util.InvUtils.readInv;
import static mopsy.productions.nexo.util.InvUtils.writeInv;

@SuppressWarnings("UnstableApiUsage")
public class TankEntity_MK1 extends BlockEntity implements ExtendedScreenHandlerFactory, IFluidStorage, SidedInventory {

    public final Inventory inventory = new SimpleInventory(2);
    public static final long MAX_CAPACITY = 8 * FluidConstants.BUCKET;
    public final SingleVariantStorage<FluidVariant> fluidStorage = new NTFluidStorage(MAX_CAPACITY, this, true);

    public TankEntity_MK1(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.TANK_MK1, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Tank");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(this.pos);
        fluidStorage.variant.toPacket(buf);
        buf.writeLong(fluidStorage.amount);
        ServerPlayNetworking.send((ServerPlayerEntity) player, FLUID_CHANGE_PACKET, buf);
        return new TankScreenHandler_MK1(syncId, inv, this.inventory, pos);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public void writeNbt(NbtCompound nbt){
        writeInv(inventory, nbt);
        nbt.putLong("fluid_amount", fluidStorage.amount);
        nbt.put("fluid_variant", fluidStorage.variant.toNbt());
        super.writeNbt(nbt);
    }
    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        readInv(inventory, nbt);
        fluidStorage.amount = nbt.getLong("fluid_amount");
        fluidStorage.variant = FluidVariant.fromNbt(nbt.getCompound("fluid_variant"));
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, TankEntity_MK1 tankEntity) {
        if (world.isClient) return;

        try(Transaction transaction = Transaction.openOuter()) {
            if (tankEntity.canTransfer()) {
                long moved = StorageUtil.move(
                        FluidUtils.getItemFluidStorage(tankEntity.inventory, 0, 1),
                        tankEntity.fluidStorage,
                        predicate -> true,
                        Long.MAX_VALUE,
                        transaction
                );
                if(moved == 0){
                    moved = StorageUtil.move(
                            tankEntity.fluidStorage,
                            FluidUtils.getItemFluidStorage(tankEntity.inventory, 0, 1),
                            fv -> true,
                            Long.MAX_VALUE,
                            transaction
                    );
                }
                if (moved > 0) {
                    transaction.commit();
                    markDirty(world, blockPos, blockState);
                }
            }
        }
        if(tryExchangeFluid(tankEntity)){
            markDirty(world,blockPos,blockState);

            var buf = PacketByteBufs.create();
            buf.writeBlockPos(blockPos);
            tankEntity.fluidStorage.variant.toPacket(buf);
            buf.writeLong(tankEntity.fluidStorage.amount);
            world.getPlayers().forEach(player-> {
                ServerPlayNetworking.send((ServerPlayerEntity) player, FLUID_CHANGE_PACKET, buf);
            });
        }
    }

    private static boolean tryExchangeFluid(TankEntity_MK1 tankEntity) {
        ItemStack inputStack = tankEntity.inventory.getStack(0);
        ItemStack outputStack = tankEntity.inventory.getStack(1);
        if(outputStack.isEmpty()){
            if(FluidUtils.isTank(inputStack.getItem())) {
                if(FluidTransactionUtils.tryImportFluid(tankEntity.inventory, 0, 1, tankEntity.fluidStorage))
                    return true;
                return FluidTransactionUtils.tryExportFluid(tankEntity.inventory, 0, 1, tankEntity.fluidStorage);
            }
        }
        if (outputStack.isEmpty()){
            if(inputStack.getItem().equals(ModdedItems.Items.get("empty_geiger_tube"))){
                if(tankEntity.fluidStorage.variant.equals(FluidVariant.of(ModdedFluids.stillFluids.get("nitrogen")))){
                    if(tankEntity.fluidStorage.amount>8100){
                        tankEntity.fluidStorage.amount -= 8100;
                        tankEntity.inventory.setStack(0, ItemStack.EMPTY);
                        tankEntity.inventory.setStack(1, new ItemStack(ModdedItems.Items.get("filled_geiger_tube")));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canTransfer(){
        if(inventory.getStack(0).isEmpty())
            return false;
        return inventory.getStack(1).getCount() < inventory.getStack(1).getMaxCount();
    }

    public SingleVariantStorage<FluidVariant> getFluidStorageFromDirection(Direction direction){
        if(direction==Direction.DOWN || direction==Direction.UP)
            return fluidStorage;
        return null;
    }


    @Override
    public FluidVariant getFluidType() {
        return fluidStorage.variant;
    }

    @Override
    public void setFluidType(FluidVariant fluidType) {
        fluidStorage.variant = fluidType;
    }

    @Override
    public long getFluidAmount() {
        return fluidStorage.amount;
    }

    @Override
    public void setFluidAmount(long amount) {
        fluidStorage.amount = amount;
    }

    @Override
    public List<SingleVariantStorage<FluidVariant>> getFluidStorages() {
        return null;
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
}
