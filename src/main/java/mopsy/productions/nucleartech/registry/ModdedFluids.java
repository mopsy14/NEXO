package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModFluids.NTFluid;
import mopsy.productions.nucleartech.util.NTBucketItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

import static mopsy.productions.nucleartech.Main.modid;

public class ModdedFluids {

    public static HashMap<String,FlowableFluid> flowingFluids = new HashMap<>();
    public static HashMap<String,FlowableFluid> stillFluids = new HashMap<>();
    public static HashMap<String,FluidBlock> fluidBlocks = new HashMap<>();
    public static HashMap<String,Item> fluidItems = new HashMap<>();

    public static void registerGas(String ID){
        flowingFluids.put(ID, Registry.register(Registry.FLUID, new Identifier(modid,ID+"_flowing"), new NTFluid.Flowing(ID)));
        stillFluids.put(ID, Registry.register(Registry.FLUID, new Identifier(modid, ID), new NTFluid.Still(ID)));
        fluidItems.put(ID, Registry.register(Registry.ITEM, new Identifier(modid, ID+"_bucket"), new NTBucketItem(stillFluids.get(ID), false)));
        fluidBlocks.put(ID, Registry.register(Registry.BLOCK, new Identifier(modid, ID), new FluidBlock(stillFluids.get(ID), FabricBlockSettings.of(Material.WATER).noCollision())));

    }

    public static void regFluids(){
        registerGas("nitrogen");
        registerGas("oxygen");
        registerGas("hydrogen");
        registerGas("super_dense_steam");
        registerGas("dense_steam");
        registerGas("steam");
        registerGas("ammonia");
        registerGas("fluorine");
        registerGas("hf_kf");
        registerGas("hydrogen_fluoride");
        registerGas("sulfur_dioxide");
        registerGas("sulfuric_acid");
        registerGas("sulfur_trioxide");
        registerGas("uranium_hexafluoride");
        registerGas("uranium_hexafluoride_enriched");
        registerGas("depleted_uranium_tails");
    }
}
