package mopsy.productions.nucleartech.ModBlocks.entities.machines;

import mopsy.productions.nucleartech.interfaces.IEnergyStorage;
import mopsy.productions.nucleartech.interfaces.IFluidStorage;
import mopsy.productions.nucleartech.recipes.CentrifugeRecipe;
import mopsy.productions.nucleartech.registry.ModdedBlockEntities;
import mopsy.productions.nucleartech.screen.centrifuge.CentrifugeScreenHandler;
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
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.ArrayList;
import java.util.List;

import static mopsy.productions.nucleartech.networking.PacketManager.ADVANCED_FLUID_CHANGE_PACKET;
import static mopsy.productions.nucleartech.networking.PacketManager.ENERGY_CHANGE_PACKET;
import static mopsy.productions.nucleartech.util.InvUtils.readInv;
import static mopsy.productions.nucleartech.util.InvUtils.writeInv;

@SuppressWarnings("UnstableApiUsage")
public class MixerEntity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory, IEnergyStorage, IFluidStorage {

    private final Inventory inventory = new SimpleInventory(14);
    public final List<SingleVariantStorage<FluidVariant>> fluidStorages = new ArrayList<>();
    protected final PropertyDelegate propertyDelegate;
    private int progress;
    private int maxProgress = 500;
    public long previousPower = 0;
    public static final long POWER_CAPACITY = 100000;
    public static final long POWER_MAX_INSERT = 100;
    public static final long POWER_MAX_EXTRACT = 0;
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(POWER_CAPACITY, POWER_MAX_INSERT, POWER_MAX_EXTRACT) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    public MixerEntity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.CENTRIFUGE, pos, state);
        fluidStorages.add(new NTFluidStorage(8* FluidConstants.BUCKET ,this, true , 0));
        fluidStorages.add(new NTFluidStorage(8* FluidConstants.BUCKET ,this, true, 1));
        fluidStorages.add(new NTFluidStorage(8* FluidConstants.BUCKET ,this, true, 2));
        fluidStorages.add(new NTFluidStorage(8* FluidConstants.BUCKET ,this, true, 3));
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index){
                    case 0: return MixerEntity.this.progress;
                    case 1: return MixerEntity.this.maxProgress;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0: MixerEntity.this.progress = value; break;
                    case 1: MixerEntity.this.maxProgress = value; break;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Mixer");
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
        return new CentrifugeScreenHandler(syncId, inv, this, this.propertyDelegate, pos);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        writeInv(inventory, nbt);
        nbt.putInt("mixer.progress", progress);
        nbt.putLong("mixer.power", energyStorage.amount);
        for (int i = 0; i < fluidStorages.size(); i++) {
            nbt.putLong("fluid_amount_"+i, fluidStorages.get(i).amount);
            nbt.put("fluid_variant_"+i, fluidStorages.get(i).variant.toNbt());
        }
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        readInv(inventory, nbt);
        progress = nbt.getInt("mixer.progress");
        energyStorage.amount = nbt.getLong("mixer.power");
        for (int i = 0; i < fluidStorages.size(); i++) {
            fluidStorages.get(i).amount = nbt.getLong("fluid_amount_"+i);
            fluidStorages.get(i).variant = FluidVariant.fromNbt(nbt.getCompound("fluid_variant_"+i));
        }
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, MixerEntity centrifugeEntity) {
        if(world.isClient)return;

        if(hasRecipe(centrifugeEntity)&& centrifugeEntity.energyStorage.amount >= 50){
            centrifugeEntity.progress++;
            centrifugeEntity.energyStorage.amount -= 50;
            if(centrifugeEntity.progress >= centrifugeEntity.maxProgress){
                craft(centrifugeEntity);
                sendFluidUpdate(centrifugeEntity);
            }
        }else{
            centrifugeEntity.progress = 0;
        }

        markDirty(world,blockPos,blockState);

        if(centrifugeEntity.energyStorage.amount!=centrifugeEntity.previousPower){
            centrifugeEntity.previousPower = centrifugeEntity.energyStorage.amount;
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(blockPos);
            buf.writeLong(centrifugeEntity.getPower());
            PlayerLookup.tracking(centrifugeEntity).forEach(player -> ServerPlayNetworking.send(player, ENERGY_CHANGE_PACKET, buf));
        }

        if(tryFabricTransactions(centrifugeEntity)){

        }
        if(tryTransactions(centrifugeEntity)){
            sendFluidUpdate(centrifugeEntity);
        }
    }

    private static boolean tryFabricTransactions(MixerEntity entity) {
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
    private static boolean tryTransactions(MixerEntity entity){
        boolean didSomething = FluidTransactionUtils.tryImportFluid(entity.inventory, 0, 1, entity.fluidStorages.get(0));
        if(FluidTransactionUtils.tryExportFluid(entity.inventory, 0, 1, entity.fluidStorages.get(0)))
            didSomething = true;
        if(FluidTransactionUtils.tryExportFluid(entity.inventory, 2, 3, entity.fluidStorages.get(1)))
            didSomething = true;
        if(FluidTransactionUtils.tryExportFluid(entity.inventory, 4, 5, entity.fluidStorages.get(2)))
            didSomething = true;

        return didSomething;
    }

    private static void craft(MixerEntity entity) {
        CentrifugeRecipe match = getFirstRecipeMatch(entity);

        if(match!=null){
            entity.fluidStorages.get(0).amount -= match.inputAmount;
            entity.fluidStorages.get(1).variant = match.output1;
            entity.fluidStorages.get(1).amount += match.output1Amount;
            entity.fluidStorages.get(2).variant = match.output2;
            entity.fluidStorages.get(2).amount += match.output2Amount;

            entity.progress = 0;
        }
    }

    private static boolean hasRecipe(MixerEntity entity) {
        return getFirstRecipeMatch(entity)!=null;
    }

    private static CentrifugeRecipe getFirstRecipeMatch(MixerEntity entity){
        for(CentrifugeRecipe centrifugeRecipe : entity.getWorld().getRecipeManager().listAllOfType(CentrifugeRecipe.Type.INSTANCE)){
            if(centrifugeRecipe.canProduce(entity.fluidStorages, entity.inventory.getStack(6), entity.getWorld())) {
                return centrifugeRecipe;
            }
        }
        return null;
    }

    private static void sendFluidUpdate(MixerEntity entity){
        for (int i = 0; i < entity.fluidStorages.size(); i++) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(entity.pos);
            buf.writeInt(i);
            entity.fluidStorages.get(i).variant.toPacket(buf);
            buf.writeLong(entity.fluidStorages.get(i).amount);
            PlayerLookup.tracking(entity).forEach(player -> ServerPlayNetworking.send(player, ADVANCED_FLUID_CHANGE_PACKET, buf));
        }
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
