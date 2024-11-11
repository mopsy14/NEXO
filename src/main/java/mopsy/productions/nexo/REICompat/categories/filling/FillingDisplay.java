package mopsy.productions.nexo.REICompat.categories.filling;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import mopsy.productions.nexo.REICompat.NEXODisplay;
import mopsy.productions.nexo.recipes.FillingRecipe;
import mopsy.productions.nexo.recipes.NEXORecipe;
import org.jetbrains.annotations.Nullable;

import static mopsy.productions.nexo.Main.modid;


public class FillingDisplay extends NEXODisplay {

    public FillingRecipe recipe;

    public FillingDisplay(NEXORecipe recipe) {
        super(recipe);
        this.recipe= (FillingRecipe) recipe;
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"filling");
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
    public static final DisplaySerializer<FillingDisplay> SERIALIZER = buildSerializer(d->d.recipe,FillingDisplay::new);
}
