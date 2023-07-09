package mopsy.productions.nucleartech.REICompat.categories.press;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mopsy.productions.nucleartech.recipes.PressRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static mopsy.productions.nucleartech.Main.modid;

public class PressDisplay implements Display {

    public PressRecipe recipe;


    public PressDisplay(PressRecipe recipe){
        this.recipe=  recipe;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"press");
    }
    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> res = new ArrayList<>();
        for(Ingredient ingredient : recipe.inputs){
            for(ItemStack stack : ingredient.getMatchingStacks()){
                res.add(EntryIngredients.of(stack));
            }
        }
        return res;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredients.of(recipe.outputs.get(0)));
    }

    @Override
    public Optional<Identifier> getDisplayLocation() {
        return Optional.of(recipe.id);
    }
}
