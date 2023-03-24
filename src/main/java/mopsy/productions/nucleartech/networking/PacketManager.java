package mopsy.productions.nucleartech.networking;

import mopsy.productions.nucleartech.networking.packets.C2SPackets;
import mopsy.productions.nucleartech.networking.packets.S2CPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

import static mopsy.productions.nucleartech.Main.modid;

public class PacketManager {
    public static final Identifier RADIATION_CHANGE_PACKET = new Identifier(modid, "radiation_change");
    public static final Identifier RADIATION_PER_SECOND_CHANGE_PACKET = new Identifier(modid, "radiation/s_change");
    public static final Identifier ENERGY_CHANGE_PACKET = new Identifier(modid, "energy_change");
    public static final Identifier FLUID_CHANGE_PACKET = new Identifier(modid, "fluid_change");
    public static final Identifier ADVANCED_FLUID_CHANGE_PACKET = new Identifier(modid, "adv_fluid_change");
    public static final Identifier SWITCH_REACTOR_POWER_PACKET = new Identifier(modid, "switch_reactor_power");

    public static final Identifier START_MIXER_PACKET = new Identifier(modid, "start_mixer");
    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(SWITCH_REACTOR_POWER_PACKET, C2SPackets::receiveSwitchReactorPower);
        ServerPlayNetworking.registerGlobalReceiver(START_MIXER_PACKET, C2SPackets::receiveStartMixer);
    }
    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(RADIATION_CHANGE_PACKET, S2CPackets::receiveRadiationChange);
        ClientPlayNetworking.registerGlobalReceiver(RADIATION_PER_SECOND_CHANGE_PACKET, S2CPackets::receiveRadiationPerSecondChange);
        ClientPlayNetworking.registerGlobalReceiver(ENERGY_CHANGE_PACKET, S2CPackets::receiveEnergyChange);
        ClientPlayNetworking.registerGlobalReceiver(FLUID_CHANGE_PACKET, S2CPackets::receiveFluidChange);
        ClientPlayNetworking.registerGlobalReceiver(ADVANCED_FLUID_CHANGE_PACKET, S2CPackets::receiveAdvancedFluidChange);
    }

}
