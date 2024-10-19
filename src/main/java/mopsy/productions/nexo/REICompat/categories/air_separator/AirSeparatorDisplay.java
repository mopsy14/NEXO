package mopsy.productions.nexo.REICompat.categories.air_separator;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mopsy.productions.nexo.REICompat.NEXODisplay;
import mopsy.productions.nexo.recipes.AirSeparatorRecipe;
import mopsy.productions.nexo.recipes.NEXORecipe;

import static mopsy.productions.nexo.Main.modid;

@SuppressWarnings("UnstableApiUsage")
public class AirSeparatorDisplay extends NEXODisplay {

    public AirSeparatorRecipe recipe;

    public AirSeparatorDisplay(NEXORecipe recipe) {
        super(recipe);
        this.recipe= (AirSeparatorRecipe) recipe;
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"air_separator");
    }
}
