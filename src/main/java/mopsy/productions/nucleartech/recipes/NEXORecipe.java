package mopsy.productions.nucleartech.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mopsy.productions.nucleartech.util.NFluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class NEXORecipe implements Recipe<SimpleInventory> {
    public final Identifier id;
    public final List<Ingredient> inputs;
    public final List<ItemStack> outputs;
    public final List<NFluidStack> inputFluids;
    public final List<NFluidStack> outputFluids;


    public NEXORecipe(Identifier id, List<Ingredient> inputs, List<ItemStack> outputs, List<NFluidStack> inputFluids, List<NFluidStack> outputFluids){
        this.id= id;
        this.inputs = inputs;
        this.outputs = outputs;
        this.inputFluids = inputFluids;
        this.outputFluids = outputFluids;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        return false;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return null;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return null;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<NEXORecipe>{
        public static final NEXORecipe.Serializer INSTANCE = new NEXORecipe.Serializer();
        //public static final String ID = "centrifuge";

        @Override
        public NEXORecipe read(Identifier id, JsonObject json) {
            List<Ingredient> inputs = new ArrayList<>();
            List<ItemStack> outputs = new ArrayList<>();
            List<NFluidStack> fluidInputs = new ArrayList<>();
            List<NFluidStack> fluidOutputs = new ArrayList<>();

            JsonArray jsonArray = json.getAsJsonArray("item_inputs");
            if(jsonArray!=null) {
                for (JsonElement element : jsonArray){
                    inputs.add(Ingredient.fromJson(element));
                }
            }

            jsonArray = json.getAsJsonArray("item_outputs");
            if(jsonArray!=null) {
                for (JsonElement element : jsonArray){
                    outputs.add(new ItemStack(
                            Registry.ITEM.get(Identifier.tryParse(element.getAsJsonObject().get("item").getAsString())),
                            element.getAsJsonObject().get("count").getAsInt()
                    ));

                }
            }

            jsonArray = json.getAsJsonArray("fluid_inputs");
            if(jsonArray!=null) {
                for (JsonElement element : jsonArray){
                    fluidInputs.add(new NFluidStack(
                            FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(element.getAsJsonObject().get("fluid_type").getAsString()))),
                            element.getAsJsonObject().get("fluid_amount").getAsLong()
                    ));
                }
            }

            jsonArray = json.getAsJsonArray("fluid_outputs");
            if(jsonArray!=null) {
                for (JsonElement element : jsonArray){
                    fluidOutputs.add(new NFluidStack(
                            FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(element.getAsJsonObject().get("fluid_type").getAsString()))),
                            element.getAsJsonObject().get("fluid_amount").getAsLong()
                    ));
                }
            }

            return new NEXORecipe(id,inputs,outputs,fluidInputs,fluidOutputs);
        }

        @Override
        public NEXORecipe read(Identifier id, PacketByteBuf buf) {
            /*
            FluidVariant inputType = FluidVariant.fromPacket(buf);
            long inputAmount = buf.readLong();

            FluidVariant output1Type = FluidVariant.fromPacket(buf);
            long output1Amount = buf.readLong();

            FluidVariant output2Type = FluidVariant.fromPacket(buf);
            long output2Amount = buf.readLong();

            boolean outputHRT = buf.readBoolean();

            return new NEXORecipe(id, inputType, inputAmount, output1Type, output1Amount, output2Type, output2Amount, outputHRT);
             */
            return null;
        }

        @Override
        public void write(PacketByteBuf buf, NEXORecipe recipe) {

            /*recipe.input.toPacket(buf);
            buf.writeLong(recipe.inputAmount);

            recipe.output1.toPacket(buf);
            buf.writeLong(recipe.output1Amount);

            recipe.output2.toPacket(buf);
            buf.writeLong(recipe.output2Amount);

            buf.writeBoolean(recipe.needsHeatResistantTubes);
            */
        }
    }



    /**
     * OVERWRITE THIS METHOD!
     */
    @Override
    public RecipeType<?> getType() {
        return null;
    }
}
