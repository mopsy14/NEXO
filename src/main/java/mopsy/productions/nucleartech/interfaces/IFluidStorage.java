package mopsy.productions.nucleartech.interfaces;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public interface IFluidStorage {
    FluidVariant getFluidType();
    void setFluidType(FluidVariant fluidType);

    long getFluidAmount();
    void setFluidAmount(long amount);

    List<SingleVariantStorage<FluidVariant>> getFluidStorages();
}
