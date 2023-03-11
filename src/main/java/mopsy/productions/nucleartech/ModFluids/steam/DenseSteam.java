package mopsy.productions.nucleartech.ModFluids.steam;

import mopsy.productions.nucleartech.interfaces.IModID;
import mopsy.productions.nucleartech.registry.ModdedFluids;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class DenseSteam extends FlowableFluid implements IModID {
    @Override
    public String getID(){
        return "dense_steam";
    }

    @Override
    protected boolean isInfinite() {
        return false;
    }
    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {

    }
    @Override
    protected int getFlowSpeed(WorldView world) {
        return 0;
    }
    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 0;
    }
    @Override
    public int getTickRate(WorldView world) {
        return 0;
    }
    @Override
    protected float getBlastResistance() {
        return 0;
    }
    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }
    @Override
    public Fluid getStill() {
        return ModdedFluids.DENSE_STEAM;
    }
    @Override
    public Fluid getFlowing() {
        return ModdedFluids.DENSE_STEAM;
    }
    @Override
    public Item getBucketItem() {
        return ModdedFluids.DENSE_STEAM_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModdedFluids.DENSE_STEAM_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    @Override
    public boolean isStill(FluidState state) {
        return false;
    }

    public static class Still extends DenseSteam {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid,FluidState> builder){
            super.appendProperties(builder);
            builder.add(LEVEL);
        }
        @Override
        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }
    }
}
