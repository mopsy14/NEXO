package mopsy.productions.nucleartech.interfaces;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

public interface IFluidStorage {
    FluidVariant getFluidType();
    void setFluidType(FluidVariant fluidType);

    long getFluidAmount();
    void setFluidAmount(long amount);
}
