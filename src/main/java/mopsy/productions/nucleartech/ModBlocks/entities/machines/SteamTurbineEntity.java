package mopsy.productions.nucleartech.ModBlocks.entities.machines;

import mopsy.productions.nucleartech.ModBlocks.blocks.multiblocks.SmallReactorHatchesBlock;
import mopsy.productions.nucleartech.interfaces.IFluidStorage;
import mopsy.productions.nucleartech.registry.ModdedBlockEntities;
import mopsy.productions.nucleartech.registry.ModdedFluids;
import mopsy.productions.nucleartech.screen.steamTurbine.SteamTurbineScreenHandler;
import mopsy.productions.nucleartech.util.FluidTransactionUtils;
import mopsy.productions.nucleartech.util.InvUtils;
import mopsy.productions.nucleartech.util.NTFluidStorage;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
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

import java.util.ArrayList;
import java.util.List;

import static mopsy.productions.nucleartech.networking.PacketManager.ADVANCED_FLUID_CHANGE_PACKET;
import static mopsy.productions.nucleartech.networking.PacketManager.ENERGY_CHANGE_PACKET;

@SuppressWarnings("UnstableApiUsage")
public class SteamTurbineEntity extends AbstractGeneratorEntity implements ExtendedScreenHandlerFactory, SidedInventory, IFluidStorage {
    public static String ID = "steam_turbine";
    public final Inventory inventory = new SimpleInventory(4);
    public final List<SingleVariantStorage<FluidVariant>> fluidStorages = new ArrayList<>();
    public long previousPower = 0;
    public static final long POWER_CAPACITY = 5000000;
    public static final long POWER_MAX_INSERT = 0;
    public static final long POWER_MAX_EXTRACT = 200000;

