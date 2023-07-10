package mopsy.productions.nucleartech.REICompat.categories.press;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mopsy.productions.nucleartech.REICompat.NEXODisplay;
import mopsy.productions.nucleartech.recipes.NEXORecipe;
import mopsy.productions.nucleartech.recipes.PressRecipe;

import static mopsy.productions.nucleartech.Main.modid;

public class PressDisplay extends NEXODisplay {

    public PressRecipe recipe;

    public PressDisplay(NEXORecipe recipe) {
        super(recipe);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"press");
    }
}
