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

public class CrusherRecipe extends NEXORecipe{
    public CrusherRecipe(Identifier id, List<Ingredient> inputs, List<ItemStack> outputs, List<NFluidStack> inputFluids, List<NFluidStack> outputFluids, List<String> additionalInfo) {
        super(id, inputs, outputs, inputFluids, outputFluids, additionalInfo);
    }
    public CrusherRecipe(NEXORecipe recipe) {
        super(recipe.id, recipe.inputs, recipe.outputs, recipe.inputFluids, recipe.outputFluids, recipe.additionalInfo);
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<CrusherRecipe>{
        private Type() {}
        public static final CrusherRecipe.Type INSTANCE = new CrusherRecipe.Type();
        public static final String ID = "crusher";
    }
    public static class Serializer implements RecipeSerializer<CrusherRecipe> {
        public static final CrusherRecipe.Serializer INSTANCE = new CrusherRecipe.Serializer();

        @Override
        public CrusherRecipe read(Identifier id, JsonObject json) {
            return new CrusherRecipe(NEXORecipe.Serializer.INSTANCE.read(id,json));
        }

        @Override
        public CrusherRecipe read(Identifier id, PacketByteBuf buf) {
            return new CrusherRecipe(NEXORecipe.Serializer.INSTANCE.read(id,buf));
        }

        @Override
        public void write(PacketByteBuf buf, CrusherRecipe recipe) {
            NEXORecipe.Serializer.INSTANCE.write(buf,recipe);
        }
    }
}
