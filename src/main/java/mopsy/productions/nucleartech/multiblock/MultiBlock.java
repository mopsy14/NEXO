package mopsy.productions.nucleartech.multiblock;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MultiBlock {
    public Block[] blocks;
    public BlockPos[] blockPosList;
    public BlockEntity controllerEntity;
    public World world;
    public int size;
    public MBType type;
    public boolean isFunctional = false;

    public MultiBlock(BlockEntity controllerEntity){

    }
    public MultiBlock(Block[] blocks){

    }
}
