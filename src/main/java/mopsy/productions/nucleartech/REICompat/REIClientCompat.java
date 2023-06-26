package mopsy.productions.nucleartech.REICompat;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import mopsy.productions.nucleartech.REICompat.categories.CrushingCategory;
import mopsy.productions.nucleartech.REICompat.categories.CrushingDisplay;
import mopsy.productions.nucleartech.recipes.CrusherRecipe;

public class REIClientCompat implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CrushingCategory());
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(CrusherRecipe.class, CrusherRecipe.Type.INSTANCE, CrushingDisplay::new);
    }
}
