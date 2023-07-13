package mopsy.productions.nexo.REICompat.categories.crusher;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mopsy.productions.nexo.registry.ModdedBlocks;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

import static mopsy.productions.nexo.Main.modid;

public class CrushingCategory implements DisplayCategory<CrushingDisplay> {
    @Override
    public CategoryIdentifier<? extends CrushingDisplay> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"crusher");
    }

    @Override
    public Text getTitle() {
        return Text.of("Crushing");
    }
    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModdedBlocks.BlockItems.get("crusher"));
    }

    @Override
    public List<Widget> setupDisplay(CrushingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createSlot(new Point(33+ bounds.x,24+ bounds.y)).markInput().entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(99+ bounds.x,24+ bounds.y)).markOutput().entries(display.getOutputEntries().get(0)));
        widgets.add(Widgets.createArrow(new Point(63+bounds.x,24+ bounds.y)).animationDurationMS(10000));
        return widgets;
    }
}
