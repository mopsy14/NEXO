package mopsy.productions.nexo.REICompat.categories.centrifuge;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mopsy.productions.nexo.registry.ModdedBlocks;
import mopsy.productions.nexo.registry.ModdedItems;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

import static mopsy.productions.nexo.Main.modid;

public class CentrifugeCategory implements DisplayCategory<CentrifugeDisplay> {
    @Override
    public CategoryIdentifier<? extends CentrifugeDisplay> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"centrifuge");
    }

    @Override
    public Text getTitle() {
        return Text.of("Centrifuging");
    }
    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModdedBlocks.BlockItems.get("centrifuge"));
    }

    @Override
    public List<Widget> setupDisplay(CentrifugeDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        widgets.add(Widgets.createSlot(new Point(65 + bounds.x, 41 + bounds.y)).entries(
                display.recipe.needsHeatResistant()?
                        EntryIngredients.of(new ItemStack(ModdedItems.Items.get("heat_resistant_test_tube"),4)) :
                        EntryIngredients.of(new ItemStack(ModdedItems.Items.get("test_tube"),4))
        ));

        //input fluids:
        widgets.add(Widgets.createSlot(new Point(33+ bounds.x,19+ bounds.y)).markInput().entries(display.getInputEntries().get(0)));
        //output fluids:
        widgets.add(Widgets.createSlot(new Point(92+ bounds.x,19+ bounds.y)).markInput().entries(display.getOutputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(111+ bounds.x,19+ bounds.y)).markOutput().entries(display.getOutputEntries().get(1)));

        widgets.add(Widgets.createArrow(new Point(61+bounds.x,20+ bounds.y)).animationDurationMS(10000));
        return widgets;
    }
}
