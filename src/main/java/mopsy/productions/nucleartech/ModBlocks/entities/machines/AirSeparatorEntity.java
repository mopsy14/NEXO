package mopsy.productions.nucleartech.ModBlocks.entities.machines;

import mopsy.productions.nucleartech.interfaces.IEnergyStorage;
import mopsy.productions.nucleartech.interfaces.ImplementedInventory;
import mopsy.productions.nucleartech.registry.ModdedBlockEntities;
import mopsy.productions.nucleartech.screen.airSeparator.AirSeparatorScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import static mopsy.productions.nucleartech.networking.PacketManager.ENERGY_CHANGE_PACKET;

public class AirSeparatorEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, IEnergyStorage {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    private int progress;
    private int maxProgress = 200;
    public long previousPower = 0;
    public static final long CAPACITY = 1000;
    public static final long MAX_INSERT = 10;
    public static final long MAX_EXTRACT = 0;
    public SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(CAPACITY, MAX_INSERT, MAX_EXTRACT) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    public AirSeparatorEntity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.AIR_SEPARATOR, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index){
                    case 0: return AirSeparatorEntity.this.progress;
                    case 1: return AirSeparatorEntity.this.maxProgress;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0: AirSeparatorEntity.this.progress = value; break;
                    case 1: AirSeparatorEntity.this.maxProgress = value; break;
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
        return Text.literal("Air Separator");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(this.pos);
        buf.writeLong(getPower());
        ServerPlayNetworking.send((ServerPlayerEntity) player, ENERGY_CHANGE_PACKET, buf);
        return new AirSeparatorScreenHandler(syncId, inv, this, this.propertyDelegate, pos);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("air_separator.progress", progress);
        nbt.putLong("air_separator.power", energyStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("air_separator.progress");
        energyStorage.amount = nbt.getLong("air_separator.power");
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, AirSeparatorEntity airSeparatorEntity) {
        if(world.isClient)return;

        if(airSeparatorEntity.energyStorage.amount >= 5){
            airSeparatorEntity.progress++;
            airSeparatorEntity.energyStorage.amount -= 5;
            if(airSeparatorEntity.progress >= airSeparatorEntity.maxProgress){
                produce(airSeparatorEntity);
            }
        }else{
            airSeparatorEntity.progress = 0;
        }

        markDirty(world,blockPos,blockState);

        if(airSeparatorEntity.energyStorage.amount!=airSeparatorEntity.previousPower){
            airSeparatorEntity.previousPower = airSeparatorEntity.energyStorage.amount;
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(blockPos);
            buf.writeLong(airSeparatorEntity.getPower());
            for (PlayerEntity player : world.getPlayers()) {
                ServerPlayNetworking.send((ServerPlayerEntity) player, ENERGY_CHANGE_PACKET, buf);
            }
        }
    }

    private static void produce(AirSeparatorEntity airSeparatorEntity) {

    }

    @Override
    public long getPower() {
        return energyStorage.amount;
    }

    @Override
    public void setPower(long power) {
        energyStorage.amount = power;
    }
}
