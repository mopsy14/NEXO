package mopsy.productions.nexo.REICompat.categories.electrolyzer;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import mopsy.productions.nexo.REICompat.NEXODisplay;
import mopsy.productions.nexo.recipes.ElectrolyzerRecipe;
import mopsy.productions.nexo.recipes.NEXORecipe;
import org.jetbrains.annotations.Nullable;

import static mopsy.productions.nexo.Main.modid;


public class ElectrolyzerDisplay extends NEXODisplay {

    public ElectrolyzerRecipe recipe;

    public ElectrolyzerDisplay(NEXORecipe recipe) {
        super(recipe);
        this.recipe= (ElectrolyzerRecipe) recipe;
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"electrolyzer");
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return buildSerializer(d->d.recipe, ElectrolyzerDisplay::new);
    }
}
