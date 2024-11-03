package mopsy.productions.nexo.REICompat.categories.ammonia_synthesizer;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mopsy.productions.nexo.REICompat.NEXODisplay;
import mopsy.productions.nexo.recipes.AmmoniaSynthesizerRecipe;
import mopsy.productions.nexo.recipes.NEXORecipe;

import static mopsy.productions.nexo.Main.modid;


public class AmmoniaSynthesizerDisplay extends NEXODisplay {

    public AmmoniaSynthesizerRecipe recipe;

    public AmmoniaSynthesizerDisplay(NEXORecipe recipe) {
        super(recipe);
        this.recipe= (AmmoniaSynthesizerRecipe) recipe;
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"ammonia_synthesizer");
    }
}
