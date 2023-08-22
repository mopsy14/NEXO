package mopsy.productions.nexo.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final RegistryEntry<PlacedFeature> URANIUM_ORE_PLACED = PlacedFeatures.register(
            "nexo_uranium_ore_placed",
            ModConfiguredFeatures.OVERWORLD_URANIUM_ORES_REG_ENTRY,
            modifiersWithCount(10, HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.fixed(40)))
    );
    public static final RegistryEntry<PlacedFeature> FLUORITE_ORE_PLACED = PlacedFeatures.register(
            "nexo_fluorite_ore_placed",
            ModConfiguredFeatures.OVERWORLD_FLUORITE_ORES_REG_ENTRY,
            modifiersWithCount(15, HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.TOP))
    );
    public static final RegistryEntry<PlacedFeature> TITANIUM_ORE_PLACED = PlacedFeatures.register(
            "nexo_titanium_ore_placed",
            ModConfiguredFeatures.OVERWORLD_TITANIUM_ORES_REG_ENTRY,
            modifiersWithCount(15, HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.TOP))
    );
    public static final RegistryEntry<PlacedFeature> SULFUR_ORE_PLACED = PlacedFeatures.register(
            "nexo_sulfur_ore_placed",
            ModConfiguredFeatures.OVERWORLD_SULFUR_ORES_REG_ENTRY,
            modifiersWithCount(20, HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.TOP))
    );
    public static final RegistryEntry<PlacedFeature> LEAD_ORE_PLACED = PlacedFeatures.register(
            "nexo_lead_ore_placed",
            ModConfiguredFeatures.OVERWORLD_LEAD_ORES_REG_ENTRY,
            modifiersWithCount(20, HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.TOP))
    );
    public static final RegistryEntry<PlacedFeature> CAROBBIITE_ORE_PLACED = PlacedFeatures.register(
            "nexo_carobbiite_ore_placed",
            ModConfiguredFeatures.OVERWORLD_CAROBBIITE_ORES_REG_ENTRY,
            modifiersWithCount(15, HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.TOP))
    );
    public static final RegistryEntry<PlacedFeature> SALT_ORE_PLACED = PlacedFeatures.register(
            "nexo_salt_ore_placed",
            ModConfiguredFeatures.OVERWORLD_SALT_ORES_REG_ENTRY,
            modifiersWithCount(3, HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.TOP))
    );
    public static final RegistryEntry<PlacedFeature> VANADIUM_ORE_PLACED = PlacedFeatures.register(
            "nexo_vanadium_ore_placed",
            ModConfiguredFeatures.OVERWORLD_VANADIUM_ORES_REG_ENTRY,
            modifiersWithCount(15, HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.TOP))
    );
    public static final RegistryEntry<PlacedFeature> NICKEL_ORE_PLACED = PlacedFeatures.register(
            "nexo_nickel_ore_placed",
            ModConfiguredFeatures.OVERWORLD_NICKEL_ORES_REG_ENTRY,
            modifiersWithCount(15, HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.TOP))
    );

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }

    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }
}
