package mopsy.productions.nexo.util;

import mopsy.productions.nexo.registry.ModdedDataComponentTypes;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.item.ItemStack;

public class FluidDataUtils {
    public static long getFluidAmount(ItemStack stack){
        return stack.getOrDefault(ModdedDataComponentTypes.FLUID_AMOUNT_COMPONENT_TYPE,0L);
    }

    public static FluidVariant getFluidType(ItemStack stack){
        return stack.getOrDefault(ModdedDataComponentTypes.FLUID_VARIANT_COMPONENT_TYPE,FluidVariant.blank());
    }

    public static ItemStack setFluidAmount(ItemStack stack, long amount){
        stack.set(ModdedDataComponentTypes.FLUID_AMOUNT_COMPONENT_TYPE,amount);
        if(amount == 0)
            stack.set(ModdedDataComponentTypes.FLUID_VARIANT_COMPONENT_TYPE, FluidVariant.blank());
        return stack;
    }
    public static ItemStack setFluidType(ItemStack stack, FluidVariant type){
        stack.set(ModdedDataComponentTypes.FLUID_VARIANT_COMPONENT_TYPE, type);
        if(type.isBlank())
            stack.set(ModdedDataComponentTypes.FLUID_AMOUNT_COMPONENT_TYPE,0L);
        return stack;
    }
}
