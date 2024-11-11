package mopsy.productions.nexo.REICompat.categories.centrifuge;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import mopsy.productions.nexo.REICompat.NEXODisplay;
import mopsy.productions.nexo.recipes.CentrifugeRecipe;
import mopsy.productions.nexo.recipes.NEXORecipe;
import org.jetbrains.annotations.Nullable;

import static mopsy.productions.nexo.Main.modid;


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

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return buildSerializer(d->d.recipe, CentrifugeDisplay::new);
    }
}
