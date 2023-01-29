package mopsy.productions.nucleartech.networking.packets;

import mopsy.productions.nucleartech.HUD.Radiation;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class S2CPackets {
    public static void receiveRadiationChange(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        Radiation.radiation = buf.readFloat();
        System.out.println("Client received radiation: "+Radiation.radiation);
    }
    public static void receiveRadiationPerSecondChange(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        Radiation.radiationPerTick = buf.readFloat();
        System.out.println("Client received radiation/s: "+Radiation.radiationPerTick);
    }
    public static void receiveEnergyChange(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){

    }
}
