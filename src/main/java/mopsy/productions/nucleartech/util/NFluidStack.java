package mopsy.productions.nucleartech.util;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

@SuppressWarnings("UnstableApiUsage")
public class NFluidStack {
    public FluidVariant fluidVariant;
    public long fluidAmount;

    public NFluidStack(FluidVariant variant, long amount){
        fluidVariant = variant;
        fluidAmount = amount;
    }
}
