package mopsy.productions.nucleartech.recipes;

import mopsy.productions.nucleartech.util.NFluidStack;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.List;

public class ElectrolyzerRecipe extends NEXORecipe{
    public ElectrolyzerRecipe(Identifier id, List<Ingredient> inputs, List<ItemStack> outputs, List<NFluidStack> inputFluids, List<NFluidStack> outputFluids, List<String> additionalInfo) {
        super(id, inputs, outputs, inputFluids, outputFluids, additionalInfo);
    }
    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<ElectrolyzerRecipe>{
        private Type() {}
        public static final ElectrolyzerRecipe.Type INSTANCE = new ElectrolyzerRecipe.Type();
        public static final String ID = "electrolyzer";
    }
    public long getRequiredPower(){
        return Long.parseLong(super.additionalInfo.get(0));
    }
}
