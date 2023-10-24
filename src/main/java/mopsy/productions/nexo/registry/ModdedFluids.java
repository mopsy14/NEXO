package mopsy.productions.nexo.registry;

import mopsy.productions.nexo.ModFluids.NTFluid;
import mopsy.productions.nexo.ModItems.blocks.Tank_MK1Item;
import mopsy.productions.nexo.client.FluidRenderers;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

import static mopsy.productions.nexo.Main.modid;
import static mopsy.productions.nexo.client.ClientUtils.isClient;

public class ModdedFluids {

    public static HashMap<String,FlowableFluid> flowingFluids = new HashMap<>();
    public static HashMap<String,FlowableFluid> stillFluids = new HashMap<>();
    public static HashMap<String,FluidBlock> fluidBlocks = new HashMap<>();
    public static HashMap<String,Item> fluidItems = new HashMap<>();

    public static void registerGas(String ID){
        flowingFluids.put(ID, Registry.register(Registry.FLUID, new Identifier(modid,ID+"_flowing"), new NTFluid.Flowing(ID)));
        stillFluids.put(ID, Registry.register(Registry.FLUID, new Identifier(modid, ID), new NTFluid.Still(ID)));
        fluidBlocks.put(ID, Registry.register(Registry.BLOCK, new Identifier(modid, ID), new FluidBlock(stillFluids.get(ID), FabricBlockSettings.of(Material.WATER).noCollision())));
        if(isClient)
            FluidRenderers.regFluidRenderer(stillFluids.get(ID), 0xA1FFFFFF);
        Tank_MK1Item.fluidGroupVariants.add(FluidVariant.of(stillFluids.get(ID)));
    }
    public static void registerGas(String ID, int color){
        flowingFluids.put(ID, Registry.register(Registry.FLUID, new Identifier(modid,ID+"_flowing"), new NTFluid.Flowing(ID)));
        stillFluids.put(ID, Registry.register(Registry.FLUID, new Identifier(modid, ID), new NTFluid.Still(ID)));
        fluidBlocks.put(ID, Registry.register(Registry.BLOCK, new Identifier(modid, ID), new FluidBlock(stillFluids.get(ID), FabricBlockSettings.of(Material.WATER).noCollision())));
        if(isClient)
            FluidRenderers.regFluidRenderer(stillFluids.get(ID), color);
        Tank_MK1Item.fluidGroupVariants.add(FluidVariant.of(stillFluids.get(ID)));
    }

    public static void regFluids(){
        registerGas("nitrogen");
        registerGas("oxygen");
        registerGas("hydrogen");
        registerGas("super_dense_steam",0xA1ff8585);
        registerGas("dense_steam",0xA1ffa4a4);
        registerGas("steam",0xA1ffd4d4);
        registerGas("ammonia");
        registerGas("fluorine");
        registerGas("hf_kf");
        registerGas("hydrogen_fluoride");
        registerGas("sulfur_dioxide");
        registerGas("sulfuric_acid");
        registerGas("sulfur_trioxide");
        registerGas("uranium_hexafluoride");
        registerGas("enriched_uranium_hexafluoride");
        registerGas("depleted_uranium_tails");
    }
}
