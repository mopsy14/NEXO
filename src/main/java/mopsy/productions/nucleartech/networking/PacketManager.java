package mopsy.productions.nucleartech.networking;

import mopsy.productions.nucleartech.networking.packets.S2CPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

import static mopsy.productions.nucleartech.Main.modid;

public class PacketManager {
    public static final Identifier RADIATION_CHANGE_PACKAGE = new Identifier(modid, "radiation_change");

    public static void registerC2SPackets(){

    }
    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(RADIATION_CHANGE_PACKAGE, S2CPackets::recieveRadiationChange);
    }

}
