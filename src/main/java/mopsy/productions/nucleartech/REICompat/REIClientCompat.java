package mopsy.productions.nucleartech.REICompat;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mopsy.productions.nucleartech.REICompat.categories.crusher.CrushingCategory;
import mopsy.productions.nucleartech.REICompat.categories.crusher.CrushingDisplay;
import mopsy.productions.nucleartech.recipes.CrusherRecipe;
import mopsy.productions.nucleartech.registry.ModdedBlocks;

import static mopsy.productions.nucleartech.Main.modid;

public class REIClientCompat implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CrushingCategory());
        registry.addWorkstations(CategoryIdentifier.of(modid,"crushing"), EntryStacks.of(ModdedBlocks.BlockItems.get("crusher")));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(CrusherRecipe.class, CrusherRecipe.Type.INSTANCE, CrushingDisplay::new);
    }
}
