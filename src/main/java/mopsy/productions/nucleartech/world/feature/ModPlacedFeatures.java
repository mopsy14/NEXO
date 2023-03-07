package mopsy.productions.nucleartech.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final RegistryEntry<PlacedFeature> URANIUM_ORE_PLACED = PlacedFeatures.register(
            "uranium_ore_placed",
            ModConfiguredFeatures.OVERWORLD_URANIUM_ORES_REG_ENTRY,
            modifiersWithCount(10, HeightRangePlacementModifier.uniform(YOffset.BOTTOM, YOffset.fixed(40)))
    );
    public static final RegistryEntry<PlacedFeature> FLUORITE_ORE_PLACED = PlacedFeatures.register(
            "fluorite_ore_placed",
            ModConfiguredFeatures.OVERWORLD_FLUORITE_ORES_REG_ENTRY,
            modifiersWithCount(15, HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.TOP))
    );
    public static final RegistryEntry<PlacedFeature> TITANIUM_ORE_PLACED = PlacedFeatures.register(
            "titanium_ore_placed",
            ModConfiguredFeatures.OVERWORLD_TITANIUM_ORES_REG_ENTRY,
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
