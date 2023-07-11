package mopsy.productions.nucleartech.REICompat.categories.electrolyzer;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mopsy.productions.nucleartech.REICompat.NEXODisplay;
import mopsy.productions.nucleartech.recipes.ElectrolyzerRecipe;
import mopsy.productions.nucleartech.recipes.NEXORecipe;

import static mopsy.productions.nucleartech.Main.modid;

@SuppressWarnings("UnstableApiUsage")
public class ElectrolyzerDisplay extends NEXODisplay {

    public ElectrolyzerRecipe recipe;

    public ElectrolyzerDisplay(NEXORecipe recipe) {
        super(recipe);
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"electrolyzer");
    }
}
