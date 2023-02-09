package mopsy.productions.nucleartech.ModBlocks.entities.machines;

import mopsy.productions.nucleartech.interfaces.IFluidStorage;
import mopsy.productions.nucleartech.interfaces.ImplementedInventory;
import mopsy.productions.nucleartech.registry.ModdedBlockEntities;
import mopsy.productions.nucleartech.screen.tank.TankScreenHandler_MK1;
import mopsy.productions.nucleartech.util.FluidUtils;
import mopsy.productions.nucleartech.util.InvUtils;
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
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static mopsy.productions.nucleartech.networking.PacketManager.FLUID_CHANGE_PACKET;

public class TankEntity_MK1 extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, IFluidStorage{

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    public static final long MAX_CAPACITY = 8;
    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return MAX_CAPACITY * FluidConstants.BUCKET;
        }

        @Override
        protected void onFinalCommit() {
            // Called after a successful insertion or extraction, markDirty to ensure the new amount and variant will be saved properly.
            markDirty();
            if (!world.isClient) {
                var buf = PacketByteBufs.create();
                buf.writeBlockPos(getPos());
                this.variant.toPacket(buf);
                buf.writeLong(this.amount);
                world.getPlayers().forEach(player-> {
                    ServerPlayNetworking.send((ServerPlayerEntity) player, FLUID_CHANGE_PACKET, buf);
                });
            }
        }
    };

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
        return new TankScreenHandler_MK1(syncId, inv, this, pos);
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
        Inventories.writeNbt(nbt, inventory);
        nbt.putLong("fluid_amount", fluidStorage.amount);
        nbt.put("fluid_variant", fluidStorage.variant.toNbt());
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        fluidStorage.amount = nbt.getLong("fluid_amount");
        fluidStorage.variant = FluidVariant.fromNbt(nbt.getCompound("fluid_variant"));
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, TankEntity_MK1 tankEntity) {
        if (world.isClient) return;

        try (Transaction transaction = Transaction.openOuter()) {
            if (StorageUtil.move(
                    FluidUtils.getItemFluidStorage(InvUtils.InvOf(tankEntity.inventory), 0, 1),
                    tankEntity.fluidStorage,
                    predicate -> true,
                    Long.MAX_VALUE,
                    transaction
               ) > 0) {
                System.out.println("move==1B");
                transaction.commit();
                markDirty(world, blockPos, blockState);
            }
        }
        //StorageUtil.move(itemStorage, tank, fv -> true, Long.MAX_VALUE, null) > 0
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
}
