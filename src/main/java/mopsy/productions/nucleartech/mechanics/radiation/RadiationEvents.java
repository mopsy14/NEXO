package mopsy.productions.nucleartech.mechanics.radiation;

import mopsy.productions.nucleartech.Main;
import mopsy.productions.nucleartech.interfaces.IData;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.ItemStack;

import static mopsy.productions.nucleartech.mechanics.radiation.RadiationHelper.sendRadiationPerSecondUpdatePackage;
import static mopsy.productions.nucleartech.mechanics.radiation.RadiationHelper.sendRadiationUpdatePackage;
import static mopsy.productions.nucleartech.registry.ModdedItems.Items;

public class RadiationEvents {
    private static int taskCounter = 0;
    public static void addEvents(){
    ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
        Main.LOGGER.info(String.valueOf(Radiation.getRadiation((IData) handler.player)));
        if (handler.getPlayer().getInventory().contains(new ItemStack(Items.get("geiger_counter")))||handler.getPlayer().isCreative()) {
            sendRadiationUpdatePackage(handler.getPlayer());
            sendRadiationPerSecondUpdatePackage(handler.getPlayer());
        }
    });
    ServerTickEvents.START_SERVER_TICK.register((server) -> {
        if(taskCounter == 1000) {
            taskCounter=0;
        }else
            taskCounter++;

        if(taskCounter%40==0){
            RadiationHelper.updateRadiationPerSecond(server);
        }
        if(taskCounter%10==0){
            RadiationHelper.updateRadiation(server);
        }
        //if(taskCounter % 20 == 0) Runs every 20 ticks (1 sec)
    });
    }
}
