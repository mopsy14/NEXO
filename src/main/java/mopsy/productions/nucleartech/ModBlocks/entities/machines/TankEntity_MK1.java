package mopsy.productions.nucleartech.ModBlocks.entities.machines;

import mopsy.productions.nucleartech.interfaces.IFluidStorage;
import mopsy.productions.nucleartech.registry.ModdedBlockEntities;
import mopsy.productions.nucleartech.screen.tank.TankScreenHandler_MK1;
import mopsy.productions.nucleartech.util.FluidUtils;
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
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static mopsy.productions.nucleartech.networking.PacketManager.FLUID_CHANGE_PACKET;

public class TankEntity_MK1 extends BlockEntity implements ExtendedScreenHandlerFactory, IFluidStorage{

    public final Inventory inventory = new SimpleInventory(2);
    public static final long MAX_CAPACITY = 8 * FluidConstants.BUCKET;
    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return MAX_CAPACITY;
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
        return new TankScreenHandler_MK1(syncId, inv, this.inventory, pos);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    private void writeInv(NbtCompound nbt){
        NbtList nbtList = new NbtList();

        for(int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack = inventory.getStack(i);
            if (!itemStack.isEmpty()) {
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putByte("Slot", (byte)i);
                itemStack.writeNbt(nbtCompound);
                nbtList.add(nbtCompound);
            }
        }

        nbt.put("Items", nbtList);
    }
    private void readInv(NbtCompound nbt){
        NbtList nbtList = nbt.getList("Items", 10);

        for(int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 255;
            if (j >= 0 && j < inventory.size()) {
                inventory.setStack(j, ItemStack.fromNbt(nbtCompound));
            }
        }
    }
    @Override
    public void writeNbt(NbtCompound nbt){
        writeInv(nbt);
        nbt.putLong("fluid_amount", fluidStorage.amount);
        nbt.put("fluid_variant", fluidStorage.variant.toNbt());
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        readInv(nbt);
        fluidStorage.amount = nbt.getLong("fluid_amount");
        fluidStorage.variant = FluidVariant.fromNbt(nbt.getCompound("fluid_variant"));
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, TankEntity_MK1 tankEntity) {
        if (world.isClient) return;

        try(Transaction transaction = Transaction.openOuter()) {
            if (tankEntity.canTransfer()) {
                long moved = StorageUtil.move(
                        FluidUtils.getItemFluidStorage(tankEntity.inventory, 0, 1),
                        tankEntity.fluidStorage,
                        predicate -> true,
                        Long.MAX_VALUE,
                        transaction
                );
                if(moved == 0){
                    moved = StorageUtil.move(
                            tankEntity.fluidStorage,
                            FluidUtils.getItemFluidStorage(tankEntity.inventory, 0, 1),
                            fv -> true,
                            Long.MAX_VALUE,
                            transaction
                    );
                }
                if (moved > 0) {
                    transaction.commit();
                    markDirty(world, blockPos, blockState);
                }
            }
        }
        //StorageUtil.move(itemStorage, tank, fv -> true, Long.MAX_VALUE, null) > 0
    }

    private boolean canTransfer(){
        if(inventory.getStack(0).isEmpty())
            return false;
        return inventory.getStack(1).getCount() < inventory.getStack(1).getMaxCount();
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
