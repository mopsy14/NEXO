package mopsy.productions.nucleartech.ModBlocks.entities.machines;

import mopsy.productions.nucleartech.ModBlocks.blocks.machines.ElectrolyzerBlock;
import mopsy.productions.nucleartech.enums.SlotIO;
import mopsy.productions.nucleartech.interfaces.IBlockEntityRecipeCompat;
import mopsy.productions.nucleartech.interfaces.IEnergyStorage;
import mopsy.productions.nucleartech.interfaces.IFluidStorage;
import mopsy.productions.nucleartech.recipes.ElectrolyzerRecipe;
import mopsy.productions.nucleartech.registry.ModdedBlockEntities;
import mopsy.productions.nucleartech.screen.electrolyzer.ElectrolyzerScreenHandler;
import mopsy.productions.nucleartech.util.FluidTransactionUtils;
import mopsy.productions.nucleartech.util.NTFluidStorage;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
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
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.ArrayList;
import java.util.List;

import static mopsy.productions.nucleartech.networking.PacketManager.ADVANCED_FLUID_CHANGE_PACKET;
import static mopsy.productions.nucleartech.networking.PacketManager.ENERGY_CHANGE_PACKET;
import static mopsy.productions.nucleartech.util.InvUtils.readInv;
import static mopsy.productions.nucleartech.util.InvUtils.writeInv;

@SuppressWarnings("UnstableApiUsage")
public class ElectrolyzerEntity extends BlockEntity implements ExtendedScreenHandlerFactory, IEnergyStorage, IFluidStorage, IBlockEntityRecipeCompat, Inventory{

