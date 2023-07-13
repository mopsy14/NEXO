package mopsy.productions.nexo.REICompat.categories.ammonia_synthesizer;

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

public class AmmoniaSynthesizerCategory implements DisplayCategory<AmmoniaSynthesizerDisplay> {
    @Override
    public CategoryIdentifier<? extends AmmoniaSynthesizerDisplay> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"ammonia_synthesizer");
    }

    @Override
    public Text getTitle() {
        return Text.of("Ammonia Synthesizing");
    }
    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModdedBlocks.BlockItems.get("ammonia_synthesizer"));
    }

    @Override
    public List<Widget> setupDisplay(AmmoniaSynthesizerDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        //input fluids:
        widgets.add(Widgets.createSlot(new Point(32+ bounds.x,23+ bounds.y)).markInput().entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(51+ bounds.x,23+ bounds.y)).markInput().entries(display.getInputEntries().get(1)));
        //output fluids:
        widgets.add(Widgets.createSlot(new Point(110+ bounds.x,23+ bounds.y)).markInput().entries(display.getOutputEntries().get(0)));

        widgets.add(Widgets.createArrow(new Point(78+bounds.x,24+ bounds.y)).animationDurationMS(10000));
        return widgets;
    }
}
