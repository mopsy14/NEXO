package mopsy.productions.nucleartech.REICompat.categories.air_separator;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mopsy.productions.nucleartech.REICompat.NEXODisplay;
import mopsy.productions.nucleartech.recipes.AirSeparatorRecipe;
import mopsy.productions.nucleartech.recipes.NEXORecipe;

import static mopsy.productions.nucleartech.Main.modid;

@SuppressWarnings("UnstableApiUsage")
public class AirSeparatorDisplay extends NEXODisplay {

    public AirSeparatorRecipe recipe;

    public AirSeparatorDisplay(NEXORecipe recipe) {
        super(recipe);
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"air_separator");
    }
}
