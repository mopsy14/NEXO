package mopsy.productions.nexo.recipes;

import mopsy.productions.nexo.ModBlocks.entities.machines.AirSeparatorEntity;
import mopsy.productions.nexo.util.NFluidStack;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.List;

public class AirSeparatorRecipe extends NEXORecipe{
    public AirSeparatorRecipe(Identifier id, List<Ingredient> inputs, List<ItemStack> outputs, List<NFluidStack> inputFluids, List<NFluidStack> outputFluids, List<String> additionalInfo) {
        super(id, inputs, outputs, inputFluids, outputFluids, additionalInfo);
    }
    public AirSeparatorRecipe(NEXORecipe recipe){
        super(recipe.id,recipe.inputs,recipe.outputs,recipe.inputFluids,recipe.outputFluids,recipe.additionalInfo);
    }

    @Override
    public boolean canOutput(BlockEntity blockEntity) {
        return !(((AirSeparatorEntity)blockEntity).fluidStorages.get(0).amount == 648000 && ((AirSeparatorEntity)blockEntity).fluidStorages.get(1).amount == 648000);
    }


    @Override
    public RecipeSerializer<NEXORecipe> getSerializer() {
        return AirSeparatorRecipe.Serializer.INSTANCE;
    }
    @Override
    public String getTypeID(){
        return AirSeparatorRecipe.Type.ID;
    }

    @Override
    public RecipeType<AirSeparatorRecipe> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AirSeparatorRecipe>{
        private Type() {}
        public static final AirSeparatorRecipe.Type INSTANCE = new AirSeparatorRecipe.Type();
        public static final String ID = "air_separator";
    }
}
