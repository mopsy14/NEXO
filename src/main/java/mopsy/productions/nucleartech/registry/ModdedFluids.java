package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModFluids.Nitrogen;
import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static mopsy.productions.nucleartech.Main.modid;

public class ModdedFluids {
    public static FlowableFluid NITROGEN;
    public static Block NITROGEN_BLOCK;

    public static void regFluids(){
        NITROGEN = regFluid(new Nitrogen.Still());
        NITROGEN_BLOCK = regBlock(NITROGEN);
    }
    private static FlowableFluid regFluid(FlowableFluid fluid){
        return Registry.register(Registry.FLUID, new Identifier(modid,((IModID)fluid).getID()), fluid);
    }
    private static Block regBlock(FlowableFluid fluid){
        return Registry.register(Registry.BLOCK, new Identifier(modid,((IModID)fluid).getID()), new FluidBlock(fluid, FabricBlockSettings.copyOf(Blocks.WATER)){});
    }
}
