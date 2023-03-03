package mopsy.productions.nucleartech.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class PressRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> inputs;

    public PressRecipe(Identifier id, DefaultedList<Ingredient> inputs, ItemStack output){
        this.id=id;
        this.output=output;
        this.inputs =inputs;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient) return false;

        return inputs.get(0).test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<PressRecipe>{
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "pressing";
    }
    public static class Serializer implements RecipeSerializer<PressRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "pressing";

        @Override
        public PressRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));

            JsonArray jsonInputs = JsonHelper.getArray(json, "input");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1, Ingredient.EMPTY);

            for(int i = 0; i<inputs.size(); i++){
                inputs.set(i, Ingredient.fromJson(jsonInputs.get(i)));
            }

            return new PressRecipe(id, inputs,  output);
        }

        @Override
        public PressRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            inputs.replaceAll(unused -> Ingredient.fromPacket(buf));

            ItemStack output = buf.readItemStack();
            return new PressRecipe(id, inputs, output);
        }

        @Override
        public void write(PacketByteBuf buf, PressRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for(Ingredient ingredient : recipe.getIngredients()){
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.output);
        }
    }
}
