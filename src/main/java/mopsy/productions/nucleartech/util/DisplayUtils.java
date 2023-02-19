package mopsy.productions.nucleartech.util;

import net.minecraft.util.Formatting;

public class DisplayUtils {
    public static String getEnergyBarText(long power, long maxPower){
        return Formatting.GOLD + getStringFromLong(power) + "E/" + getStringFromLong(maxPower) + "E";
    }
    public static String getFluidBarText(long amount, long maxAmount){
        if(amount<10000)
            return Formatting.GOLD.toString() + amount + "mB/" + maxAmount + "mB";
        return Formatting.GOLD + getStringFromLong(amount*1000) + "B/" + getStringFromLong(maxAmount*1000) + "B";
    }
    private static String getStringFromLong(long input){
        //10G+
        if(input>10000000000L){
            return Math.floor(input/1000000000F)+"G";
        }
        //10M+
        if(input>10000000L){
            return Math.floor(input/1000000F)+"M";
        }
        //10k+
        if(input>10000L){
            return Math.floor(input/1000F)+"K";
        }
        return String.valueOf(input);
    }
}
