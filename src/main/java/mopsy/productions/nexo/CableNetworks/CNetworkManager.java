package mopsy.productions.nexo.CableNetworks;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class CNetworkManager {
    public static boolean isLoaded = false;

    public void regEvents(){
        ServerLifecycleEvents.SERVER_STARTING.register(NetworksData::getNetworkData);
        ServerLifecycleEvents.SERVER_STOPPING.register((server -> {
            NetworksData
            NetworksData.removeData();
        }));
    }
}
