package mopsy.productions.nucleartech.multiblock;

import mopsy.productions.nucleartech.interfaces.IMBBlock;
import mopsy.productions.nucleartech.interfaces.IModID;
import mopsy.productions.nucleartech.interfaces.IMultiBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class MBUtils {
    public static int maxMBSize = 50;

    public static boolean hasCompleteMultiBlock(BlockEntity blockEntity, MultiBlock multiBlock){
        if(multiBlock instanceof IMultiBlock) {
            MultiBlockRequirement[] requirements = ((IMultiBlock)multiBlock).getRequirements();
            for (MultiBlockRequirement requirement : requirements){
                if(getMBBlockAmount(requirement.blockID.getPath(), blockEntity.getPos(), blockEntity.getWorld())<requirement.amount)
                    return false;
            }
            return true;
        }
        return false;
    }

    public static int getMBBlockAmount(String ID, BlockPos blockPos, World world){
        List<BlockPos> checkedBlocks = new ArrayList<>();
        List<BlockPos> blocks = new ArrayList<>();
        List<BlockPos> toCheck = new ArrayList<>();
        getSurroundingBlocks(blockPos, world, checkedBlocks, blocks, toCheck);
        int i = 0;
        for (; i < maxMBSize; i++) {
            if(toCheck.size()>0) {
                getSurroundingBlocks(toCheck.get(0), world, checkedBlocks, blocks, toCheck);
                toCheck.remove(0);
            }else
                break;
        }
        return i<=maxMBSize && toCheck.size()==0 ? (int) blocks.stream().filter((tmpPos)-> world.getBlockState(tmpPos).getBlock() instanceof IModID && ((IModID) world.getBlockState(tmpPos).getBlock()).getID().equals(ID)).count():0;
    }
    private static List<BlockPos> getSurroundingBlocks(BlockPos blockPos, World world, List<BlockPos> checkedBlocks, List<BlockPos> blocks, List<BlockPos> toCheck){
        List<BlockPos> res = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            BlockPos tmpPos = getBlockAtSide(blockPos,i);
            if(!checkedBlocks.contains(tmpPos)) {
                if (world.getBlockState(tmpPos).getBlock() instanceof IMBBlock) {
                    blocks.add(tmpPos);
                    toCheck.add(tmpPos);
                }
                checkedBlocks.add(tmpPos);
            }
        }

        return res;
    }
    private static BlockPos getBlockAtSide(BlockPos blockPos, int side){
        return switch (side){
            case 1 -> blockPos.add(1,0,0);
            case 2 -> blockPos.add(-1,0,0);
            case 3 -> blockPos.add(0,1,0);
            case 4 -> blockPos.add(0,-1,0);
            case 5 -> blockPos.add(0,0,1);
            case 6 -> blockPos.add(0,0,-1);
            default ->  new BlockPos(0,0,0);
        };
    }
}
