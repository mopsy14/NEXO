package mopsy.productions.nexo.REICompat.categories.ammonia_synthesizer;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import mopsy.productions.nexo.REICompat.NEXODisplay;
import mopsy.productions.nexo.recipes.AmmoniaSynthesizerRecipe;
import mopsy.productions.nexo.recipes.NEXORecipe;
import org.jetbrains.annotations.Nullable;

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

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
    public static final DisplaySerializer<AmmoniaSynthesizerDisplay> SERIALIZER = buildSerializer(d->d.recipe,AmmoniaSynthesizerDisplay::new);
}
