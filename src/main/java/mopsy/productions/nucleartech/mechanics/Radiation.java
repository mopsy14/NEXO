package mopsy.productions.nucleartech.mechanics;

import mopsy.productions.nucleartech.Main;
import mopsy.productions.nucleartech.interfaces.IEntityDataSaver;
import mopsy.productions.nucleartech.networking.PacketManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class Radiation {

    public static int getRadiation(IEntityDataSaver player){
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt("radiation");
    }
    public static void setRadiation(IEntityDataSaver player, int radiation){
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("radiation", radiation);
    }

    public static int counter = 0;
    public static void addEvents(){
    ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
        Main.LOGGER.info(String.valueOf(getRadiation((IEntityDataSaver) handler.player)));
    });
    ServerTickEvents.START_SERVER_TICK.register((server) -> {
        if(counter == 200) {
            counter=0;
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                setRadiation((IEntityDataSaver) player, getRadiation((IEntityDataSaver) player)+1);
                PacketByteBuf buffer = PacketByteBufs.create();
                buffer.writeInt(getRadiation((IEntityDataSaver) player));
                ServerPlayNetworking.send(player, PacketManager.RADIATION_CHANGE_PACKAGE, buffer);
            }
        }else
            counter++;
    });
    }

}
