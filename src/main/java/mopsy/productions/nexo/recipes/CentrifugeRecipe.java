package mopsy.productions.nexo.recipes;

import com.mojang.serialization.MapCodec;
import mopsy.productions.nexo.registry.ModdedItems;
import mopsy.productions.nexo.util.NFluidStack;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.List;

public class CentrifugeRecipe extends NEXORecipe{
    public CentrifugeRecipe(Identifier id, List<Ingredient> inputs, List<ItemStack> outputs, List<NFluidStack> inputFluids, List<NFluidStack> outputFluids, List<String> additionalInfo) {
        super(id, inputs, outputs, inputFluids, outputFluids, additionalInfo);
    }
    public CentrifugeRecipe(NEXORecipe recipe){
        super(recipe.id,recipe.inputs,recipe.outputs,recipe.inputFluids,recipe.outputFluids,recipe.additionalInfo);
    }

    @Override
    public boolean hasRecipe(BlockEntity blockEntity) {
        ItemStack stack = ((Inventory)blockEntity).getStack(6);
        return super.hasRecipe(blockEntity)&&stack.getCount()==4&&
                (needsHeatResistant()? stack.getItem()==ModdedItems.Items.get("heat_resistant_test_tube") : stack.getItem()==ModdedItems.Items.get("test_tube"));
    }

    @Override
    public String getTypeID(){
        return CentrifugeRecipe.Type.ID;
    }

    @Override
    public RecipeSerializer<NEXORecipe> getSerializer() {
        return CentrifugeRecipe.Serializer.INSTANCE;
    }
    @Override
    public RecipeType<CentrifugeRecipe> getType() {
        return CentrifugeRecipe.Type.INSTANCE;
    }
    public static class Type implements RecipeType<CentrifugeRecipe>{
        private Type() {}
        public static final CentrifugeRecipe.Type INSTANCE = new CentrifugeRecipe.Type();
        public static final String ID = "centrifuge";
    }
    public static class Serializer implements RecipeSerializer<NEXORecipe> {
        public static final CentrifugeRecipe.Serializer INSTANCE = new CentrifugeRecipe.Serializer();

        @Override
        public MapCodec<NEXORecipe> codec() {
            return NEXORecipe.Serializer.CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, NEXORecipe> packetCodec() {
            return NEXORecipe.Serializer.PACKET_CODEC;
        }
    }
    public boolean needsHeatResistant(){
        if(!super.additionalInfo.isEmpty())
            return super.additionalInfo.getFirst().equals("true");
        return false;
    }
}
