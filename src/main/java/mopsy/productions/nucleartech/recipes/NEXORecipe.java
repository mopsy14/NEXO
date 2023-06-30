package mopsy.productions.nucleartech.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mopsy.productions.nucleartech.util.NFluidStack;
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





    public NEXORecipe getRecipe(){
        return this;
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
                    fluidInputs.add(NFluidStack.fromJson(element));
                }
            }

            jsonArray = json.getAsJsonArray("fluid_outputs");
            if(jsonArray!=null) {
                for (JsonElement element : jsonArray){
                    fluidOutputs.add(NFluidStack.fromJson(element));
                }
            }

            return new NEXORecipe(id,inputs,outputs,fluidInputs,fluidOutputs);
        }

        @Override
        public NEXORecipe read(Identifier id, PacketByteBuf buf) {
            List<Ingredient> inputs = new ArrayList<>();
            List<ItemStack> outputs = new ArrayList<>();
            List<NFluidStack> fluidInputs = new ArrayList<>();
            List<NFluidStack> fluidOutputs = new ArrayList<>();

            for (int i = 0; i < buf.readInt(); i++) {
                inputs.add(Ingredient.fromPacket(buf));
            }
            for (int i = 0; i < buf.readInt(); i++) {
                outputs.add(buf.readItemStack());
            }
            for (int i = 0; i < buf.readInt(); i++) {
                fluidInputs.add(NFluidStack.fromPacket(buf));
            }
            for (int i = 0; i < buf.readInt(); i++) {
                fluidOutputs.add(NFluidStack.fromPacket(buf));
            }
            return new NEXORecipe(id,inputs,outputs,fluidInputs,fluidOutputs);
        }

        @Override
        public void write(PacketByteBuf buf, NEXORecipe recipe) {
            buf.writeInt(recipe.inputs.size());
            for(Ingredient ingredient : recipe.inputs){
                ingredient.write(buf);
            }
            buf.writeInt(recipe.outputs.size());
            for(ItemStack itemStack : recipe.outputs){
                buf.writeItemStack(itemStack);
            }
            buf.writeInt(recipe.inputFluids.size());
            for(NFluidStack fluidStack : recipe.inputFluids){
                NFluidStack.toBuf(fluidStack,buf);
            }
            buf.writeInt(recipe.outputFluids.size());
            for(NFluidStack fluidStack : recipe.outputFluids){
                NFluidStack.toBuf(fluidStack,buf);
            }
        }
    }



    /**
     * OVERWRITE THIS METHOD!
     */
    @Override
    public RecipeType<?> getType() {
        return null;
    }


    /**
     * UNUSED METHOD OF RECIPE!!!
     */
    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        return false;
    }
    /**
     * UNUSED METHOD OF RECIPE!!!
     */
    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return null;
    }
    /**
     * UNUSED METHOD OF RECIPE!!!
     */
    @Override
    public boolean fits(int width, int height) {
        return false;
    }
    /**
     * UNUSED METHOD OF RECIPE!!!
     */
    @Override
    public ItemStack getOutput() {
        return null;
    }
}
