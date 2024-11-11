package mopsy.productions.nexo.REICompat.categories.mixer;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import mopsy.productions.nexo.REICompat.NEXODisplay;
import mopsy.productions.nexo.recipes.MixerRecipe;
import mopsy.productions.nexo.recipes.NEXORecipe;
import org.jetbrains.annotations.Nullable;

import static mopsy.productions.nexo.Main.modid;


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

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return buildSerializer(d->d.recipe, MixerDisplay::new);
    }
}
