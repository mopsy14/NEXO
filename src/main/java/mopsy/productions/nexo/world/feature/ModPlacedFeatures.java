package mopsy.productions.nexo.world.feature;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

import static mopsy.productions.nexo.Main.modid;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> URANIUM_ORE_PLACED_KEY = registerKey("uranium_ore_placed");
    public static final RegistryKey<PlacedFeature> FLUORITE_ORE_PLACED_KEY = registerKey("fluorite_ore_placed");
    public static final RegistryKey<PlacedFeature> TITANIUM_ORE_PLACED_KEY = registerKey("titanium_ore_placed");
    public static final RegistryKey<PlacedFeature> SULFUR_ORE_PLACED_KEY = registerKey("sulfur_ore_placed");
    public static final RegistryKey<PlacedFeature> LEAD_ORE_PLACED_KEY = registerKey("lead_ore_placed");
    public static final RegistryKey<PlacedFeature> CAROBBIITE_ORE_PLACED_KEY = registerKey("carobbiite_ore_placed");
    public static final RegistryKey<PlacedFeature> SALT_ORE_PLACED_KEY = registerKey("salt_ore_placed");
    public static final RegistryKey<PlacedFeature> VANADIUM_ORE_PLACED_KEY = registerKey("vanadium_ore_placed");
    public static final RegistryKey<PlacedFeature> NICKEL_ORE_PLACED_KEY = registerKey("nickel_ore_placed");

    public static void bootstrap(Registerable<PlacedFeature> context){
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context,URANIUM_ORE_PLACED_KEY,registryLookup.getOrThrow(ModConfiguredFeatures.URANIUM_ORE_KEY),
                modifiersWithCount(10, HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.fixed(40)))
        );
        register(context,FLUORITE_ORE_PLACED_KEY,registryLookup.getOrThrow(ModConfiguredFeatures.FLUORITE_ORE_KEY),
                modifiersWithCount(15, HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.TOP))
        );
        register(context,TITANIUM_ORE_PLACED_KEY,registryLookup.getOrThrow(ModConfiguredFeatures.TITANIUM_ORE_KEY),
                modifiersWithCount(15, HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.TOP))
        );
        register(context,SULFUR_ORE_PLACED_KEY,registryLookup.getOrThrow(ModConfiguredFeatures.SULFUR_ORE_KEY),
                modifiersWithCount(20, HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.TOP))
        );
        register(context,LEAD_ORE_PLACED_KEY,registryLookup.getOrThrow(ModConfiguredFeatures.LEAD_ORE_KEY),
                modifiersWithCount(20, HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.TOP))
        );
        register(context,CAROBBIITE_ORE_PLACED_KEY,registryLookup.getOrThrow(ModConfiguredFeatures.CAROBBIITE_ORE_KEY),
                modifiersWithCount(15, HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.TOP))
        );
        register(context,SALT_ORE_PLACED_KEY,registryLookup.getOrThrow(ModConfiguredFeatures.SALT_ORE_KEY),
                modifiersWithCount(3, HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.TOP))
        );
        register(context,VANADIUM_ORE_PLACED_KEY,registryLookup.getOrThrow(ModConfiguredFeatures.VANADIUM_ORE_KEY),
                modifiersWithCount(15, HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.TOP))
        );
        register(context,NICKEL_ORE_PLACED_KEY,registryLookup.getOrThrow(ModConfiguredFeatures.NICKEL_ORE_KEY),
                modifiersWithCount(15, HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.TOP))
        );
    }

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }

    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }

    public static RegistryKey<PlacedFeature> registerKey(String name){
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(modid,name));
    }
    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                 RegistryEntry<ConfiguredFeature<?,?>> configuration,List<PlacementModifier> modifiers){
        context.register(key,new PlacedFeature(configuration,List.copyOf(modifiers)));
    }
}
