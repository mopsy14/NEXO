package mopsy.productions.nucleartech.REICompat.categories.press;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mopsy.productions.nucleartech.registry.ModdedBlocks;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

import static mopsy.productions.nucleartech.Main.modid;

public class PressCategory implements DisplayCategory<PressDisplay> {
    @Override
    public CategoryIdentifier<? extends PressDisplay> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"press");
    }

    @Override
    public Text getTitle() {
        return Text.of("Pressing");
    }
    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModdedBlocks.BlockItems.get("press"));
    }

    @Override
    public List<Widget> setupDisplay(PressDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createSlot(new Point(33+ bounds.x,24+ bounds.y)).markInput().entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(99+ bounds.x,24+ bounds.y)).markOutput().entries(display.getOutputEntries().get(0)));
        widgets.add(Widgets.createArrow(new Point(63+bounds.x,24+ bounds.y)).animationDurationMS(10000));
        return widgets;
    }
}
