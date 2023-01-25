package mopsy.productions.nucleartech.mechanics.radiation;

import mopsy.productions.nucleartech.interfaces.IEntityDataSaver;
import net.minecraft.nbt.NbtCompound;

public class Radiation {

    public static float getRadiation(IEntityDataSaver player){
        NbtCompound nbt = player.getPersistentData();
        return nbt.getFloat("radiation");
    }
    public static void setRadiation(IEntityDataSaver player, float radiation) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putFloat("radiation", radiation);
    }
}
