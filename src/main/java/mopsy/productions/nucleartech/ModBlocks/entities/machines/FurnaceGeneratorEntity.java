package mopsy.productions.nucleartech.ModBlocks.entities.machines;

import mopsy.productions.nucleartech.interfaces.IEnergyStorage;
import mopsy.productions.nucleartech.registry.ModdedBlockEntities;
import mopsy.productions.nucleartech.screen.furnaceGenerator.FurnaceGeneratorScreenHandler;
import mopsy.productions.nucleartech.util.InvUtils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
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

import static mopsy.productions.nucleartech.networking.PacketManager.ENERGY_CHANGE_PACKET;

@SuppressWarnings("UnstableApiUsage")
public class FurnaceGeneratorEntity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory, IEnergyStorage {

    private final Inventory inventory = new SimpleInventory(1);
    protected final PropertyDelegate propertyDelegate;
    private int burnTimeLeft;
    private int burnTime;
    public long previousPower = 0;
    public static final long POWER_CAPACITY = 1000;
    public static final long POWER_MAX_INSERT = 0;
    public static final long POWER_MAX_EXTRACT = 4;
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(POWER_CAPACITY, POWER_MAX_INSERT, POWER_MAX_EXTRACT) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    public FurnaceGeneratorEntity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.FURNACE_GENERATOR, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index){
                    case 0: return FurnaceGeneratorEntity.this.burnTimeLeft;
                    case 1: return FurnaceGeneratorEntity.this.burnTime;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0: FurnaceGeneratorEntity.this.burnTimeLeft = value; break;
                    case 1: FurnaceGeneratorEntity.this.burnTime = value; break;
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
        return Text.literal("Furnace Generator");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(this.pos);
        buf.writeLong(getPower());
        ServerPlayNetworking.send((ServerPlayerEntity) player, ENERGY_CHANGE_PACKET, buf);
        return new FurnaceGeneratorScreenHandler(syncId, inv, this, this.propertyDelegate, pos);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        InvUtils.writeInv(inventory, nbt);
        nbt.putInt("furnace_generator.burn_time_left", burnTimeLeft);
        nbt.putInt("furnace_generator.burn_time", burnTime);
        nbt.putLong("furnace_generator.power", energyStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        InvUtils.readInv(inventory, nbt);
        burnTimeLeft = nbt.getInt("furnace_generator.burn_time_left");
        burnTime = nbt.getInt("furnace_generator.burn_time");
        energyStorage.amount = nbt.getLong("furnace_generator.power");
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, FurnaceGeneratorEntity entity) {
        if(world.isClient)return;

        if(entity.burnTimeLeft ==0&&entity.energyStorage.capacity-entity.energyStorage.amount!=0){
             Integer burnTime = FuelRegistry.INSTANCE.get(entity.inventory.getStack(0).getItem());
             if(burnTime!=null && burnTime>1){
                 burnTime = Math.round(burnTime/2.0f);
                 entity.burnTime = burnTime;
                 entity.burnTimeLeft = burnTime;
                 Inventory inv = entity.inventory;
                 Item remainder = inv.getStack(0).getItem().getRecipeRemainder();
                 inv.getStack(0).decrement(1);
                 if (entity.inventory.getStack(0).isEmpty()) {
                     inv.setStack(0, remainder == null ? ItemStack.EMPTY : new ItemStack(remainder));
                 }
             }
        }

        if(entity.burnTimeLeft >0 && entity.energyStorage.amount < entity.energyStorage.capacity){
            entity.burnTimeLeft--;
            entity.energyStorage.amount += Math.min(5, entity.energyStorage.capacity-entity.energyStorage.amount);
        }

        markDirty(world,blockPos,blockState);

        if(entity.energyStorage.amount!=entity.previousPower){
            entity.previousPower = entity.energyStorage.amount;
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(blockPos);
            buf.writeLong(entity.getPower());
            PlayerLookup.tracking(entity).forEach(player -> ServerPlayNetworking.send(player, ENERGY_CHANGE_PACKET, buf));
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
        return true;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }
}
