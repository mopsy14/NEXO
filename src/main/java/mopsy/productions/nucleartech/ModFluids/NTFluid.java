package mopsy.productions.nucleartech.ModFluids;

import mopsy.productions.nucleartech.interfaces.IModID;
import mopsy.productions.nucleartech.registry.ModdedFluids;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class NTFluid extends FlowableFluid implements IModID {
    String ID;
    @Override
    public String getID() {
        return ID;
    }
    public NTFluid(String ID){
        this.ID=ID;
    }

    @Override
    public void onScheduledTick(World world, BlockPos pos, FluidState state) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    @Override
    protected boolean isInfinite() {
        return false;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {

    }

    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockView blockView, BlockPos blockPos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    protected int getFlowSpeed(WorldView worldView) {
        return 4;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView worldView) {
        return 1;
    }

    @Override
    public int getTickRate(WorldView worldView) {
        return 15;
    }

    @Override
    protected float getBlastResistance() {
        return 100.0F;
    }

    //new:
    @Override
    public Fluid getStill() {
        return ModdedFluids.stillFluids.get(ID);
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }

    @Override
    public Fluid getFlowing() {
        return ModdedFluids.flowingFluids.get(ID);
    }

    @Override
    public Item getBucketItem() {
        return ModdedFluids.fluidItems.get(ID);
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return ModdedFluids.fluidBlocks.get(ID).getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    public static class Flowing extends NTFluid {
        public Flowing(String ID) {
            super(ID);
        }
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends NTFluid {
        public Still(String ID) {
            super(ID);
        }
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }
        @Override
        public int getLevel(FluidState fluidState) {
            return 15;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