    public SteamTurbineEntity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.STEAM_TURBINE, pos, state, POWER_CAPACITY, POWER_MAX_INSERT, POWER_MAX_EXTRACT);
        fluidStorages.add(new NTFluidStorage(16* FluidConstants.BUCKET,this, true , 0));
        fluidStorages.add(new NTFluidStorage(16*FluidConstants.BUCKET,this, false, 1));
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Steam Turbine");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(this.pos);
        buf.writeLong(getPower());
        ServerPlayNetworking.send((ServerPlayerEntity) player, ENERGY_CHANGE_PACKET, buf);
        for (int i = 0; i < fluidStorages.size(); i++){
            buf = PacketByteBufs.create();
            buf.writeBlockPos(pos);
            buf.writeInt(i);
            fluidStorages.get(i).variant.toPacket(buf);
            buf.writeLong(fluidStorages.get(i).amount);
            ServerPlayNetworking.send((ServerPlayerEntity) player, ADVANCED_FLUID_CHANGE_PACKET, buf);
        }
        return new SteamTurbineScreenHandler(syncId, inv, this, pos);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        InvUtils.writeInv(inventory, nbt);
        nbt.putLong(ID+".power", energyStorage.amount);
        for (int i = 0; i < fluidStorages.size(); i++) {
            nbt.putLong("fluid_amount_"+i, fluidStorages.get(i).amount);
            nbt.put("fluid_variant_"+i, fluidStorages.get(i).variant.toNbt());
        }
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        InvUtils.readInv(inventory, nbt);
        energyStorage.amount = nbt.getLong(ID+".power");
        for (int i = 0; i < fluidStorages.size(); i++) {
            fluidStorages.get(i).amount = nbt.getLong("fluid_amount_"+i);
            fluidStorages.get(i).variant = FluidVariant.fromNbt(nbt.getCompound("fluid_variant_"+i));
        }
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, SteamTurbineEntity entity) {
        if(world.isClient)return;

        setFluidStorageToEmpty(entity.fluidStorages.get(0));
        setFluidStorageToEmpty(entity.fluidStorages.get(1));

        if (entity.fluidStorages.get(0).variant.equals(FluidVariant.of(ModdedFluids.SUPER_DENSE_STEAM))&&entity.fluidStorages.get(0).amount>40499&&((entity.fluidStorages.get(1).variant.equals(FluidVariant.of(ModdedFluids.DENSE_STEAM))&&entity.fluidStorages.get(1).getCapacity()-entity.fluidStorages.get(1).amount>40499)||entity.fluidStorages.get(1).variant.equals(FluidVariant.blank()))){
            entity.fluidStorages.get(1).variant = FluidVariant.of(ModdedFluids.DENSE_STEAM);
            entity.fluidStorages.get(0).amount -= 40500;
            entity.fluidStorages.get(1).amount += 40500;
            entity.energyStorage.amount += Math.min(entity.energyStorage.capacity-entity.energyStorage.amount, 100000);
            sendFluidUpdate(entity);
        } else if (entity.fluidStorages.get(0).variant.equals(FluidVariant.of(ModdedFluids.DENSE_STEAM))&&entity.fluidStorages.get(0).amount>40499&&((entity.fluidStorages.get(1).variant.equals(FluidVariant.of(ModdedFluids.STEAM))&&entity.fluidStorages.get(1).getCapacity()-entity.fluidStorages.get(1).amount>40499)||entity.fluidStorages.get(1).variant.equals(FluidVariant.blank()))){
            entity.fluidStorages.get(1).variant = FluidVariant.of(ModdedFluids.STEAM);
            entity.fluidStorages.get(0).amount -= 40500;
            entity.fluidStorages.get(1).amount += 40500;
            entity.energyStorage.amount += Math.min(entity.energyStorage.capacity-entity.energyStorage.amount, 50000);
            sendFluidUpdate(entity);
        } else if (entity.fluidStorages.get(0).variant.equals(FluidVariant.of(ModdedFluids.STEAM))&&entity.fluidStorages.get(0).amount>40499&&((entity.fluidStorages.get(1).variant.equals(FluidVariant.of(Fluids.WATER))&&entity.fluidStorages.get(1).getCapacity()-entity.fluidStorages.get(1).amount>20249)||entity.fluidStorages.get(1).variant.equals(FluidVariant.blank()))){
            entity.fluidStorages.get(1).variant = FluidVariant.of(Fluids.WATER);
            entity.fluidStorages.get(0).amount -= 40500;
            entity.fluidStorages.get(1).amount += 20250;
            entity.energyStorage.amount += Math.min(entity.energyStorage.capacity-entity.energyStorage.amount, 50000);
            sendFluidUpdate(entity);
        }

        markDirty(world,blockPos,blockState);

        tryFabricTransactions(entity);

        if(tryTransactions(entity)){
            sendFluidUpdate(entity);
        }

        tryExportPower(entity);

        if(entity.energyStorage.amount!=entity.previousPower){
            entity.previousPower = entity.energyStorage.amount;
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(blockPos);
            buf.writeLong(entity.getPower());
            PlayerLookup.tracking(entity).forEach(player -> ServerPlayNetworking.send(player, ENERGY_CHANGE_PACKET, buf));
        }
    }

    public SingleVariantStorage getFluidStorageFromDirection(Direction direction){
        if(direction==Direction.DOWN||direction==Direction.UP) return null;
        if(this.getCachedState().get(SmallReactorHatchesBlock.FACING)==direction.getOpposite()){
            return null;
        }
        if(this.getCachedState().get(SmallReactorHatchesBlock.FACING)==direction.getOpposite().rotateYClockwise()){
            return fluidStorages.get(0);
        }
        if(this.getCachedState().get(SmallReactorHatchesBlock.FACING)==direction){
            return null;
        }
        if(this.getCachedState().get(SmallReactorHatchesBlock.FACING)==direction.rotateYClockwise()){
            return fluidStorages.get(1);
        }
        return null;
    }

    private static void setFluidStorageToEmpty(SingleVariantStorage storage){
        if(storage.amount==0){
            storage.variant = FluidVariant.blank();
        } else if (storage.variant.equals(FluidVariant.blank())) {
            storage.amount = 0;
        }
    }

    private static void sendFluidUpdate(SteamTurbineEntity entity){
        for (int i = 0; i < entity.fluidStorages.size(); i++){
            var buf = PacketByteBufs.create();
            buf.writeBlockPos(entity.pos);
            buf.writeInt(i);
            entity.fluidStorages.get(i).variant.toPacket(buf);
            buf.writeLong(entity.fluidStorages.get(i).amount);
            PlayerLookup.tracking(entity).forEach(player -> ServerPlayNetworking.send(player, ADVANCED_FLUID_CHANGE_PACKET, buf));
        }
    }

    private static boolean tryFabricTransactions(SteamTurbineEntity entity) {
        boolean didSomething= FluidTransactionUtils.doFabricImportTransaction(entity.inventory, 0, 1, entity.fluidStorages.get(0));
        if(FluidTransactionUtils.doFabricExportTransaction(entity.inventory, 0, 1, entity.fluidStorages.get(0)))
            didSomething = true;
        if(FluidTransactionUtils.doFabricExportTransaction(entity.inventory, 2, 3, entity.fluidStorages.get(1)))
            didSomething = true;

        return didSomething;
    }
    //Slots: 0=FluidInputInput, 1=FluidInputOutput, 2=FluidOutput1Input, 3=FluidOutput1Output, 4=FluidOutput2Input, 5=FluidOutput2Output,
    private static boolean tryTransactions(SteamTurbineEntity entity){
        boolean didSomething = FluidTransactionUtils.tryImportFluid(entity.inventory, 0, 1, entity.fluidStorages.get(0));
        if(FluidTransactionUtils.tryExportFluid(entity.inventory, 0, 1, entity.fluidStorages.get(0)))
            didSomething = true;
        if(FluidTransactionUtils.tryExportFluid(entity.inventory, 2, 3, entity.fluidStorages.get(1)))
            didSomething = true;

        return didSomething;
    }

    @Override
    public long getPower() {
        return energyStorage.amount;
    }

    @Override
    public void setPower(long power) {
        energyStorage.amount = power;
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
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Override
    public FluidVariant getFluidType() {
        return null;
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
