package mopsy.productions.nexo.ModBlocks.entities;

import mopsy.productions.nexo.registry.ModdedBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("UnstableApiUsage")
public class InsulatedCopperCableEntity extends BlockEntity{


    public InsulatedCopperCableEntity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.AIR_SEPARATOR, pos, state);
    }
    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);

    }
    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);

    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, InsulatedCopperCableEntity entity) {
        if(world.isClient)return;



        markDirty(world,blockPos,blockState);
    }
}
