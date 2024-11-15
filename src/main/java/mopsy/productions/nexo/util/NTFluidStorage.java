package mopsy.productions.nexo.util;

import mopsy.productions.nexo.networking.payloads.AdvancedFluidChangePayload;
import mopsy.productions.nexo.networking.payloads.FluidChangePayload;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.entity.BlockEntity;


public class NTFluidStorage extends SingleVariantStorage<FluidVariant> {
    final long MAX_CAPACITY;
    final BlockEntity blockEntity;
    final boolean canInsert;
    boolean isAdvanced = false;
    int index;

    public NTFluidStorage(long capacity, BlockEntity blockEntity, boolean canInsert){
        this.MAX_CAPACITY = capacity;
        this.blockEntity = blockEntity;
        this.canInsert = canInsert;
    }

    public NTFluidStorage(long capacity, BlockEntity blockEntity, boolean canInsert, int index){
        this.MAX_CAPACITY = capacity;
        this.blockEntity = blockEntity;
        this.canInsert = canInsert;
        this.isAdvanced = true;
        this.index = index;
    }

    public NTFluidStorage copy(){
        NTFluidStorage storage = isAdvanced? new NTFluidStorage(MAX_CAPACITY, blockEntity, canInsert, index) : new NTFluidStorage(MAX_CAPACITY,blockEntity,canInsert);

        storage.amount = amount;
        storage.variant= variant;

        return storage;
    }
    public boolean isEmpty(){
        return amount==0||variant.isBlank();
    }

    @Override
    protected FluidVariant getBlankVariant() {
        return FluidVariant.blank();
    }

    @Override
    protected long getCapacity(FluidVariant variant) {
        return MAX_CAPACITY;
    }

    @Override
    protected boolean canInsert(FluidVariant variant) {
        if(!canInsert)
            return false;
        return super.canInsert(variant);
    }

    @Override
    public boolean supportsInsertion() {
        if(!canInsert)
            return false;
        return super.supportsInsertion();
    }

    @Override
    protected void onFinalCommit() {
        blockEntity.markDirty();
        if(isAdvanced)
            advancedFinalCommit();
        else
            simpleFinalCommit();
    }

    private void simpleFinalCommit(){
        if (!blockEntity.getWorld().isClient) {
            PlayerLookup.tracking(blockEntity).forEach(player-> ServerPlayNetworking.send(
                    player, new FluidChangePayload(blockEntity.getPos(),variant,amount)
            ));
        }
    }

    private void advancedFinalCommit(){
        if (!blockEntity.getWorld().isClient) {
            PlayerLookup.tracking(blockEntity).forEach(player-> ServerPlayNetworking.send(
                    player, new AdvancedFluidChangePayload(blockEntity.getPos(),index,variant,amount)
            ));
        }
    }
}
