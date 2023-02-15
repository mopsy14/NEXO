package mopsy.productions.nucleartech.ModBlocks.entities.machines;

import mopsy.productions.nucleartech.interfaces.IEnergyStorage;
import mopsy.productions.nucleartech.registry.ModdedBlockEntities;
import mopsy.productions.nucleartech.screen.crusher.CrusherScreenHandler;
import mopsy.productions.nucleartech.util.NCFluidStorage;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import static mopsy.productions.nucleartech.networking.PacketManager.ENERGY_CHANGE_PACKET;
import static mopsy.productions.nucleartech.util.InvUtils.readInv;
import static mopsy.productions.nucleartech.util.InvUtils.writeInv;

public class ElectrolyzerEntity extends BlockEntity implements ExtendedScreenHandlerFactory, IEnergyStorage {

    public final Inventory inventory = new SimpleInventory(6);
    protected final PropertyDelegate propertyDelegate;
    private int progress;
    private int maxProgress = 200;
    public final NCFluidStorage inputFluidStorage = new NCFluidStorage(8*FluidConstants.BUCKET,this, true);
    public final NCFluidStorage output1FluidStorage = new NCFluidStorage(8*FluidConstants.BUCKET,this, false);
    public final NCFluidStorage output2fluidStorage = new NCFluidStorage(8*FluidConstants.BUCKET,this, false);
    public long previousPower = 0;
    public static final long POWER_CAPACITY = 1000;
    public static final long POWER_MAX_INSERT = 10;
    public static final long POWER_MAX_EXTRACT = 0;
    public SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(POWER_CAPACITY, POWER_MAX_INSERT, POWER_MAX_EXTRACT) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    public ElectrolyzerEntity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.ELECTROLYZER, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index){
                    case 0: return ElectrolyzerEntity.this.progress;
                    case 1: return ElectrolyzerEntity.this.maxProgress;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0: ElectrolyzerEntity.this.progress = value; break;
                    case 1: ElectrolyzerEntity.this.maxProgress = value; break;
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
        return Text.literal("Electrolyzer");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(this.pos);
        buf.writeLong(getPower());
        ServerPlayNetworking.send((ServerPlayerEntity) player, ENERGY_CHANGE_PACKET, buf);
        return new CrusherScreenHandler(syncId, inv, this.inventory, this.propertyDelegate, pos);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        writeInv(inventory, nbt);
        nbt.putInt("crusher.progress", progress);
        nbt.putLong("crusher.power", energyStorage.amount);
    }
    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        readInv(inventory, nbt);
        progress = nbt.getInt("crusher.progress");
        energyStorage.amount = nbt.getLong("crusher.power");
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, ElectrolyzerEntity crusherEntity) {
        if(world.isClient)return;

        if(crusherEntity.energyStorage.amount!=crusherEntity.previousPower){
            crusherEntity.previousPower = crusherEntity.energyStorage.amount;
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBlockPos(blockPos);
            buf.writeLong(crusherEntity.getPower());
            for (PlayerEntity player : world.getPlayers()) {
                ServerPlayNetworking.send((ServerPlayerEntity) player, ENERGY_CHANGE_PACKET, buf);
            }
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
}
