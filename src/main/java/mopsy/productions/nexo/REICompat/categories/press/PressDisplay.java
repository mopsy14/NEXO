package mopsy.productions.nexo.REICompat.categories.press;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mopsy.productions.nexo.REICompat.NEXODisplay;
import mopsy.productions.nexo.recipes.NEXORecipe;
import mopsy.productions.nexo.recipes.PressRecipe;

import static mopsy.productions.nexo.Main.modid;

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
