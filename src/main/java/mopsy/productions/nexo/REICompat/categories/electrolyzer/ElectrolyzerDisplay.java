package mopsy.productions.nexo.REICompat.categories.electrolyzer;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mopsy.productions.nexo.REICompat.NEXODisplay;
import mopsy.productions.nexo.recipes.ElectrolyzerRecipe;
import mopsy.productions.nexo.recipes.NEXORecipe;

import static mopsy.productions.nexo.Main.modid;

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
