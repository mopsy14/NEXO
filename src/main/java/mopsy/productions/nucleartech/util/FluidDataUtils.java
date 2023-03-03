package mopsy.productions.nucleartech.util;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.nbt.NbtCompound;
@SuppressWarnings("UnstableApiUsage")
public class FluidDataUtils {
    public static long getFluidAmount(NbtCompound nbt){
        return nbt.getLong("fluid_amount");
    }

    public static FluidVariant getFluidType(NbtCompound nbt){
        return FluidVariant.fromNbt(nbt.getCompound("fluid_variant"));
    }
    public static void setFluidAmount(NbtCompound nbt, long amount){
        nbt.putLong("fluid_amount", amount);
        if(amount == 0)
            nbt.put("fluid_variant", FluidVariant.blank().toNbt());
    }
    public static void setFluidType(NbtCompound nbt, FluidVariant type){
        nbt.put("fluid_variant", type.toNbt());
        if(type.isBlank())
            nbt.putLong("fluid_amount", 0);
    }
    public static void creNbtIfNeeded(NbtCompound nbt){
        if(!(nbt.contains("fluid_variant")&&nbt.contains("fluid_amount"))){
            setFluidAmount(nbt, 0);
        }
    }
}
