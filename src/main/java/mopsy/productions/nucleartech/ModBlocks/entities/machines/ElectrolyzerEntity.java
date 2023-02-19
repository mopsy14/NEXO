package mopsy.productions.nucleartech.ModBlocks.entities.machines;

import mopsy.productions.nucleartech.interfaces.IEnergyStorage;
import mopsy.productions.nucleartech.interfaces.IFluidStorage;
import mopsy.productions.nucleartech.registry.ModdedBlockEntities;
import mopsy.productions.nucleartech.registry.ModdedFluids;
import mopsy.productions.nucleartech.screen.electrolyzer.ElectrolyzerScreenHandler;
import mopsy.productions.nucleartech.util.FluidTransactionUtils;
import mopsy.productions.nucleartech.util.NCFluidStorage;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.ArrayList;
import java.util.List;

import static mopsy.productions.nucleartech.networking.PacketManager.ADVANCED_FLUID_CHANGE_PACKET;
import static mopsy.productions.nucleartech.networking.PacketManager.ENERGY_CHANGE_PACKET;
import static mopsy.productions.nucleartech.util.InvUtils.readInv;
import static mopsy.productions.nucleartech.util.InvUtils.writeInv;

public class ElectrolyzerEntity extends BlockEntity implements ExtendedScreenHandlerFactory, IEnergyStorage, IFluidStorage {

    public final Inventory inventory = new SimpleInventory(6);
    public final List<SingleVariantStorage<FluidVariant>> fluidStorages = new ArrayList<>();
    public long previousPower = 0;
    public static final long POWER_CAPACITY = 10000;
    public static final long POWER_MAX_INSERT = 50;
    public static final long POWER_MAX_EXTRACT = 0;
    public SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(POWER_CAPACITY, POWER_MAX_INSERT, POWER_MAX_EXTRACT) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };
    public static ElectrolyzerRecipe[] recipes = {
            new ElectrolyzerRecipe(FluidVariant.of(Fluids.WATER), 4050, FluidVariant.of(ModdedFluids.HYDROGEN), 2025, FluidVariant.of(ModdedFluids.OXYGEN), 2025, 25)
    };

    public ElectrolyzerEntity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.ELECTROLYZER, pos, state);
        fluidStorages.add(new NCFluidStorage(16*FluidConstants.BUCKET,this, true , 0));
        fluidStorages.add(new NCFluidStorage(16*FluidConstants.BUCKET,this, false, 1));
        fluidStorages.add(new NCFluidStorage(16*FluidConstants.BUCKET,this, false, 2));
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

        if(tryProduce(entity)){
            markDirty(world,blockPos,blockState);

            for (int i = 0; i < entity.fluidStorages.size(); i++){
                var buf = PacketByteBufs.create();
                buf.writeBlockPos(blockPos);
                buf.writeInt(i);
                entity.fluidStorages.get(i).variant.toPacket(buf);
                buf.writeLong(entity.fluidStorages.get(i).amount);
                world.getPlayers().forEach(player -> {
                    ServerPlayNetworking.send((ServerPlayerEntity) player, ADVANCED_FLUID_CHANGE_PACKET, buf);
                });
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
                world.getPlayers().forEach(player -> {
                    ServerPlayNetworking.send((ServerPlayerEntity) player, ADVANCED_FLUID_CHANGE_PACKET, buf);
                });
            }
        }

        //Send PowerChange package
        if(entity.energyStorage.amount!=entity.previousPower){
            entity.previousPower = entity.energyStorage.amount;
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(blockPos);
            buf.writeLong(entity.getPower());
            for (PlayerEntity player : world.getPlayers()) {
                ServerPlayNetworking.send((ServerPlayerEntity) player, ENERGY_CHANGE_PACKET, buf);
            }
        }
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

    private static boolean tryProduce(ElectrolyzerEntity entity){
        ElectrolyzerRecipe recipe = canProduce(entity);
        if(recipe!=null){
            entity.energyStorage.amount -= recipe.requiredPower;
            entity.fluidStorages.get(0).amount -= recipe.inputAmount;
            entity.fluidStorages.get(1).variant = recipe.output1;
            entity.fluidStorages.get(1).amount += recipe.output1Amount;
            entity.fluidStorages.get(2).variant = recipe.output2;
            entity.fluidStorages.get(2).amount += recipe.output2Amount;
            return true;
        }
        return false;
    }

    private static ElectrolyzerRecipe canProduce(ElectrolyzerEntity entity){
        for (ElectrolyzerRecipe recipe: recipes) {
            if(entity.energyStorage.amount >= recipe.requiredPower) {
                if (entity.fluidStorages.get(0).variant.equals(recipe.input)) {
                    if (entity.fluidStorages.get(0).amount >= recipe.inputAmount) {
                        if (entity.fluidStorages.get(1).variant.isBlank() || entity.fluidStorages.get(1).variant.equals(recipe.output1)) {
                            if (entity.fluidStorages.get(1).getCapacity() - entity.fluidStorages.get(1).amount >= recipe.output1Amount) {
                                if (entity.fluidStorages.get(2).variant.isBlank() || entity.fluidStorages.get(2).variant.equals(recipe.output2)) {
                                    if (entity.fluidStorages.get(2).getCapacity() - entity.fluidStorages.get(2).amount >= recipe.output2Amount) {
                                        return recipe;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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

    private static class ElectrolyzerRecipe{
        public FluidVariant input;
        public long inputAmount;
        public FluidVariant output1;
        public long output1Amount;
        public FluidVariant output2;
        public long output2Amount;
        public long requiredPower;

        public ElectrolyzerRecipe(FluidVariant input, long inputAmount, FluidVariant output1, long output1Amount, FluidVariant output2, long output2Amount, long requiredPower){
            this.input = input;
            this.inputAmount = inputAmount;
            this.output1 = output1;
            this.output1Amount = output1Amount;
            this.output2 = output2;
            this.output2Amount = output2Amount;
            this.requiredPower = requiredPower;
        }
    }
}
