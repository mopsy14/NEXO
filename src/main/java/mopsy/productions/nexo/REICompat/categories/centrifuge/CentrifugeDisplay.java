package mopsy.productions.nexo.REICompat.categories.centrifuge;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mopsy.productions.nexo.REICompat.NEXODisplay;
import mopsy.productions.nexo.recipes.CentrifugeRecipe;
import mopsy.productions.nexo.recipes.NEXORecipe;

import static mopsy.productions.nexo.Main.modid;

@SuppressWarnings("UnstableApiUsage")
public class CentrifugeDisplay extends NEXODisplay {

    public CentrifugeRecipe recipe;

    public CentrifugeDisplay(NEXORecipe recipe) {
        super(recipe);
        this.recipe = (CentrifugeRecipe) recipe;
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"centrifuge");
    }
}
