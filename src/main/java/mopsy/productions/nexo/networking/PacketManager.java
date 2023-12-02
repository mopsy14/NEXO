package mopsy.productions.nexo.networking;

import mopsy.productions.nexo.networking.packets.C2SPackets;
import mopsy.productions.nexo.networking.packets.S2CPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.modid;

public class PacketManager {
    //S2C
    public static final Identifier RADIATION_CHANGE_PACKET = new Identifier(modid, "radiation_change");
    public static final Identifier RADIATION_PER_SECOND_CHANGE_PACKET = new Identifier(modid, "radiation/s_change");
    public static final Identifier ENERGY_CHANGE_PACKET = new Identifier(modid, "energy_change");
    public static final Identifier FLUID_CHANGE_PACKET = new Identifier(modid, "fluid_change");
    public static final Identifier ADVANCED_FLUID_CHANGE_PACKET = new Identifier(modid, "adv_fluid_change");
    //C2S
    public static final Identifier SWITCH_REACTOR_POWER_PACKET = new Identifier(modid, "switch_reactor_power");
    public static final Identifier START_MIXER_PACKET = new Identifier(modid, "start_mixer");

    public static final Identifier CHANGE_MIXER_SLIDER_PACKET = new Identifier(modid, "change_mixer_slider");
    public static final Identifier FLUID_PIPE_STATE_CHANGE_PACKET = new Identifier(modid, "fluid_pipe_state_change");
    public static final Identifier FLUID_PIPE_STATE_REQUEST_PACKET = new Identifier(modid, "fluid_pipe_state_request");
    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(SWITCH_REACTOR_POWER_PACKET, C2SPackets::receiveSwitchReactorPower);
        ServerPlayNetworking.registerGlobalReceiver(START_MIXER_PACKET, C2SPackets::receiveStartMixer);
        ServerPlayNetworking.registerGlobalReceiver(CHANGE_MIXER_SLIDER_PACKET, C2SPackets::receiveChangeMixerHeat);
        ServerPlayNetworking.registerGlobalReceiver(FLUID_PIPE_STATE_REQUEST_PACKET, C2SPackets::receivePipeStateRequest);
    }
    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(RADIATION_CHANGE_PACKET, S2CPackets::receiveRadiationChange);
        ClientPlayNetworking.registerGlobalReceiver(RADIATION_PER_SECOND_CHANGE_PACKET, S2CPackets::receiveRadiationPerSecondChange);
        ClientPlayNetworking.registerGlobalReceiver(ENERGY_CHANGE_PACKET, S2CPackets::receiveEnergyChange);
        ClientPlayNetworking.registerGlobalReceiver(FLUID_CHANGE_PACKET, S2CPackets::receiveFluidChange);
        ClientPlayNetworking.registerGlobalReceiver(ADVANCED_FLUID_CHANGE_PACKET, S2CPackets::receiveAdvancedFluidChange);
        ClientPlayNetworking.registerGlobalReceiver(FLUID_PIPE_STATE_CHANGE_PACKET, S2CPackets::receiveFluidPipeStateChange);
    }

}
