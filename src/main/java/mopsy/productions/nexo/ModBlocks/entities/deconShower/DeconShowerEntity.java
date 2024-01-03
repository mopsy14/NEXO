package mopsy.productions.nexo.ModBlocks.entities.deconShower;

import mopsy.productions.nexo.interfaces.IFluidStorage;
import mopsy.productions.nexo.mechanics.radiation.RadiationHelper;
import mopsy.productions.nexo.registry.ModdedBlockEntities;
import mopsy.productions.nexo.screen.deconShower.DeconShowerScreenHandler;
import mopsy.productions.nexo.util.FluidTransactionUtils;
import mopsy.productions.nexo.util.NTFluidStorage;
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
import net.minecraft.fluid.Fluids;
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
public class DeconShowerEntity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory, IFluidStorage {

    private final Inventory inventory = new SimpleInventory(2);
    public final SingleVariantStorage<FluidVariant> fluidStorage = new NTFluidStorage(16* FluidConstants.BUCKET ,this, true);

    public DeconShowerEntity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.DECON_SHOWER, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Decontamination Shower");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        fluidStorage.variant.toPacket(buf);
        buf.writeLong(fluidStorage.amount);
        ServerPlayNetworking.send((ServerPlayerEntity) player, FLUID_CHANGE_PACKET, buf);

        return new DeconShowerScreenHandler(syncId, inv, this, pos);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        writeInv(inventory, nbt);
        nbt.putLong("fluid_amount", fluidStorage.amount);
        nbt.put("fluid_variant", fluidStorage.variant.toNbt());
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        readInv(inventory, nbt);
        fluidStorage.amount = nbt.getLong("fluid_amount");
        fluidStorage.variant = FluidVariant.fromNbt(nbt.getCompound("fluid_variant"));
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, DeconShowerEntity entity) {
        if(world.isClient)return;
        if(entity.fluidStorage.amount>=81 && world.getBlockEntity(blockPos.add(0,-3,0)) instanceof DeconShowerEntity drainEntity){
            if(drainEntity.fluidStorage.amount+81<=drainEntity.fluidStorage.getCapacity()){
                try(Transaction transaction = Transaction.openOuter()){
                    if(entity.fluidStorage.extract(FluidVariant.of(Fluids.WATER),81,transaction) == 81) {
                        if (drainEntity.fluidStorage.insert(FluidVariant.of(Fluids.WATER), 81, transaction) == 81) {
                            PlayerEntity player = world.getClosestPlayer(blockPos.getX() + 0.5f, blockPos.getY() - 2.0f, blockPos.getZ() + 0.5f, 0.5f, false);
                            if (player instanceof ServerPlayerEntity serverPlayer) {
                                if (RadiationHelper.getPlayerRadiation(serverPlayer) > 0) {
                                    RadiationHelper.addPlayerRadiation(serverPlayer, -1.0f);
                                    transaction.commit();
                                } else
                                    transaction.abort();
                            } else
                                transaction.abort();
                        } else
                            transaction.abort();
                    } else
                        transaction.abort();
                }
            }
        }

        markDirty(world,blockPos,blockState);

        if(tryFabricTransactions(entity)){

        }
        if(tryTransactions(entity)){
            sendFluidUpdate(entity);
        }
    }

    private static boolean tryFabricTransactions(DeconShowerEntity entity) {
        boolean didSomething = FluidTransactionUtils.doFabricImportTransaction(entity.inventory, 0, 1, entity.fluidStorage);
        if(FluidTransactionUtils.doFabricExportTransaction(entity.inventory, 0, 1, entity.fluidStorage))
            didSomething = true;

        return didSomething;
    }

    private static boolean tryTransactions(DeconShowerEntity entity){
        boolean didSomething = FluidTransactionUtils.tryImportFluid(entity.inventory, 0, 1, entity.fluidStorage);
        if(FluidTransactionUtils.tryExportFluid(entity.inventory, 0, 1, entity.fluidStorage))
            didSomething = true;

        return didSomething;
    }

    private static void sendFluidUpdate(DeconShowerEntity entity){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(entity.pos);
        entity.fluidStorage.variant.toPacket(buf);
        buf.writeLong(entity.fluidStorage.amount);
        PlayerLookup.tracking(entity).forEach(player -> ServerPlayNetworking.send(player, FLUID_CHANGE_PACKET, buf));
    }

    public SingleVariantStorage getFluidStorageFromDirection(Direction direction){
        return fluidStorage;
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
}
