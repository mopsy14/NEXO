package mopsy.productions.nexo.REICompat.categories.mixer;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import mopsy.productions.nexo.REICompat.NEXODisplay;
import mopsy.productions.nexo.recipes.MixerRecipe;
import mopsy.productions.nexo.recipes.NEXORecipe;

import static mopsy.productions.nexo.Main.modid;

@SuppressWarnings("UnstableApiUsage")
public class MixerDisplay extends NEXODisplay {

    public MixerRecipe recipe;

    public MixerDisplay(NEXORecipe recipe) {
        super(recipe);
        this.recipe = (MixerRecipe) recipe;
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"mixer");
    }
}
