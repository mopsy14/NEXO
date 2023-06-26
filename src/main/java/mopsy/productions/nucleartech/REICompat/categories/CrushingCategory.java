package mopsy.productions.nucleartech.REICompat.categories;

import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mopsy.productions.nucleartech.registry.ModdedBlocks;
import net.minecraft.text.Text;

import static mopsy.productions.nucleartech.Main.modid;

public class CrushingCategory implements DisplayCategory<CrushingDisplay> {
    @Override
    public CategoryIdentifier<? extends CrushingDisplay> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"crushing");
    }

    @Override
    public Text getTitle() {
        return Text.of("Crushing");
    }
    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModdedBlocks.BlockItems.get("crusher"));
    }


}
