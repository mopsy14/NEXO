package mopsy.productions.nexo.REICompat.categories.air_separator;

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

public class AirSeparatorCategory implements DisplayCategory<AirSeparatorDisplay> {
    @Override
    public CategoryIdentifier<? extends AirSeparatorDisplay> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"air_separator");
    }

    @Override
    public Text getTitle() {
        return Text.of("Air Separating");
    }
    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModdedBlocks.BlockItems.get("air_separator"));
    }

    @Override
    public List<Widget> setupDisplay(AirSeparatorDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        //output fluids:
        widgets.add(Widgets.createSlot(new Point(69+ bounds.x,23+ bounds.y)).markInput().entries(display.getOutputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(88+ bounds.x,23+ bounds.y)).markOutput().entries(display.getOutputEntries().get(1)));

        widgets.add(Widgets.createArrow(new Point(38+bounds.x,24+ bounds.y)).animationDurationMS(10000));
        return widgets;
    }
}
