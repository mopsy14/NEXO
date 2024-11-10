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

public class AmmoniaSynthesizerRecipe extends NEXORecipe{
    public AmmoniaSynthesizerRecipe(Identifier id, List<Ingredient> inputs, List<ItemStack> outputs, List<NFluidStack> inputFluids, List<NFluidStack> outputFluids, List<String> additionalInfo) {
        super(id, inputs, outputs, inputFluids, outputFluids, additionalInfo);
    }
    public AmmoniaSynthesizerRecipe(NEXORecipe recipe){
        super(recipe.id,recipe.inputs,recipe.outputs,recipe.inputFluids,recipe.outputFluids,recipe.additionalInfo);
    }
    @Override
    public String getTypeID(){
        return AmmoniaSynthesizerRecipe.Type.ID;
    }

    @Override
    public RecipeSerializer<NEXORecipe> getSerializer() {
        return AmmoniaSynthesizerRecipe.Serializer.INSTANCE;
    }
    @Override
    public RecipeType<AmmoniaSynthesizerRecipe> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<AmmoniaSynthesizerRecipe>{
        private Type() {}
        public static final AmmoniaSynthesizerRecipe.Type INSTANCE = new AmmoniaSynthesizerRecipe.Type();
        public static final String ID = "ammonia_synthesizer";
    }
    public static class Serializer implements RecipeSerializer<NEXORecipe> {
        public static final AmmoniaSynthesizerRecipe.Serializer INSTANCE = new AmmoniaSynthesizerRecipe.Serializer();

        @Override
        public MapCodec<NEXORecipe> codec() {
            return NEXORecipe.Serializer.CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, NEXORecipe> packetCodec() {
            return NEXORecipe.Serializer.PACKET_CODEC;
        }
    }
}
