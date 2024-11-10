package mopsy.productions.nexo.world.feature;

import mopsy.productions.nexo.registry.ModdedBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

import static mopsy.productions.nexo.Main.modid;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> URANIUM_ORE_KEY = registryKey("uranium_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> FLUORITE_ORE_KEY = registryKey("fluorite_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> TITANIUM_ORE_KEY = registryKey("titanium_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> SULFUR_ORE_KEY = registryKey("sulfur_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> LEAD_ORE_KEY = registryKey("lead_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> CAROBBIITE_ORE_KEY = registryKey("carobbiite_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> SALT_ORE_KEY = registryKey("salt_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> VANADIUM_ORE_KEY = registryKey("vanadium_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> NICKEL_ORE_KEY = registryKey("nickel_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?,?>> context){
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreFeatureConfig.Target> uraniumOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables,ModdedBlocks.Blocks.get("uranium_ore").getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables,ModdedBlocks.Blocks.get("deepslate_uranium_ore").getDefaultState()));
        List<OreFeatureConfig.Target> fluoriteOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables,ModdedBlocks.Blocks.get("uranium_ore").getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables,ModdedBlocks.Blocks.get("deepslate_uranium_ore").getDefaultState()));
        List<OreFeatureConfig.Target> titaniumOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables,ModdedBlocks.Blocks.get("uranium_ore").getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables,ModdedBlocks.Blocks.get("deepslate_uranium_ore").getDefaultState()));
        List<OreFeatureConfig.Target> sulfurOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables,ModdedBlocks.Blocks.get("uranium_ore").getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables,ModdedBlocks.Blocks.get("deepslate_uranium_ore").getDefaultState()));
        List<OreFeatureConfig.Target> leadOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables,ModdedBlocks.Blocks.get("uranium_ore").getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables,ModdedBlocks.Blocks.get("deepslate_uranium_ore").getDefaultState()));
        List<OreFeatureConfig.Target> carobbiiteOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables,ModdedBlocks.Blocks.get("uranium_ore").getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables,ModdedBlocks.Blocks.get("deepslate_uranium_ore").getDefaultState()));
        List<OreFeatureConfig.Target> saltOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables,ModdedBlocks.Blocks.get("uranium_ore").getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables,ModdedBlocks.Blocks.get("deepslate_uranium_ore").getDefaultState()));
        List<OreFeatureConfig.Target> vanadiumOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables,ModdedBlocks.Blocks.get("uranium_ore").getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables,ModdedBlocks.Blocks.get("deepslate_uranium_ore").getDefaultState()));
        List<OreFeatureConfig.Target> nickelOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables,ModdedBlocks.Blocks.get("uranium_ore").getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables,ModdedBlocks.Blocks.get("deepslate_uranium_ore").getDefaultState()));

        register(context,URANIUM_ORE_KEY,Feature.ORE,new OreFeatureConfig(uraniumOres,4));
        register(context,FLUORITE_ORE_KEY,Feature.ORE,new OreFeatureConfig(fluoriteOres,5));
        register(context,TITANIUM_ORE_KEY,Feature.ORE,new OreFeatureConfig(titaniumOres,5));
        register(context,SULFUR_ORE_KEY,Feature.ORE,new OreFeatureConfig(sulfurOres,6));
        register(context,LEAD_ORE_KEY,Feature.ORE,new OreFeatureConfig(leadOres,5));
        register(context,CAROBBIITE_ORE_KEY,Feature.ORE,new OreFeatureConfig(carobbiiteOres,5));
        register(context,SALT_ORE_KEY,Feature.ORE,new OreFeatureConfig(saltOres,40));
        register(context,VANADIUM_ORE_KEY,Feature.ORE,new OreFeatureConfig(vanadiumOres,4));
        register(context,NICKEL_ORE_KEY,Feature.ORE,new OreFeatureConfig(nickelOres,4));
    }

    public static RegistryKey<ConfiguredFeature<?,?>> registryKey(String name){
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,Identifier.of(modid,name));
    }
    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?,?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?,?>> key,
                                                                                   F feature, FC configuration){
        context.register(key,new ConfiguredFeature<>(feature,configuration));
    }
}
