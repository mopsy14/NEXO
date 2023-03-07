package mopsy.productions.nucleartech.world.feature;

import mopsy.productions.nucleartech.registry.ModdedBlocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class ModConfiguredFeatures {
    public static final List<OreFeatureConfig.Target> OVERWORLD_URANIUM_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("uranium_ore").getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("deepslate_uranium_ore").getDefaultState())
    );
    public static final List<OreFeatureConfig.Target> OVERWORLD_FLUORITE_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("fluorite_ore").getDefaultState())
    );
    public static final List<OreFeatureConfig.Target> OVERWORLD_TITANIUM_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("titanium_ore").getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("deepslate_titanium_ore").getDefaultState())
    );


    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> OVERWORLD_URANIUM_ORES_REG_ENTRY =
            ConfiguredFeatures.register("uranium_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_URANIUM_ORES, 4));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> OVERWORLD_FLUORITE_ORES_REG_ENTRY =
            ConfiguredFeatures.register("fluorite_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_FLUORITE_ORES, 6));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> OVERWORLD_TITANIUM_ORES_REG_ENTRY =
            ConfiguredFeatures.register("titanium_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_TITANIUM_ORES, 6));

    public static void regConfiguredFeatures(){

    }
}
