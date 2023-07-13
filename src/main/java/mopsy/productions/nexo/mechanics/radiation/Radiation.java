package mopsy.productions.nexo.mechanics.radiation;

import mopsy.productions.nexo.interfaces.IData;
import net.minecraft.nbt.NbtCompound;

public class Radiation {

    public static float getRadiation(IData player){
        NbtCompound nbt = player.getPersistentData();
        return nbt.getFloat("radiation");
    }
    public static void setRadiation(IData player, float radiation) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putFloat("radiation", radiation);
    }
}
