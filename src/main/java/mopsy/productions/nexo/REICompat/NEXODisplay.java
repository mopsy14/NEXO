package mopsy.productions.nexo.REICompat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mopsy.productions.nexo.recipes.NEXORecipe;
import mopsy.productions.nexo.util.NFluidStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NEXODisplay implements Display {
    public NEXORecipe recipe;
    public NEXODisplay(NEXORecipe recipe){
        this.recipe=  recipe;

    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return null;
    }
    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> res = new ArrayList<>();
        for(Ingredient ingredient : recipe.inputs){
            for(ItemStack stack : ingredient.getMatchingStacks()){
                res.add(EntryIngredients.of(stack));
            }
        }
        for(NFluidStack stack : recipe.inputFluids){
            res.add(EntryIngredients.of(stack.toFluidStack()));
        }
        return res;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        List<EntryIngredient> res = new ArrayList<>();
        for(ItemStack stack : recipe.outputs){
            res.add(EntryIngredients.of(stack));
        }
        for(NFluidStack stack : recipe.outputFluids){
            res.add(EntryIngredients.of(stack.toFluidStack()));
        }

        return res;
    }

    @Override
    public Optional<Identifier> getDisplayLocation() {
        return Optional.of(recipe.id);
    }
}
