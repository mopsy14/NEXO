package mopsy.productions.nucleartech.networking.packets;

import mopsy.productions.nucleartech.HUD.Radiation;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class S2CPackets {
    public static void recieveRadiationChange(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        Radiation.Radiation = buf.readInt();
    }
}
