package mopsy.productions.nucleartech.ModBlocks.entities.machines;

import mopsy.productions.nucleartech.interfaces.IEnergyStorage;
import mopsy.productions.nucleartech.recipes.CentrifugeRecipe;
import mopsy.productions.nucleartech.registry.ModdedBlockEntities;
import mopsy.productions.nucleartech.screen.centrifuge.CentrifugeScreenHandler;
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

public class CentrifugeEntity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory, IEnergyStorage {

    private final Inventory inventory = new SimpleInventory(4);
    public final List<SingleVariantStorage<FluidVariant>> fluidStorages = new ArrayList<>();
    protected final PropertyDelegate propertyDelegate;
    private int progress;
    private int maxProgress = 200;
    public long previousPower = 0;
    public static final long CAPACITY = 100000;
    public static final long MAX_INSERT = 100;
    public static final long MAX_EXTRACT = 0;
    public SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(CAPACITY, MAX_INSERT, MAX_EXTRACT) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    public CentrifugeEntity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.CENTRIFUGE, pos, state);
        fluidStorages.add(new NCFluidStorage(8* FluidConstants.BUCKET ,this, true , 0));
        fluidStorages.add(new NCFluidStorage(8* FluidConstants.BUCKET ,this, false, 1));
        fluidStorages.add(new NCFluidStorage(8* FluidConstants.BUCKET ,this, false, 2));
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index){
                    case 0: return CentrifugeEntity.this.progress;
                    case 1: return CentrifugeEntity.this.maxProgress;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0: CentrifugeEntity.this.progress = value; break;
                    case 1: CentrifugeEntity.this.maxProgress = value; break;
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
        return Text.literal("Centrifuge");
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
        nbt.putInt("centrifuge.progress", progress);
        nbt.putLong("centrifuge.power", energyStorage.amount);
        for (int i = 0; i < fluidStorages.size(); i++) {
            nbt.putLong("fluid_amount_"+i, fluidStorages.get(i).amount);
            nbt.put("fluid_variant_"+i, fluidStorages.get(i).variant.toNbt());
        }
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        readInv(inventory, nbt);
        progress = nbt.getInt("centrifuge.progress");
        energyStorage.amount = nbt.getLong("centrifuge.power");
        for (int i = 0; i < fluidStorages.size(); i++) {
            fluidStorages.get(i).amount = nbt.getLong("fluid_amount_"+i);
            fluidStorages.get(i).variant = FluidVariant.fromNbt(nbt.getCompound("fluid_variant_"+i));
        }
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, CentrifugeEntity centrifugeEntity) {
        if(world.isClient)return;

        if(hasRecipe(centrifugeEntity)&& centrifugeEntity.energyStorage.amount >= 5){
            centrifugeEntity.progress++;
            centrifugeEntity.energyStorage.amount -= 5;
            if(centrifugeEntity.progress >= centrifugeEntity.maxProgress){
                craft(centrifugeEntity);
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
            for (PlayerEntity player : world.getPlayers()) {
                ServerPlayNetworking.send((ServerPlayerEntity) player, ENERGY_CHANGE_PACKET, buf);
            }
        }
    }

    private static void craft(CentrifugeEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for(int i = 0; i< entity.size(); i++){
            inventory.setStack(i, entity.getStack(i));
        }

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

    private static boolean hasRecipe(CentrifugeEntity entity) {
        return getFirstRecipeMatch(entity)!=null;
    }

    private static CentrifugeRecipe getFirstRecipeMatch(CentrifugeEntity entity){
        for(CentrifugeRecipe centrifugeRecipe : entity.getWorld().getRecipeManager().listAllOfType(CentrifugeRecipe.Type.INSTANCE)){
            if(centrifugeRecipe.canProduce(entity.fluidStorages, entity.getWorld())) {
                return centrifugeRecipe;
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
