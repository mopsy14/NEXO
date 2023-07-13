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

public class ElectrolyzerRecipe extends NEXORecipe{
    public ElectrolyzerRecipe(Identifier id, List<Ingredient> inputs, List<ItemStack> outputs, List<NFluidStack> inputFluids, List<NFluidStack> outputFluids, List<String> additionalInfo) {
        super(id, inputs, outputs, inputFluids, outputFluids, additionalInfo);
    }
    public ElectrolyzerRecipe(NEXORecipe recipe){
        super(recipe.id,recipe.inputs,recipe.outputs,recipe.inputFluids,recipe.outputFluids,recipe.additionalInfo);
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
    public static class Serializer implements RecipeSerializer<ElectrolyzerRecipe> {
        public static final ElectrolyzerRecipe.Serializer INSTANCE = new ElectrolyzerRecipe.Serializer();

        @Override
        public ElectrolyzerRecipe read(Identifier id, JsonObject json) {
            return new ElectrolyzerRecipe(NEXORecipe.Serializer.INSTANCE.read(id,json));
        }

        @Override
        public ElectrolyzerRecipe read(Identifier id, PacketByteBuf buf) {
            return new ElectrolyzerRecipe(NEXORecipe.Serializer.INSTANCE.read(id,buf));
        }

        @Override
        public void write(PacketByteBuf buf, ElectrolyzerRecipe recipe) {
            NEXORecipe.Serializer.INSTANCE.write(buf,recipe);
        }
    }
    public long getRequiredPower(){
        if(super.additionalInfo.size()>0)
            return Long.parseLong(super.additionalInfo.get(0));
        return 0;
    }
}
