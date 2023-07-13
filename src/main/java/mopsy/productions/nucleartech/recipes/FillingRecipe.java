package mopsy.productions.nucleartech.recipes;

import com.google.gson.JsonObject;
import mopsy.productions.nucleartech.util.NFluidStack;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.List;

public class FillingRecipe extends NEXORecipe{
    public FillingRecipe(Identifier id, List<Ingredient> inputs, List<ItemStack> outputs, List<NFluidStack> inputFluids, List<NFluidStack> outputFluids, List<String> additionalInfo) {
        super(id, inputs, outputs, inputFluids, outputFluids, additionalInfo);
    }
    public FillingRecipe(NEXORecipe recipe){
        super(recipe.id,recipe.inputs,recipe.outputs,recipe.inputFluids,recipe.outputFluids,recipe.additionalInfo);
    }
    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<FillingRecipe>{
        private Type() {}
        public static final FillingRecipe.Type INSTANCE = new FillingRecipe.Type();
        public static final String ID = "filling";
    }
    public static class Serializer implements RecipeSerializer<FillingRecipe> {
        public static final FillingRecipe.Serializer INSTANCE = new FillingRecipe.Serializer();

        @Override
        public FillingRecipe read(Identifier id, JsonObject json) {
            return new FillingRecipe(NEXORecipe.Serializer.INSTANCE.read(id,json));
        }

        @Override
        public FillingRecipe read(Identifier id, PacketByteBuf buf) {
            return new FillingRecipe(NEXORecipe.Serializer.INSTANCE.read(id,buf));
        }

        @Override
        public void write(PacketByteBuf buf, FillingRecipe recipe) {
            NEXORecipe.Serializer.INSTANCE.write(buf,recipe);
        }
    }
}
