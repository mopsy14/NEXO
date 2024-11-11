package mopsy.productions.nexo.REICompat.categories.press;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import mopsy.productions.nexo.REICompat.NEXODisplay;
import mopsy.productions.nexo.recipes.NEXORecipe;
import mopsy.productions.nexo.recipes.PressRecipe;
import org.jetbrains.annotations.Nullable;

import static mopsy.productions.nexo.Main.modid;

public class PressDisplay extends NEXODisplay {

    public PressRecipe recipe;

    public PressDisplay(NEXORecipe recipe) {
        super(recipe);
        this.recipe= (PressRecipe) recipe;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"press");
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return buildSerializer(d->d.recipe, PressDisplay::new);
    }
}
