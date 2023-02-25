package mopsy.productions.nucleartech.recipes;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
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

public class CentrifugeRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    public final FluidVariant input;
    public final long inputAmount;
    public final FluidVariant output1;
    public final long output1Amount;
    public final FluidVariant output2;
    public final long output2Amount;

    public CentrifugeRecipe(Identifier id, FluidVariant input, long inputAmount, FluidVariant output1, long output1Amount, FluidVariant output2, long output2Amount){
        this.id=id;
        this.input = input;
        this.inputAmount=inputAmount;
        this.output1=output1;
        this.output1Amount=output1Amount;
        this.output2=output2;
        this.output2Amount=output1Amount;
    }


    public boolean canProduce(List<SingleVariantStorage<FluidVariant>> fluidStorages, World world) {
        if(world.isClient) return false;

        return  fluidStorages.get(0).variant.equals(input) && fluidStorages.get(0).amount >= inputAmount && canOutput(fluidStorages);
    }

    private boolean canOutput(List<SingleVariantStorage<FluidVariant>> fluidStorages){
        boolean canOutput1 = fluidStorages.get(1).variant.equals(output1) && fluidStorages.get(1).getCapacity() - fluidStorages.get(1).amount >= output1Amount;
        if(fluidStorages.get(1).amount == 0)
            canOutput1 = true;

        if(canOutput1 && fluidStorages.get(2).variant.equals(output2)&& fluidStorages.get(2).getCapacity()-fluidStorages.get(2).amount >= output2Amount)
            return true;
        return canOutput1 && fluidStorages.get(2).amount == 0;
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

    public static class Type implements RecipeType<CentrifugeRecipe>{
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "centrifuge";
    }
    public static class Serializer implements RecipeSerializer<CentrifugeRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "centrifuge";

        @Override
        public CentrifugeRecipe read(Identifier id, JsonObject json) {
            JsonObject jsonInput = JsonHelper.getObject(json, "input");
            String inputStrType = jsonInput.get("type").getAsString();
            FluidVariant inputType = FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(inputStrType)));
            long inputAmount = jsonInput.get("amount").getAsLong();

            JsonObject jsonOutput1 = JsonHelper.getObject(json, "output_1");
            String output1StrType = jsonOutput1.get("type").getAsString();
            FluidVariant output1Type = FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(output1StrType)));
            long output1Amount = jsonOutput1.get("amount").getAsLong();

            JsonObject jsonOutput2 = JsonHelper.getObject(json, "output_2");
            String output2StrType = jsonOutput2.get("type").getAsString();
            FluidVariant output2Type = FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(output2StrType)));
            long output2Amount = jsonOutput2.get("amount").getAsLong();


            return new CentrifugeRecipe(id, inputType, inputAmount, output1Type, output1Amount, output2Type, output2Amount);
        }

        @Override
        public CentrifugeRecipe read(Identifier id, PacketByteBuf buf) {

            FluidVariant inputType = FluidVariant.fromPacket(buf);
            long inputAmount = buf.readLong();

            FluidVariant output1Type = FluidVariant.fromPacket(buf);
            long output1Amount = buf.readLong();

            FluidVariant output2Type = FluidVariant.fromPacket(buf);
            long output2Amount = buf.readLong();

            return new CentrifugeRecipe(id, inputType, inputAmount, output1Type, output1Amount, output2Type, output2Amount);
        }

        @Override
        public void write(PacketByteBuf buf, CentrifugeRecipe recipe) {

            recipe.input.toPacket(buf);
            buf.writeLong(recipe.inputAmount);

            recipe.output1.toPacket(buf);
            buf.writeLong(recipe.output1Amount);

            recipe.output2.toPacket(buf);
            buf.writeLong(recipe.output2Amount);

        }
    }
}
