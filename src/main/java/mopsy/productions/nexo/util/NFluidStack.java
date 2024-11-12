package mopsy.productions.nexo.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.fluid.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;


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
    public FluidStack toFluidStack(){
        return FluidStack.create(fluidVariant.getFluid(),fluidAmount);
    }
}
