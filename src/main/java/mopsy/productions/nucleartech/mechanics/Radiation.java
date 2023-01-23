package mopsy.productions.nucleartech.mechanics;

import mopsy.productions.nucleartech.Main;
import mopsy.productions.nucleartech.interfaces.IEntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.nbt.NbtCompound;

public class Radiation {

    public static int getRadiation(IEntityDataSaver player){
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt("radiation");
    }
    public static void setRadiation(IEntityDataSaver player, int radiation){
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("radiation", radiation);
    }

    public static void addEvents(){
    ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
        Main.LOGGER.info(String.valueOf(getRadiation((IEntityDataSaver) handler.player)));
    });
    }

}
