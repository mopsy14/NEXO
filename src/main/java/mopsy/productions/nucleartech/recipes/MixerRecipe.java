package mopsy.productions.nucleartech.recipes;

import com.google.gson.JsonObject;
import mopsy.productions.nucleartech.util.FluidUtils;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

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
    public final FluidVariant inputFluid4;
    public final long inputFluid4Amount;
    public final FluidVariant outputFluid1;
    public final long outputFluid1Amount;
    public final FluidVariant outputFluid2;
    public final long outputFluid2Amount;
    public final FluidVariant outputFluid3;
    public final long outputFluid3Amount;
    public final FluidVariant outputFluid4;
    public final long outputFluid4Amount;
    public final List<ItemStack> ingredients;
    public final List<ItemStack> outputs;

    public MixerRecipe(Identifier id, FluidVariant inputFluid1, long inputFluid1Amount, FluidVariant inputFluid2, long inputFluid2Amount, FluidVariant inputFluid3, long inputFluid3Amount, FluidVariant inputFluid4, long inputFluid4Amount, FluidVariant outputFluid1, long outputFluid1Amount, FluidVariant outputFluid2, long outputFluid2Amount, FluidVariant outputFluid3, long outputFluid3Amount, FluidVariant outputFluid4, long outputFluid4Amount, List<ItemStack> ingredients, List<ItemStack> outputs){
        this.id=id;
        this.inputFluid1 = inputFluid1;
        this.inputFluid1Amount = inputFluid1Amount;
        this.inputFluid2 = inputFluid2;
        this.inputFluid2Amount = inputFluid2Amount;
        this.inputFluid3 = inputFluid3;
        this.inputFluid3Amount = inputFluid3Amount;
        this.inputFluid4 = inputFluid4;
        this.inputFluid4Amount = inputFluid4Amount;

        this.outputFluid1 = outputFluid1;
        this.outputFluid1Amount = outputFluid1Amount;
        this.outputFluid2 = outputFluid2;
        this.outputFluid2Amount = outputFluid2Amount;
        this.outputFluid3 = outputFluid3;
        this.outputFluid3Amount = outputFluid3Amount;
        this.outputFluid4 = outputFluid4;
        this.outputFluid4Amount = outputFluid4Amount;

        this.ingredients = ingredients;
        this.outputs = outputs;
    }


    public boolean isMatch(List<SingleVariantStorage<FluidVariant>> fluidStorages, Inventory inv){
        return itemsMatch(inv)&&fluidsMatch(fluidStorages);
    }
    private boolean itemsMatch(Inventory inv){

    }
    private boolean fluidsMatch(List<SingleVariantStorage<FluidVariant>> fluidStorages){

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
            JsonObject jsonInput = JsonHelper.getObject(json, "input");
            String inputStrType = jsonInput.get("type").getAsString();
            FluidVariant inputType = FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(inputStrType)));
            long inputAmount = FluidUtils.mBtoDroplets(jsonInput.get("amount").getAsLong());

            JsonObject jsonOutput1 = JsonHelper.getObject(json, "output_1");
            String output1StrType = jsonOutput1.get("type").getAsString();
            FluidVariant output1Type = FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(output1StrType)));
            long output1Amount = FluidUtils.mBtoDroplets(jsonOutput1.get("amount").getAsLong());

            JsonObject jsonOutput2 = JsonHelper.getObject(json, "output_2");
            String output2StrType = jsonOutput2.get("type").getAsString();
            FluidVariant output2Type = FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(output2StrType)));
            long output2Amount = FluidUtils.mBtoDroplets(jsonOutput2.get("amount").getAsLong());

            boolean outputHRT = JsonHelper.getBoolean(json, "needs_heat_resistant_tubes");

            return new MixerRecipe(id, inputType, inputAmount, output1Type, output1Amount, output2Type, output2Amount, outputHRT);
        }

        @Override
        public MixerRecipe read(Identifier id, PacketByteBuf buf) {

            FluidVariant inputType = FluidVariant.fromPacket(buf);
            long inputAmount = buf.readLong();

            FluidVariant output1Type = FluidVariant.fromPacket(buf);
            long output1Amount = buf.readLong();

            FluidVariant output2Type = FluidVariant.fromPacket(buf);
            long output2Amount = buf.readLong();

            boolean outputHRT = buf.readBoolean();

            return new MixerRecipe(id, inputType, inputAmount, output1Type, output1Amount, output2Type, output2Amount, outputHRT);
        }

        @Override
        public void write(PacketByteBuf buf, MixerRecipe recipe) {

            recipe.input.toPacket(buf);
            buf.writeLong(recipe.inputAmount);

            recipe.output1.toPacket(buf);
            buf.writeLong(recipe.output1Amount);

            recipe.output2.toPacket(buf);
            buf.writeLong(recipe.output2Amount);

            buf.writeBoolean(recipe.needsHeatResistantTubes);

        }
    }
}
