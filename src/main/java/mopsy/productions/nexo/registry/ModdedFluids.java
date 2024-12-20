package mopsy.productions.nexo.registry;

import mopsy.productions.nexo.ModFluids.NTFluid;
import mopsy.productions.nexo.ModFluids.RadiatedWaterFluid;
import mopsy.productions.nexo.ModFluids.SuluricAcidFluid;
import mopsy.productions.nexo.ModItems.blocks.Tank_MK1Item;
import mopsy.productions.nexo.client.FluidRenderers;
import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.HashMap;

import static mopsy.productions.nexo.Main.LOGGER;
import static mopsy.productions.nexo.Main.modid;
import static mopsy.productions.nexo.client.ClientUtils.isClient;

public class ModdedFluids {

    public static HashMap<String,FlowableFluid> flowingFluids = new HashMap<>();
    public static HashMap<String,FlowableFluid> stillFluids = new HashMap<>();
    public static HashMap<String,FluidBlock> fluidBlocks = new HashMap<>();
    public static HashMap<String,Item> fluidItems = new HashMap<>();
    public static HashMap<String,BucketItem> buckets = new HashMap<>();

    public static void registerGas(String ID){
        flowingFluids.put(ID, Registry.register(Registries.FLUID, Identifier.of(modid,ID+"_flowing"), new NTFluid.Flowing(ID)));
        stillFluids.put(ID, Registry.register(Registries.FLUID, Identifier.of(modid, ID), new NTFluid.Still(ID)));
        fluidBlocks.put(ID, Registry.register(Registries.BLOCK, Identifier.of(modid, ID), new FluidBlock(stillFluids.get(ID), Block.Settings.copy(Blocks.WATER).noCollision()
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modid,ID))))));
        if(isClient)
            FluidRenderers.regFluidRenderer(stillFluids.get(ID), 0xA1FFFFFF);
        Tank_MK1Item.fluidGroupVariants.add(FluidVariant.of(stillFluids.get(ID)));
    }
    public static void registerGas(String ID, int color){
        flowingFluids.put(ID, Registry.register(Registries.FLUID, Identifier.of(modid,ID+"_flowing"), new NTFluid.Flowing(ID)));
        stillFluids.put(ID, Registry.register(Registries.FLUID, Identifier.of(modid, ID), new NTFluid.Still(ID)));
        fluidBlocks.put(ID, Registry.register(Registries.BLOCK, Identifier.of(modid, ID), new FluidBlock(stillFluids.get(ID), Block.Settings.copy(Blocks.WATER).noCollision()
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modid,ID))))));
        if(isClient)
            FluidRenderers.regFluidRenderer(stillFluids.get(ID), color);
        Tank_MK1Item.fluidGroupVariants.add(FluidVariant.of(stillFluids.get(ID)));
    }

    public static void regFluids(){
        LOGGER.info("Registering fluids");
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
        //registerGas("sulfuric_acid");
        registerGas("sulfur_trioxide");
        registerGas("uranium_hexafluoride");
        registerGas("enriched_uranium_hexafluoride");
        registerGas("depleted_uranium_tails");

        regFluid(new RadiatedWaterFluid.Flowing(),new RadiatedWaterFluid.Still(), 0x3F76E4,false);
        regFluid(new SuluricAcidFluid.Flowing(),new SuluricAcidFluid.Still(), 0xA1FFFFFF,true);
    }

    private static void regFluid(FlowableFluid flowingFluid, FlowableFluid stillFluid, int fluidColor, boolean hasBucket){
        if(flowingFluid instanceof IModID iModID) {
            String ID = iModID.getID();
            flowingFluids.put(ID, Registry.register(Registries.FLUID, Identifier.of(modid, ID + "_flowing"), flowingFluid));
            stillFluids.put(ID, Registry.register(Registries.FLUID, Identifier.of(modid, ID), stillFluid));
            fluidBlocks.put(ID, Registry.register(Registries.BLOCK, Identifier.of(modid, ID), new FluidBlock(stillFluids.get(ID), Block.Settings.copy(Blocks.WATER).noCollision()
                    .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modid,ID))))));
            Tank_MK1Item.fluidGroupVariants.add(FluidVariant.of(stillFluids.get(ID)));

            if(hasBucket){
                buckets.put(ID,Registry.register(Registries.ITEM, Identifier.of(modid,ID+"_bucket"), new BucketItem(stillFluid, new Item.Settings()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modid,ID+"_bucket"))))));
            }

            if(isClient){
                FluidRenderers.regFluidRenderer(flowingFluids.get(ID), stillFluids.get(ID), fluidColor);
            }
        }
    }
}
