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
    private static String getExactStringFromLong(long input){
        StringBuilder res = new StringBuilder(String.valueOf(input));

        for(int I = Math.toIntExact(Math.round(Math.floor(res.length()/3F)));I>0;I--) {
            res.insert(I*3, ',');
        }
        return res.toString();
    }
}
