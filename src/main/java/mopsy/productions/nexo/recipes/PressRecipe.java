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

public class PressRecipe extends NEXORecipe{
    public PressRecipe(Identifier id, List<Ingredient> inputs, List<ItemStack> outputs, List<NFluidStack> inputFluids, List<NFluidStack> outputFluids, List<String> additionalInfo) {
        super(id, inputs, outputs, inputFluids, outputFluids, additionalInfo);
    }
    public PressRecipe(NEXORecipe recipe) {
        super(recipe.id, recipe.inputs, recipe.outputs, recipe.inputFluids, recipe.outputFluids, recipe.additionalInfo);
    }

    @Override
    public String getTypeID(){
        return Type.ID;
    }

    @Override
    public RecipeSerializer<NEXORecipe> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<PressRecipe> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<PressRecipe>{
        private Type() {}
        public static final PressRecipe.Type INSTANCE = new PressRecipe.Type();
        public static final String ID = "press";
    }
    public static class Serializer implements RecipeSerializer<NEXORecipe> {
        public static final PressRecipe.Serializer INSTANCE = new PressRecipe.Serializer();

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
