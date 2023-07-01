package mopsy.productions.nucleartech.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mopsy.productions.nucleartech.enums.SlotIO;
import mopsy.productions.nucleartech.interfaces.IBlockEntityRecipeCompat;
import mopsy.productions.nucleartech.interfaces.IFluidStorage;
import mopsy.productions.nucleartech.util.NFluidStack;
import mopsy.productions.nucleartech.util.NTFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
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

    //hasRecipe Code:
    public boolean hasRecipe(BlockEntity blockEntity){
        return hasItems((SidedInventory)blockEntity,((IBlockEntityRecipeCompat)blockEntity).getItemSlotIOs()) && hasFluids(((IFluidStorage)blockEntity).getFluidStorages(),((IBlockEntityRecipeCompat)blockEntity));
    }
    private boolean hasFluids(List<SingleVariantStorage<FluidVariant>> fluidStorages, IBlockEntityRecipeCompat config){
        for (int i = 0; i < inputFluids.size(); i++) {
            if(!hasFluid(fluidStorages, config.getFluidSlotIOs()[i], inputFluids.get(i)))return false;
        }
        return true;
    }
    private boolean hasFluid(List<SingleVariantStorage<FluidVariant>> fluidStorages, SlotIO fluidConfig, NFluidStack checkStack){
        for(SingleVariantStorage<FluidVariant> fluidStorage : fluidStorages){
            if(fluidConfig== SlotIO.INPUT||fluidConfig== SlotIO.BOTH) {
                if (fluidStorage.variant == checkStack.fluidVariant) {
                    if (fluidStorage.amount >= checkStack.fluidAmount)return true;
                }
            }
        }
        return false;
    }
    private boolean hasItems(SidedInventory inv, SlotIO[] itemConfig){
        for(int i = 0; i < inputs.size(); i++){
            if(!hasItem(inv,inputs.get(i),itemConfig[i]))return false;
        }
        return true;
    }
    private boolean hasItem(SidedInventory inv, Ingredient ingredient, SlotIO slotIO){
        for (int i = 0; i < inv.size(); i++) {
            if(slotIO==SlotIO.INPUT||slotIO==SlotIO.BOTH)
                if(ingredient.test(inv.getStack(i)))return true;
        }
        return false;
    }

    //canOutput Code:
    public boolean canOutput(BlockEntity blockEntity){
        return canOutputItems((Inventory)blockEntity,((IBlockEntityRecipeCompat)blockEntity).getItemSlotIOs())&&canOutputFluids();
    }
    private boolean canOutputItems(Inventory blockInventory,SlotIO[] slotIOs) {
        //creating a copy inv with all slots that can accept output
        Inventory outputInv = new SimpleInventory(blockInventory.size());
        int invCounter=0;
        for (int i = 0; i < blockInventory.size(); i++) {
            if(slotIOs[i] == SlotIO.OUTPUT || slotIOs[i] == SlotIO.BOTH) {
                outputInv.setStack(invCounter,blockInventory.getStack(i).copy());
                invCounter++;
            }
        }
        //creating an inv with all outputs
        Inventory toBeOutputted = new SimpleInventory(outputs.size());
        for (int i = 0; i < outputs.size(); i++) {
            if(!outputs.get(i).isEmpty()) {
                toBeOutputted.setStack(i, outputs.get(i).copy());
            }
        }
        //checking if every output can be put into outputInv
        for (int i = 0; i < toBeOutputted.size();i++) {
            tryOutputItemStack(toBeOutputted.getStack(i), outputInv);
            if(!toBeOutputted.getStack(i).isEmpty())return false;
        }
        return true;
    }
    private void tryOutputItemStack(ItemStack outputStack, Inventory outputInv){
        int outputted = 0;
        for (int i = 0; i < outputInv.size() && !outputStack.isEmpty(); i++) {
            if(outputInv.getStack(i).isEmpty()){
                outputInv.setStack(i, outputStack);
                outputted = outputted + outputStack.getCount();
                outputStack.setCount(0);
            } else if (outputInv.getStack(i).getItem()==outputStack.getItem()) {
                int toOutput = Math.min(outputStack.getCount(),64-outputInv.getStack(i).getCount());
                outputInv.getStack(i).setCount(outputInv.getStack(i).getCount()+toOutput);
                outputted = outputted + toOutput;
                outputStack.setCount(outputStack.getCount()-toOutput);
            }
        }
    }
    private boolean canOutputFluids(List<SingleVariantStorage<FluidVariant>> fluidStorages, SlotIO[] fluidSlotIOs){
        //creating a copy inv with all storages that can accept output
        List<NTFluidStorage> outputStorages = new ArrayList<>();
        for (int i = 0; i < fluidStorages.size(); i++) {
            if(fluidSlotIOs[i] == SlotIO.OUTPUT || fluidSlotIOs[i] == SlotIO.BOTH) {
                outputStorages.add(((NTFluidStorage)fluidStorages.get(i)).copy());
            }
        }
        //creating a copy of all outputs
        List<NFluidStack> toBeOutputted = new ArrayList<>();
        for (NFluidStack outputFluid : outputFluids) {
            if (!outputFluid.isEmpty()) {
                toBeOutputted.add(outputFluid.copy());
            }
        }
        //checking if every output can be put into outputInv
        for (NFluidStack fluidStack : toBeOutputted) {
            tryOutputFluidStack(fluidStack, outputStorages);
            if (!fluidStack.isEmpty()) return false;
        }
        return true;
    }
    private void tryOutputFluidStack(NFluidStack outputStack, List<NTFluidStorage> outputStorages){
        long outputted = 0;
        for (int i = 0; i < outputStorages.size() && !outputStack.isEmpty(); i++) {
            if(outputStorages.get(i).isResourceBlank()){
                outputStorages.get(i).variant = outputStack.fluidVariant;
                long toOutput = Math.min(outputStack.fluidAmount, outputStorages.get(i).getCapacity());
                outputStorages.get(i).amount = outputStorages.get(i).amount + toOutput;
                outputted = outputted + toOutput;
                outputStack.fluidAmount = outputStack.fluidAmount-toOutput;
            } else if (outputStorages.get(i).getResource().equals(outputStack.fluidVariant)) {
                long toOutput = Math.min(outputStack.fluidAmount,outputStorages.get(i).getCapacity()-outputStorages.get(i).amount);
                outputStorages.get(i).amount = outputStorages.get(i).amount + toOutput;
                outputted = outputted + toOutput;
                outputStack.fluidAmount = outputStack.fluidAmount-toOutput;
            }
        }
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
