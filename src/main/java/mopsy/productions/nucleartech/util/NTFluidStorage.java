package mopsy.productions.nucleartech.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import static mopsy.productions.nucleartech.networking.PacketManager.ADVANCED_FLUID_CHANGE_PACKET;
import static mopsy.productions.nucleartech.networking.PacketManager.FLUID_CHANGE_PACKET;

@SuppressWarnings("UnstableApiUsage")
public class NTFluidStorage extends SingleVariantStorage<FluidVariant> {
    final long MAX_CAPACITY;
    final BlockEntity blockEntity;
    final boolean canInsert;
    boolean isAdvanced = false;
    int index;
    @SuppressWarnings("UnstableApiUsage")
    public NTFluidStorage(long capacity, BlockEntity blockEntity, boolean canInsert){
        this.MAX_CAPACITY = capacity;
        this.blockEntity = blockEntity;
        this.canInsert = canInsert;
    }
    @SuppressWarnings("UnstableApiUsage")
    public NTFluidStorage(long capacity, BlockEntity blockEntity, boolean canInsert, int index){
        this.MAX_CAPACITY = capacity;
        this.blockEntity = blockEntity;
        this.canInsert = canInsert;
        this.isAdvanced = true;
        this.index = index;
    }
    @SuppressWarnings("UnstableApiUsage")
    @Override
    protected FluidVariant getBlankVariant() {
        return FluidVariant.blank();
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    protected long getCapacity(FluidVariant variant) {
        return MAX_CAPACITY;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    protected boolean canInsert(FluidVariant variant) {
        if(!canInsert)
            return false;
        return super.canInsert(variant);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public boolean supportsInsertion() {
        if(!canInsert)
            return false;
        return super.supportsInsertion();
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    protected void onFinalCommit() {
        blockEntity.markDirty();
        if(isAdvanced)
            advancedFinalCommit();
        else
            simpleFinalCommit();
    }
    @SuppressWarnings("UnstableApiUsage")
    private void simpleFinalCommit(){
        if (!blockEntity.getWorld().isClient) {
            var buf = PacketByteBufs.create();
            buf.writeBlockPos(blockEntity.getPos());
            this.variant.toPacket(buf);
            buf.writeLong(this.amount);
            blockEntity.getWorld().getPlayers().forEach(player-> {
                ServerPlayNetworking.send((ServerPlayerEntity) player, FLUID_CHANGE_PACKET, buf);
            });
        }
    }
    @SuppressWarnings("UnstableApiUsage")
    private void advancedFinalCommit(){
        if (!blockEntity.getWorld().isClient) {
            var buf = PacketByteBufs.create();
            buf.writeBlockPos(blockEntity.getPos());
            buf.writeInt(index);
            this.variant.toPacket(buf);
            buf.writeLong(this.amount);
            blockEntity.getWorld().getPlayers().forEach(player-> {
                ServerPlayNetworking.send((ServerPlayerEntity) player, ADVANCED_FLUID_CHANGE_PACKET, buf);
            });
        }
    }
}
