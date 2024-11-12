package mopsy.productions.nexo.REICompat;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mopsy.productions.nexo.REICompat.categories.air_separator.AirSeparatorCategory;
import mopsy.productions.nexo.REICompat.categories.air_separator.AirSeparatorDisplay;
import mopsy.productions.nexo.REICompat.categories.ammonia_synthesizer.AmmoniaSynthesizerCategory;
import mopsy.productions.nexo.REICompat.categories.ammonia_synthesizer.AmmoniaSynthesizerDisplay;
import mopsy.productions.nexo.REICompat.categories.centrifuge.CentrifugeCategory;
import mopsy.productions.nexo.REICompat.categories.centrifuge.CentrifugeDisplay;
import mopsy.productions.nexo.REICompat.categories.crusher.CrushingCategory;
import mopsy.productions.nexo.REICompat.categories.crusher.CrushingDisplay;
import mopsy.productions.nexo.REICompat.categories.electrolyzer.ElectrolyzerCategory;
import mopsy.productions.nexo.REICompat.categories.electrolyzer.ElectrolyzerDisplay;
import mopsy.productions.nexo.REICompat.categories.filling.FillingCategory;
import mopsy.productions.nexo.REICompat.categories.filling.FillingDisplay;
import mopsy.productions.nexo.REICompat.categories.mixer.MixerCategory;
import mopsy.productions.nexo.REICompat.categories.mixer.MixerDisplay;
import mopsy.productions.nexo.REICompat.categories.press.PressCategory;
import mopsy.productions.nexo.REICompat.categories.press.PressDisplay;
import mopsy.productions.nexo.recipes.*;
import mopsy.productions.nexo.registry.ModdedBlocks;

import java.util.function.Function;

import static mopsy.productions.nexo.Main.modid;

public class REIClientCompat implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CrushingCategory());
        registry.addWorkstations(CategoryIdentifier.of(modid,"crusher"), EntryStacks.of(ModdedBlocks.BlockItems.get("crusher")));
        registry.add(new PressCategory());
        registry.addWorkstations(CategoryIdentifier.of(modid,"press"), EntryStacks.of(ModdedBlocks.BlockItems.get("press")));
        registry.add(new MixerCategory());
        registry.addWorkstations(CategoryIdentifier.of(modid,"mixer"), EntryStacks.of(ModdedBlocks.BlockItems.get("mixer")));
        registry.add(new CentrifugeCategory());
        registry.addWorkstations(CategoryIdentifier.of(modid,"centrifuge"), EntryStacks.of(ModdedBlocks.BlockItems.get("centrifuge")));
        registry.add(new ElectrolyzerCategory());
        registry.addWorkstations(CategoryIdentifier.of(modid,"electrolyzer"), EntryStacks.of(ModdedBlocks.BlockItems.get("electrolyzer")));
        registry.add(new AirSeparatorCategory());
        registry.addWorkstations(CategoryIdentifier.of(modid,"air_separator"), EntryStacks.of(ModdedBlocks.BlockItems.get("air_separator")));
        registry.add(new AmmoniaSynthesizerCategory());
        registry.addWorkstations(CategoryIdentifier.of(modid,"ammonia_synthesizer"), EntryStacks.of(ModdedBlocks.BlockItems.get("ammonia_synthesizer")));
        registry.add(new FillingCategory());
        registry.addWorkstations(CategoryIdentifier.of(modid,"filling"), EntryStacks.of(ModdedBlocks.BlockItems.get("tank_mk1")));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        regDisplayFiller(registry,CrushingDisplay::new,CrusherRecipe.class);
        regDisplayFiller(registry,PressDisplay::new,PressRecipe.class);
        regDisplayFiller(registry,MixerDisplay::new,MixerRecipe.class);
        regDisplayFiller(registry,CentrifugeDisplay::new,CentrifugeRecipe.class);
        regDisplayFiller(registry,ElectrolyzerDisplay::new,ElectrolyzerRecipe.class);
        regDisplayFiller(registry,AirSeparatorDisplay::new,AirSeparatorRecipe.class);
        regDisplayFiller(registry,AmmoniaSynthesizerDisplay::new,AmmoniaSynthesizerRecipe.class);
        regDisplayFiller(registry,FillingDisplay::new,FillingRecipe.class);
    }
    private <R extends NEXORecipe, D extends NEXODisplay> void regDisplayFiller(DisplayRegistry registry, Function<R,D> constructor, Class<R> recipeClass){
        registry.registerFillerWithReason((((o, displayAdditionReasons) -> recipeClass.isInstance(o))),((o, displayAdditionReasons) -> constructor.apply((R) o)));
    }
}
