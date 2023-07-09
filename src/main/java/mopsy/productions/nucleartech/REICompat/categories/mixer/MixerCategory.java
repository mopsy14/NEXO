package mopsy.productions.nucleartech.REICompat.categories.mixer;

import dev.architectury.fluid.FluidStack;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mopsy.productions.nucleartech.registry.ModdedBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

import static mopsy.productions.nucleartech.Main.modid;

public class MixerCategory implements DisplayCategory<MixerDisplay> {
    @Override
    public CategoryIdentifier<? extends MixerDisplay> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"mixer");
    }

    @Override
    public Text getTitle() {
        return Text.of("Mixing");
    }
    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModdedBlocks.BlockItems.get("mixer"));
    }

    private List<EntryIngredient> getFluidEntries(List<EntryIngredient> src){
        List<EntryIngredient> res = new ArrayList<>();
        for(EntryIngredient ingredient : src){
            if(ingredient.get(0).getValue() instanceof FluidStack){
                res.add(ingredient);
            }
        }
        while (res.size()<3) {
            res.add(EntryIngredients.of(FluidStack.empty()));
        }
        return res;
    }
    private List<EntryIngredient> getItemEntries(List<EntryIngredient> src){
        List<EntryIngredient> res = new ArrayList<>();
        for(EntryIngredient ingredient : src){
            if(ingredient.get(0).getValue() instanceof ItemStack){
                res.add(ingredient);
            }
        }
        while (res.size() < 4) {
            res.add(EntryIngredients.of(ItemStack.EMPTY));
        }
        return res;
    }

    @Override
    public List<Widget> setupDisplay(MixerDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        //input fluids:
        List<EntryIngredient> entries = getFluidEntries(display.getInputEntries());
        widgets.add(Widgets.createSlot(new Point(5+ bounds.x,5+ bounds.y)).markInput().entries(entries.get(0)));
        widgets.add(Widgets.createSlot(new Point(24+ bounds.x,5+ bounds.y)).markOutput().entries(entries.get(1)));
        widgets.add(Widgets.createSlot(new Point(43+ bounds.x,5+ bounds.y)).markOutput().entries(entries.get(2)));
        //input slots:
        entries = getItemEntries(display.getInputEntries());
        widgets.add(Widgets.createSlot(new Point(5+ bounds.x,26+ bounds.y)).markInput().entries(entries.get(0)));
        widgets.add(Widgets.createSlot(new Point(24+ bounds.x,26+ bounds.y)).markOutput().entries(entries.get(1)));
        widgets.add(Widgets.createSlot(new Point(5+ bounds.x,45+ bounds.y)).markOutput().entries(entries.get(2)));
        widgets.add(Widgets.createSlot(new Point(24+ bounds.x,45+ bounds.y)).markOutput().entries(entries.get(3)));

        //output fluids:
        entries = getFluidEntries(display.getOutputEntries());
        widgets.add(Widgets.createSlot(new Point(91+ bounds.x,5+ bounds.y)).markInput().entries(entries.get(0)));
        widgets.add(Widgets.createSlot(new Point(110+ bounds.x,5+ bounds.y)).markOutput().entries(entries.get(1)));
        widgets.add(Widgets.createSlot(new Point(129+ bounds.x,5+ bounds.y)).markOutput().entries(entries.get(2)));
        //output slots:
        entries = getItemEntries(display.getOutputEntries());
        widgets.add(Widgets.createSlot(new Point(110+ bounds.x,25+ bounds.y)).markInput().entries(entries.get(0)));
        widgets.add(Widgets.createSlot(new Point(129+ bounds.x,25+ bounds.y)).markOutput().entries(entries.get(1)));
        widgets.add(Widgets.createSlot(new Point(110+ bounds.x,44+ bounds.y)).markOutput().entries(entries.get(2)));
        widgets.add(Widgets.createSlot(new Point(129+ bounds.x,44+ bounds.y)).markOutput().entries(entries.get(3)));

        widgets.add(Widgets.createArrow(new Point(63+bounds.x,24+ bounds.y)).animationDurationMS(10000));
        return widgets;
    }
}
