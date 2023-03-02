package mopsy.productions.nucleartech.util;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class DisplayUtils {
    public static String getEnergyBarText(long power, long maxPower, boolean exact){
        if(exact)
            return Formatting.GOLD + getExactStringFromLong(power) + "E/" + getExactStringFromLong(maxPower) + "E";
        return Formatting.GOLD + getStringFromLong(power) + "E/" + getStringFromLong(maxPower) + "E";
    }
    public static String getFluidBarText(long amount, long maxAmount, boolean exact){
        if(exact) {
            return Formatting.GOLD + getExactStringFromLong(amount) + "mB/" + getExactStringFromLong(maxAmount) + "mB";
        }
        if (amount < 10000)
            return Formatting.GOLD.toString() + amount + "mB/" + maxAmount + "mB";
        return Formatting.GOLD + getStringFromLong(amount / 1000) + "B/" + getStringFromLong(maxAmount / 1000) + "B";
    }
    public static String getStringFromLong(long input){
        //10G+
        if(input>9999999999L){
            return Math.floor(input/100000000F)/10F+"G";
        }
        //10M+
        if(input>9999999L){
            return Math.floor(input/100000F)/10F+"M";
        }
        //10k+
        if(input>9999L){
            return Math.floor(input/100F)/10F+"K";
        }
        return String.valueOf(input);
    }
    public static String getExactStringFromLong(long input){
        StringBuilder res = new StringBuilder(String.valueOf(input));
        int LENGTH = res.length();

        for(int I = LENGTH-1;I>0;I--) {
            if((LENGTH-I)%3==0)
                res.insert(I, ',');
        }
        return res.toString();
    }
    public static List<Text> getFluidTooltipText(long amount, long maxAmount, FluidVariant fluidVariant, boolean exact){
        List<Text> text = new ArrayList<>();
        if (fluidVariant.getFluid() != Fluids.EMPTY && amount>0) {
            text.add(Text.translatable(fluidVariant.getFluid().getDefaultState().getBlockState().getBlock().getTranslationKey()));
            text.add(Text.of(DisplayUtils.getFluidBarText(amount, maxAmount, exact)));
        } else {
            if(exact)
                text.add(Text.of(Formatting.GOLD + "0mB/" + getExactStringFromLong(maxAmount) + "mB"));
            else {
                if(maxAmount<10000)
                    text.add(Text.of(Formatting.GOLD + "0mB/" + getStringFromLong(maxAmount) + "mB"));
                else
                    text.add(Text.of(Formatting.GOLD + "0mB/" + getStringFromLong(maxAmount/1000) + "B"));
            }
        }
        if(!exact)
            text.add(Text.of("Hold shift for advanced view"));
        return text;
    }
}
