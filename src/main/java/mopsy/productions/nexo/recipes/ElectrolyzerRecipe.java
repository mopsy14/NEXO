package mopsy.productions.nexo.recipes;

import com.mojang.serialization.MapCodec;
import mopsy.productions.nexo.util.NFluidStack;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
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
    public String getTypeID(){
        return ElectrolyzerRecipe.Type.ID;
    }

    @Override
    public RecipeSerializer<NEXORecipe> getSerializer() {
        return ElectrolyzerRecipe.Serializer.INSTANCE;
    }
    @Override
    public RecipeType<ElectrolyzerRecipe> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<ElectrolyzerRecipe>{
        private Type() {}
        public static final ElectrolyzerRecipe.Type INSTANCE = new ElectrolyzerRecipe.Type();
        public static final String ID = "electrolyzer";
    }
    public static class Serializer implements RecipeSerializer<NEXORecipe> {
        public static final ElectrolyzerRecipe.Serializer INSTANCE = new ElectrolyzerRecipe.Serializer();

        @Override
        public MapCodec<NEXORecipe> codec() {
            return NEXORecipe.Serializer.CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, NEXORecipe> packetCodec() {
            return NEXORecipe.Serializer.PACKET_CODEC;
        }
    }
    public long getRequiredPower(){
        if(!super.additionalInfo.isEmpty())
            return Long.parseLong(super.additionalInfo.getFirst());
        return 0;
    }
}
