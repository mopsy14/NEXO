package mopsy.productions.nexo.screen.fluidPipe;

import mopsy.productions.nexo.screen.DefaultSHPayload;
import mopsy.productions.nexo.screen.ScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;

public class FluidPipeScreenHandler extends ScreenHandler {
    private final BlockPos blockPos;

    public FluidPipeScreenHandler(int syncId, PlayerInventory playerInventory, DefaultSHPayload buf){
        this(syncId, playerInventory, buf.blockPos());
    }
    public FluidPipeScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos blockPos) {
        super(ScreenHandlers.FLUID_PIPE, syncId);
        this.blockPos = blockPos;
    }
    public BlockPos getBlockPos(){
        return blockPos;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
