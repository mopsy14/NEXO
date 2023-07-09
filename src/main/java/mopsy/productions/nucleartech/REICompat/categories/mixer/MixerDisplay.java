package mopsy.productions.nucleartech.REICompat.categories.mixer;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import mopsy.productions.nucleartech.recipes.MixerRecipe;
import mopsy.productions.nucleartech.util.NFluidStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static mopsy.productions.nucleartech.Main.modid;

@SuppressWarnings("UnstableApiUsage")
public class MixerDisplay implements Display {

    public MixerRecipe recipe;


    public MixerDisplay(MixerRecipe recipe){
        this.recipe=  recipe;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CategoryIdentifier.of(modid,"mixer");
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
