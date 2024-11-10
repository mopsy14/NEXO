package mopsy.productions.nexo.util;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.fluid.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class NFluidStack {
    public FluidVariant fluidVariant;
    public long fluidAmount;

    public static final Codec<NFluidStack> CODEC = Codec.lazyInitialized(
            ()-> RecordCodecBuilder.create(
                    in -> in.group(
                            FluidVariant.CODEC.fieldOf("fluid_type").forGetter(fluidStack->fluidStack.fluidVariant),
                            Codec.LONG.fieldOf("fluid_amount").forGetter(fluidStack->fluidStack.fluidAmount)
                    ).apply(in,NFluidStack::new))
    );

    public NFluidStack(FluidVariant variant, long amount){
        fluidVariant = variant;
        fluidAmount = amount;
    }
    public boolean isEmpty(){
        return fluidVariant.isBlank()||fluidAmount==0;
    }
    public void setFluidAmount(long fluidAmount) {
        if(fluidAmount==0)
            fluidVariant=FluidVariant.blank();
        this.fluidAmount = fluidAmount;
    }
    public long getFluidAmount() {
        return fluidAmount;
    }
    public void setFluidVariant(FluidVariant fluidVariant) {
        if(fluidVariant.isBlank())
            fluidAmount = 0;
        this.fluidVariant = fluidVariant;
    }
    public FluidVariant getFluidVariant() {
        return fluidVariant;
    }
    public NFluidStack copy(){
        return isEmpty() ? new NFluidStack(FluidVariant.blank(),0) : new NFluidStack(fluidVariant,fluidAmount);
    }

    public static NFluidStack fromNBT(NbtCompound nbtCompound){
        return new NFluidStack(
                FluidVariant.fromNbt(nbtCompound.getCompound("fluid_type")),
                nbtCompound.getLong("fluid_amount")
        );
    }
    public static NFluidStack fromJson(JsonElement jsonElement){
        return new NFluidStack(
                FluidVariant.of(Registry.FLUID.get(Identifier.tryParse(jsonElement.getAsJsonObject().get("fluid_type").getAsString()))),
                jsonElement.getAsJsonObject().get("fluid_amount").getAsLong()
        );
    }
    public static NFluidStack fromPacket(PacketByteBuf buf){
        return new NFluidStack(
                FluidVariant.fromPacket(buf),
                buf.readLong()
        );
    }
    public static NbtCompound toNBT(NFluidStack fluidStack, NbtCompound nbtCompound){
        nbtCompound.put("fluid_type",  fluidStack.fluidVariant.toNbt());
        nbtCompound.putLong("fluid_amount", fluidStack.fluidAmount);

        return nbtCompound;
    }
    public static JsonElement toJson(NFluidStack fluidStack, JsonElement jsonElement){
        jsonElement.getAsJsonObject().addProperty("fluid_type", Registry.FLUID.getId(fluidStack.fluidVariant.getFluid()).toString());
        jsonElement.getAsJsonObject().addProperty("fluid_amount", fluidStack.fluidAmount);

        return jsonElement;
    }
    public static PacketByteBuf toBuf(NFluidStack fluidStack, PacketByteBuf buf){
        fluidStack.fluidVariant.toPacket(buf);
        buf.writeLong(fluidStack.fluidAmount);

        return buf;
    }
    public FluidStack toFluidStack(){
        return FluidStack.create(fluidVariant.getFluid(),fluidAmount);
    }
}
