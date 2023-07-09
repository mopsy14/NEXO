package mopsy.productions.nucleartech.REICompat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mopsy.productions.nucleartech.recipes.NEXORecipe;
import mopsy.productions.nucleartech.util.NFluidStack;
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
    private Identifier getID(){
        return new Identifier("");
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(getID());
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

    public static NEXODisplay fromRecipe(NEXORecipe recipe){
        return null;
    }
}