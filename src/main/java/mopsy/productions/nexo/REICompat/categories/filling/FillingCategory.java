package mopsy.productions.nexo.REICompat.categories.filling;

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
import mopsy.productions.nexo.registry.ModdedBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

import static mopsy.productions.nexo.Main.modid;

public class FillingCategory implements DisplayCategory<FillingDisplay> {
    @Override
    public CategoryIdentifier<? extends FillingDisplay> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"filling");
    }

    @Override
    public Text getTitle() {
        return Text.of("Filling");
    }
    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModdedBlocks.BlockItems.get("tank_mk1"));
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
    public List<Widget> setupDisplay(FillingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        //input fluids:
        List<EntryIngredient> entries = getFluidEntries(display.getInputEntries());
        widgets.add(Widgets.createSlot(new Point(65+ bounds.x,5+ bounds.y)).markInput().entries(entries.get(0)));
        //input item:
        entries = getItemEntries(display.getInputEntries());
        widgets.add(Widgets.createSlot(new Point(39+ bounds.x,24+ bounds.y)).markInput().entries(entries.get(0)));
        //output item:
        entries = getItemEntries(display.getOutputEntries());
        widgets.add(Widgets.createSlot(new Point(91+ bounds.x,24+ bounds.y)).markInput().entries(entries.get(0)));

        widgets.add(Widgets.createArrow(new Point(62+bounds.x,24+ bounds.y)).animationDurationMS(10000));
        return widgets;
    }
}
