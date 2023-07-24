package mopsy.productions.nexo.CableNetworks;

import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;

public class CNetworkManager {
    public static CNetworkManager INSTANCE;
    public static HashMap<World, List<CNetwork>> networks = new HashMap<>();
    public static boolean isLoaded = false;

    CNetworkManager(){
        INSTANCE = this;
    }
}
