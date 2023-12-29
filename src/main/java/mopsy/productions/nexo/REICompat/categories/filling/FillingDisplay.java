package mopsy.productions.nexo.REICompat.categories.filling;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mopsy.productions.nexo.REICompat.NEXODisplay;
import mopsy.productions.nexo.recipes.FillingRecipe;
import mopsy.productions.nexo.recipes.NEXORecipe;

import static mopsy.productions.nexo.Main.modid;

@SuppressWarnings("UnstableApiUsage")
public class FillingDisplay extends NEXODisplay {

    public FillingRecipe recipe;

    public FillingDisplay(NEXORecipe recipe) {
        super(recipe);
        this.recipe= (FillingRecipe) recipe;
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"filling");
    }
}
