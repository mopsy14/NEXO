package mopsy.productions.nexo.recipes;

import com.google.gson.JsonObject;
import mopsy.productions.nexo.util.NFluidStack;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.List;

public class PressRecipe extends NEXORecipe{
    public PressRecipe(Identifier id, List<Ingredient> inputs, List<ItemStack> outputs, List<NFluidStack> inputFluids, List<NFluidStack> outputFluids, List<String> additionalInfo) {
        super(id, inputs, outputs, inputFluids, outputFluids, additionalInfo);
    }
    public PressRecipe(NEXORecipe recipe) {
        super(recipe.id, recipe.inputs, recipe.outputs, recipe.inputFluids, recipe.outputFluids, recipe.additionalInfo);
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<PressRecipe>{
        private Type() {}
        public static final PressRecipe.Type INSTANCE = new PressRecipe.Type();
        public static final String ID = "press";
    }
    public static class Serializer implements RecipeSerializer<PressRecipe> {
        public static final PressRecipe.Serializer INSTANCE = new PressRecipe.Serializer();

        @Override
        public PressRecipe read(Identifier id, JsonObject json) {
            return new PressRecipe(NEXORecipe.Serializer.INSTANCE.read(id,json));
        }

        @Override
        public PressRecipe read(Identifier id, PacketByteBuf buf) {
            return new PressRecipe(NEXORecipe.Serializer.INSTANCE.read(id,buf));
        }

        @Override
        public void write(PacketByteBuf buf, PressRecipe recipe) {
            NEXORecipe.Serializer.INSTANCE.write(buf,recipe);
        }
    }
}
