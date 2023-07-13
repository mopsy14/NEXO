package mopsy.productions.nucleartech.REICompat.categories.filling;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mopsy.productions.nucleartech.REICompat.NEXODisplay;
import mopsy.productions.nucleartech.recipes.FillingRecipe;
import mopsy.productions.nucleartech.recipes.NEXORecipe;

import static mopsy.productions.nucleartech.Main.modid;

@SuppressWarnings("UnstableApiUsage")
public class FillingDisplay extends NEXODisplay {

    public FillingRecipe recipe;

    public FillingDisplay(NEXORecipe recipe) {
        super(recipe);
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"filling");
    }
}
