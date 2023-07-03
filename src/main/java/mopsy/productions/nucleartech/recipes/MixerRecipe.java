package mopsy.productions.nucleartech.recipes;

import com.google.gson.JsonObject;
import mopsy.productions.nucleartech.ModBlocks.entities.machines.MixerEntity;
import mopsy.productions.nucleartech.util.NFluidStack;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.List;

public class MixerRecipe extends NEXORecipe{
    public MixerRecipe(Identifier id, List<Ingredient> inputs, List<ItemStack> outputs, List<NFluidStack> inputFluids, List<NFluidStack> outputFluids, List<String> additionalInfo) {
        super(id, inputs, outputs, inputFluids, outputFluids, additionalInfo);
    }
    public MixerRecipe(NEXORecipe recipe){
        super(recipe.id,recipe.inputs,recipe.outputs,recipe.inputFluids,recipe.outputFluids,recipe.additionalInfo);
    }

    @Override
    public boolean hasRecipe(BlockEntity blockEntity) {
        if(blockEntity instanceof MixerEntity mixer) {
            return super.hasRecipe(blockEntity) && mixer.heat>=getMinHeat() && mixer.heat<=getMaxHeat();
        }

        return false;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
    public static class Type implements RecipeType<MixerRecipe>{
        private Type() {}
        public static final MixerRecipe.Type INSTANCE = new MixerRecipe.Type();
        public static final String ID = "mixer";
    }
    public static class Serializer implements RecipeSerializer<MixerRecipe> {
        public static final MixerRecipe.Serializer INSTANCE = new MixerRecipe.Serializer();

        @Override
        public MixerRecipe read(Identifier id, JsonObject json) {
            return new MixerRecipe(NEXORecipe.Serializer.INSTANCE.read(id,json));
        }

        @Override
        public MixerRecipe read(Identifier id, PacketByteBuf buf) {
            return new MixerRecipe(NEXORecipe.Serializer.INSTANCE.read(id,buf));
        }

        @Override
        public void write(PacketByteBuf buf, MixerRecipe recipe) {
            NEXORecipe.Serializer.INSTANCE.write(buf,recipe);
        }
    }
    public int getMinHeat(){
        if(super.additionalInfo.size()> 0)
            return Integer.parseInt(super.additionalInfo.get(0));
        return 0;
    }
    public int getMaxHeat(){
        if(super.additionalInfo.size()> 1)
            return Integer.parseInt(super.additionalInfo.get(1));
        return 0;
    }
}
