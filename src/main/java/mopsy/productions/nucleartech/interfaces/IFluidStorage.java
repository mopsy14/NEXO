package mopsy.productions.nucleartech.interfaces;

import net.minecraft.fluid.Fluid;

public interface IFluidStorage {
    Fluid getFluidType();
    void setFluidType(Fluid fluidType);

    long getFluidAmount();
    void setFluidAmount(long amount);
}
