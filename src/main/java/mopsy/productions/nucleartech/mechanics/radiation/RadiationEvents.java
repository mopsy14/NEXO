package mopsy.productions.nucleartech.mechanics.radiation;

import mopsy.productions.nucleartech.Main;
import mopsy.productions.nucleartech.interfaces.IEntityDataSaver;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import static mopsy.productions.nucleartech.mechanics.radiation.RadiationHelper.changePlayerRadiation;

public class RadiationEvents {
    private static int taskCounter = 0;
    public static void addEvents(){
    ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
        Main.LOGGER.info(String.valueOf(Radiation.getRadiation((IEntityDataSaver) handler.player)));
    });
    ServerTickEvents.START_SERVER_TICK.register((server) -> {
        if(taskCounter == 1000) {
            taskCounter=0;
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                changePlayerRadiation(player, Radiation.getRadiation((IEntityDataSaver) player)+1);

            }
        }else
            taskCounter++;

        if(taskCounter%40==0){
            RadiationHelper.updateRadiationPerTick(server);
        }
        if(taskCounter%10==0){
            RadiationHelper.updateRadiation(server);
        }
        //if(taskCounter % 20 == 0) Runs every 20 ticks (1 sec)
    });
    }
}
