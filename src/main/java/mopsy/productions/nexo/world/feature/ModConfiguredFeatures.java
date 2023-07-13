package mopsy.productions.nexo.world.feature;

import mopsy.productions.nexo.registry.ModdedBlocks;
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
    public static final List<OreFeatureConfig.Target> OVERWORLD_SULFUR_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("sulfur_ore").getDefaultState())
    );
    public static final List<OreFeatureConfig.Target> OVERWORLD_LEAD_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("lead_ore").getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("deepslate_lead_ore").getDefaultState())
    );
    public static final List<OreFeatureConfig.Target> OVERWORLD_CAROBBIITE_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("carobbiite_ore").getDefaultState())
    );
    public static final List<OreFeatureConfig.Target> OVERWORLD_SALT_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("salt_ore").getDefaultState())
    );
    public static final List<OreFeatureConfig.Target> OVERWORLD_VANADIUM_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("vanadium_ore").getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("deepslate_vanadium_ore").getDefaultState())
    );
    public static final List<OreFeatureConfig.Target> OVERWORLD_NICKEL_ORES = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("nickel_ore").getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, ModdedBlocks.Blocks.get("deepslate_nickel_ore").getDefaultState())
    );


    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> OVERWORLD_URANIUM_ORES_REG_ENTRY =
            ConfiguredFeatures.register("uranium_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_URANIUM_ORES, 4));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> OVERWORLD_FLUORITE_ORES_REG_ENTRY =
            ConfiguredFeatures.register("fluorite_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_FLUORITE_ORES, 5));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> OVERWORLD_TITANIUM_ORES_REG_ENTRY =
            ConfiguredFeatures.register("titanium_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_TITANIUM_ORES, 5));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> OVERWORLD_SULFUR_ORES_REG_ENTRY =
            ConfiguredFeatures.register("sulfur_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_SULFUR_ORES, 6));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> OVERWORLD_LEAD_ORES_REG_ENTRY =
            ConfiguredFeatures.register("lead_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_LEAD_ORES, 5));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> OVERWORLD_CAROBBIITE_ORES_REG_ENTRY =
            ConfiguredFeatures.register("carobbiite_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_CAROBBIITE_ORES, 5));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> OVERWORLD_SALT_ORES_REG_ENTRY =
            ConfiguredFeatures.register("salt_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_SALT_ORES, 40));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> OVERWORLD_VANADIUM_ORES_REG_ENTRY =
            ConfiguredFeatures.register("vanadium_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_VANADIUM_ORES, 4));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> OVERWORLD_NICKEL_ORES_REG_ENTRY =
            ConfiguredFeatures.register("nickel_ore", Feature.ORE, new OreFeatureConfig(OVERWORLD_NICKEL_ORES, 4));

    public static void regConfiguredFeatures(){

    }
}
