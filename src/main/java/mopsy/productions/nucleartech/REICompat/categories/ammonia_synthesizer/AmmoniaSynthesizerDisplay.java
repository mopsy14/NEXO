package mopsy.productions.nucleartech.REICompat.categories.ammonia_synthesizer;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mopsy.productions.nucleartech.REICompat.NEXODisplay;
import mopsy.productions.nucleartech.recipes.AmmoniaSynthesizerRecipe;
import mopsy.productions.nucleartech.recipes.NEXORecipe;

import static mopsy.productions.nucleartech.Main.modid;

@SuppressWarnings("UnstableApiUsage")
public class AmmoniaSynthesizerDisplay extends NEXODisplay {

    public AmmoniaSynthesizerRecipe recipe;

    public AmmoniaSynthesizerDisplay(NEXORecipe recipe) {
        super(recipe);
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"ammonia_synthesizer");
    }
}
