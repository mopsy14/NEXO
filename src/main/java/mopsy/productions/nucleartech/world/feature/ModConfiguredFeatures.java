package mopsy.productions.nucleartech.world.feature;

import mopsy.productions.nucleartech.registry.ModdedBlocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class ModConfiguredFeatures {
    public static final List<OreFeatureConfig.Target> OVERWORLD_URANIUM_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("uranium_ore").getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("deepslate_uranium_ore.json").getDefaultState())
    );

    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> OVERWORLD_ORES_REG_ENTRY =
            ConfiguredFeatures.register("uranium_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_URANIUM_ORES, 4));

    public static void regConfiguredFeatures(){

    }
}
