package mopsy.productions.nucleartech.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import mopsy.productions.nucleartech.util.FluidUtils;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class MixerRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    public final FluidVariant inputFluid1;
    public final long inputFluid1Amount;
    public final FluidVariant inputFluid2;
    public final long inputFluid2Amount;
    public final FluidVariant inputFluid3;
    public final long inputFluid3Amount;
    public final FluidVariant outputFluid1;
    public final long outputFluid1Amount;
    public final FluidVariant outputFluid2;
    public final long outputFluid2Amount;
    public final FluidVariant outputFluid3;
    public final long outputFluid3Amount;
    public final List<ItemStack> ingredients;
    public final List<ItemStack> outputs;

    public MixerRecipe(Identifier id, FluidVariant inputFluid1, long inputFluid1Amount, FluidVariant inputFluid2, long inputFluid2Amount, FluidVariant inputFluid3, long inputFluid3Amount, FluidVariant outputFluid1, long outputFluid1Amount, FluidVariant outputFluid2, long outputFluid2Amount, FluidVariant outputFluid3, long outputFluid3Amount, List<ItemStack> ingredients, List<ItemStack> outputs){
        this.id=id;
        this.inputFluid1 = inputFluid1;
        this.inputFluid1Amount = inputFluid1Amount;
        this.inputFluid2 = inputFluid2;
        this.inputFluid2Amount = inputFluid2Amount;
        this.inputFluid3 = inputFluid3;
        this.inputFluid3Amount = inputFluid3Amount;

        this.outputFluid1 = outputFluid1;
        this.outputFluid1Amount = outputFluid1Amount;
        this.outputFluid2 = outputFluid2;
        this.outputFluid2Amount = outputFluid2Amount;
        this.outputFluid3 = outputFluid3;
        this.outputFluid3Amount = outputFluid3Amount;

        this.ingredients = ingredients;
        this.outputs = outputs;
    }


    public boolean isMatch(List<SingleVariantStorage<FluidVariant>> fluidStorages, Inventory inv){
        return itemsMatch(inv)&&fluidsMatch(fluidStorages);
    }
    private boolean itemsMatch(Inventory inv){
        for(ItemStack stack : ingredients){
            if (!containsItems(inv,stack.getItem(),stack.getCount()))return false;
        }
        return true;
    }
    private boolean containsItems(Inventory inv, Item item, int amount){
        for (int i = 8; i < 14; i++) {
            if(inv.getStack(i).getItem()==item&&inv.getStack(i).getCount()==amount)return true;
        }
        return false;
    }
    private boolean fluidsMatch(List<SingleVariantStorage<FluidVariant>> fluidStorages){
        return  containsFluid(fluidStorages,inputFluid1,inputFluid1Amount)&&
                containsFluid(fluidStorages,inputFluid2,inputFluid2Amount)&&
                containsFluid(fluidStorages,inputFluid3,inputFluid3Amount);
    }
    private boolean containsFluid(List<SingleVariantStorage<FluidVariant>> fluidStorages, FluidVariant type, long amount){
        for(SingleVariantStorage<FluidVariant> storage : fluidStorages){
            if(storage.variant==type&&storage.amount>=amount) return true;
        }
        return false;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        return false;//not Needed
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

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<MixerRecipe>{
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "mixing";
    }
    @SuppressWarnings("UnstableApiUsage")
    public static class Serializer implements RecipeSerializer<MixerRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "mixing";

        @Override
        public MixerRecipe read(Identifier id, JsonObject json) {
            //input fluids:
            JsonObject jsonInput = JsonHelper.getObject(json, "fluid_input");
            String fluidInputStrType = jsonInput.get("type").getAsString();
            FluidVariant fluidInputType = FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(fluidInputStrType)));
            long fluidInputAmount = FluidUtils.mBtoDroplets(jsonInput.get("amount").getAsLong());

            jsonInput = JsonHelper.getObject(json, "fluid_input2");
            String fluidInput2StrType = jsonInput.get("type").getAsString();
            FluidVariant fluidInput2Type = FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(fluidInput2StrType)));
            long fluidInput2Amount = FluidUtils.mBtoDroplets(jsonInput.get("amount").getAsLong());

            jsonInput = JsonHelper.getObject(json, "fluid_input3");
            String fluidInput3StrType = jsonInput.get("type").getAsString();
            FluidVariant fluidInput3Type = FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(fluidInput3StrType)));
            long fluidInput3Amount = FluidUtils.mBtoDroplets(jsonInput.get("amount").getAsLong());

            //output fluids:
            jsonInput = JsonHelper.getObject(json, "fluid_output_1");
            String fluidOutput1StrType = jsonInput.get("type").getAsString();
            FluidVariant fluidOutput1Type = FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(fluidOutput1StrType)));
            long fluidOutput1Amount = FluidUtils.mBtoDroplets(jsonInput.get("amount").getAsLong());

            jsonInput = JsonHelper.getObject(json, "fluid_output_2");
            String fluidOutput2StrType = jsonInput.get("type").getAsString();
            FluidVariant fluidOutput2Type = FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(fluidOutput2StrType)));
            long fluidOutput2Amount = FluidUtils.mBtoDroplets(jsonInput.get("amount").getAsLong());

            jsonInput = JsonHelper.getObject(json, "fluid_output_3");
            String fluidOutput3StrType = jsonInput.get("type").getAsString();
            FluidVariant fluidOutput3Type = FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(fluidOutput3StrType)));
            long fluidOutput3Amount = FluidUtils.mBtoDroplets(jsonInput.get("amount").getAsLong());


            //input items:
            List<ItemStack> inputStacks = new ArrayList<>();
            JsonArray jsonInputs = JsonHelper.getArray(json, "input");
            jsonInputs.forEach((jsonElement) -> {if(!((JsonObject) jsonElement).has("item")) inputStacks.add(getStack((JsonObject) jsonElement));});


            //output items:
            List<ItemStack> outputStacks = new ArrayList<>();
            JsonArray jsonOutputs = JsonHelper.getArray(json, "output");
            jsonOutputs.forEach((jsonElement) -> {if(!((JsonObject) jsonElement).has("item")) outputStacks.add(getStack((JsonObject) jsonElement));});


            return new MixerRecipe(id, fluidInputType, fluidInputAmount, fluidInput2Type, fluidInput2Amount, fluidInput3Type, fluidInput3Amount, fluidOutput1Type, fluidOutput1Amount, fluidOutput2Type, fluidOutput2Amount, fluidOutput3Type, fluidOutput3Amount, inputStacks, outputStacks);
        }
        private ItemStack getStack(JsonObject object){
            return new ItemStack(Registry.ITEM.get(Identifier.tryParse(object.get("item").getAsString())), object.has("count")?object.get("count").getAsInt():1);
        }

        @Override
        public MixerRecipe read(Identifier id, PacketByteBuf buf) {

            FluidVariant inputType = FluidVariant.fromPacket(buf);
            long inputAmount = buf.readLong();
            FluidVariant input2Type = FluidVariant.fromPacket(buf);
            long input2Amount = buf.readLong();
            FluidVariant input3Type = FluidVariant.fromPacket(buf);
            long input3Amount = buf.readLong();

            FluidVariant output1Type = FluidVariant.fromPacket(buf);
            long output1Amount = buf.readLong();
            FluidVariant output2Type = FluidVariant.fromPacket(buf);
            long output2Amount = buf.readLong();
            FluidVariant output3Type = FluidVariant.fromPacket(buf);
            long output3Amount = buf.readLong();

            int bufferSize = buf.readInt();
            List<ItemStack> inputStacks = new ArrayList<>();
            for (int i = 0; i < bufferSize; i++) {
                inputStacks.add(buf.readItemStack());
            }

            bufferSize = buf.readInt();
            List<ItemStack> outputStacks = new ArrayList<>();
            for (int i = 0; i < bufferSize; i++) {
                inputStacks.add(buf.readItemStack());
            }

            return new MixerRecipe(id, inputType, inputAmount,  input2Type, input2Amount, input3Type, input3Amount, output1Type, output1Amount, output2Type, output2Amount, output3Type, output3Amount, inputStacks, outputStacks);
        }

        @Override
        public void write(PacketByteBuf buf, MixerRecipe recipe) {

            recipe.inputFluid1.toPacket(buf);
            buf.writeLong(recipe.inputFluid1Amount);
            recipe.inputFluid2.toPacket(buf);
            buf.writeLong(recipe.inputFluid2Amount);
            recipe.inputFluid3.toPacket(buf);
            buf.writeLong(recipe.inputFluid3Amount);

            recipe.outputFluid1.toPacket(buf);
            buf.writeLong(recipe.outputFluid1Amount);
            recipe.outputFluid2.toPacket(buf);
            buf.writeLong(recipe.outputFluid2Amount);
            recipe.outputFluid3.toPacket(buf);
            buf.writeLong(recipe.outputFluid3Amount);

            buf.writeInt(recipe.ingredients.size());
            for(ItemStack ingredient : recipe.ingredients){
                buf.writeItemStack(ingredient);
            }

            buf.writeInt(recipe.outputs.size());
            for(ItemStack output : recipe.outputs){
                buf.writeItemStack(output);
            }
        }
    }
}