    public final Inventory inventory = new SimpleInventory(6);
    public final List<SingleVariantStorage<FluidVariant>> fluidStorages = new ArrayList<>();
    public long previousPower = 0;
    public static final long POWER_CAPACITY = 10000;
    public static final long POWER_MAX_INSERT = 50;
    public static final long POWER_MAX_EXTRACT = 0;
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(POWER_CAPACITY, POWER_MAX_INSERT, POWER_MAX_EXTRACT) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    public ElectrolyzerEntity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.ELECTROLYZER, pos, state);
        fluidStorages.add(new NTFluidStorage(16*FluidConstants.BUCKET,this, true , 0));
        fluidStorages.add(new NTFluidStorage(16*FluidConstants.BUCKET,this, false, 1));
        fluidStorages.add(new NTFluidStorage(16*FluidConstants.BUCKET,this, false, 2));
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Electrolyzer");
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
        return new ElectrolyzerScreenHandler(syncId, inv, this.inventory, pos);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        writeInv(inventory, nbt);
        nbt.putLong("electrolyzer.power", energyStorage.amount);
        for (int i = 0; i < fluidStorages.size(); i++) {
            nbt.putLong("fluid_amount_"+i, fluidStorages.get(i).amount);
            nbt.put("fluid_variant_"+i, fluidStorages.get(i).variant.toNbt());
        }
    }
    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        readInv(inventory, nbt);
        energyStorage.amount = nbt.getLong("electrolyzer.power");
        for (int i = 0; i < fluidStorages.size(); i++) {
            fluidStorages.get(i).amount = nbt.getLong("fluid_amount_"+i);
            fluidStorages.get(i).variant = FluidVariant.fromNbt(nbt.getCompound("fluid_variant_"+i));
        }
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, ElectrolyzerEntity entity) {
        if(world.isClient)return;

        ElectrolyzerRecipe recipe = getFirstRecipeMatch(entity);
        if(recipe!=null){
            if(entity.energyStorage.amount >= recipe.getRequiredPower()){
                entity.energyStorage.amount -= recipe.getRequiredPower();
                recipe.craft(entity,true,true);
                markDirty(world,blockPos,blockState);

                for (int i = 0; i < entity.fluidStorages.size(); i++){
                    var buf = PacketByteBufs.create();
                    buf.writeBlockPos(blockPos);
                    buf.writeInt(i);
                    entity.fluidStorages.get(i).variant.toPacket(buf);
                    buf.writeLong(entity.fluidStorages.get(i).amount);
                    PlayerLookup.tracking(entity).forEach(player -> ServerPlayNetworking.send(player, ADVANCED_FLUID_CHANGE_PACKET, buf));
                }
            }
        }

        if(tryFabricTransactions(entity)){
            markDirty(world,blockPos,blockState);
        }

        if(tryTransactions(entity)){
            markDirty(world,blockPos,blockState);

            for (int i = 0; i < entity.fluidStorages.size(); i++){
                var buf = PacketByteBufs.create();
                buf.writeBlockPos(blockPos);
                buf.writeInt(i);
                entity.fluidStorages.get(i).variant.toPacket(buf);
                buf.writeLong(entity.fluidStorages.get(i).amount);
                PlayerLookup.tracking(entity).forEach(player -> ServerPlayNetworking.send(player, ADVANCED_FLUID_CHANGE_PACKET, buf));
            }
        }

        //Send PowerChange package
        if(entity.energyStorage.amount!=entity.previousPower){
            entity.previousPower = entity.energyStorage.amount;
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(blockPos);
            buf.writeLong(entity.getPower());
            PlayerLookup.tracking(entity).forEach(player -> ServerPlayNetworking.send(player, ENERGY_CHANGE_PACKET, buf));
        }
    }

    private static ElectrolyzerRecipe getFirstRecipeMatch(ElectrolyzerEntity entity){
        for(ElectrolyzerRecipe electrolyzerRecipe : entity.getWorld().getRecipeManager().listAllOfType(ElectrolyzerRecipe.Type.INSTANCE)){
            if(electrolyzerRecipe.hasRecipe(entity)) {
                return electrolyzerRecipe;
            }
        }
        return null;
    }

    private static boolean tryFabricTransactions(ElectrolyzerEntity entity) {
        boolean didSomething = FluidTransactionUtils.doFabricImportTransaction(entity.inventory, 0, 1, entity.fluidStorages.get(0));
        if(FluidTransactionUtils.doFabricExportTransaction(entity.inventory, 0, 1, entity.fluidStorages.get(0)))
            didSomething = true;
        if(FluidTransactionUtils.doFabricExportTransaction(entity.inventory, 2, 3, entity.fluidStorages.get(1)))
            didSomething = true;
        if(FluidTransactionUtils.doFabricExportTransaction(entity.inventory, 4, 5, entity.fluidStorages.get(2)))
            didSomething = true;

        return didSomething;
    }

    //Slots: 0=FluidInputInput, 1=FluidInputOutput, 2=FluidOutput1Input, 3=FluidOutput1Output, 4=FluidOutput2Input, 5=FluidOutput2Output,
    private static boolean tryTransactions(ElectrolyzerEntity entity){
        boolean didSomething = FluidTransactionUtils.tryImportFluid(entity.inventory, 0, 1, entity.fluidStorages.get(0));
        if(FluidTransactionUtils.tryExportFluid(entity.inventory, 0, 1, entity.fluidStorages.get(0)))
            didSomething = true;
        if(FluidTransactionUtils.tryExportFluid(entity.inventory, 2, 3, entity.fluidStorages.get(1)))
            didSomething = true;
        if(FluidTransactionUtils.tryExportFluid(entity.inventory, 4, 5, entity.fluidStorages.get(2)))
            didSomething = true;

        return didSomething;
    }

    public SingleVariantStorage getFluidStorageFromDirection(Direction direction){
        if(direction==Direction.DOWN)
            return fluidStorages.get(0);
        if(this.getCachedState().get(ElectrolyzerBlock.FACING)==direction.getOpposite().rotateYClockwise())
            return fluidStorages.get(1);
        if(this.getCachedState().get(ElectrolyzerBlock.FACING)==direction.rotateYClockwise())
            return fluidStorages.get(2);
        return null;
    }

    @Override
    public long getPower() {
        return energyStorage.amount;
    }

    @Override
    public void setPower(long power) {
        energyStorage.amount = power;
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

    @Override
    public SlotIO[] getFluidSlotIOs() {
        return new SlotIO[]{SlotIO.INPUT,SlotIO.OUTPUT,SlotIO.OUTPUT};
    }

    @Override
    public SlotIO[] getItemSlotIOs() {
        return new SlotIO[]{SlotIO.NONE,SlotIO.NONE,SlotIO.NONE,SlotIO.NONE,SlotIO.NONE,SlotIO.NONE};
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