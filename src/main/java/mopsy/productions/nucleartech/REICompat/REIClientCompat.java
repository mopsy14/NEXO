package mopsy.productions.nucleartech.REICompat;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import mopsy.productions.nucleartech.REICompat.categories.CrushingCategory;

public class REIClientCompat implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CrushingCategory());
    }
}
