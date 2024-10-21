package mopsy.productions.nexo.ModFluids;

import mopsy.productions.nexo.registry.ModdedFluids;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.StateManager;

public abstract class NaturalCrudeOilFluid extends CrudeOilFluid{
    @Override
    public String getID() {
        return "natural_crude_oil";
    }

    /**
     * @return whether the given fluid an instance of this fluid
     */
    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    @Override
    public Fluid getStill() {
        return ModdedFluids.stillFluids.get(getID());
    }

    @Override
    public Fluid getFlowing() {
        return ModdedFluids.flowingFluids.get(getID());
    }

    public static class Flowing extends NaturalCrudeOilFluid {
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

    public static class Still extends NaturalCrudeOilFluid {
        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
