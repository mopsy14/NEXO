package mopsy.productions.nexo.networking;

import mopsy.productions.nexo.networking.packets.C2SPackets;
import mopsy.productions.nexo.networking.packets.S2CPackets;
import mopsy.productions.nexo.networking.payloads.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.LOGGER;
import static mopsy.productions.nexo.Main.modid;

public class PacketManager {
    //S2C
    public static final Identifier RADIATION_CHANGE_PACKET = Identifier.of(modid, "radiation_change");
    public static final Identifier RADIATION_PER_SECOND_CHANGE_PACKET = Identifier.of(modid, "radiation/s_change");
    public static final Identifier ENERGY_CHANGE_PACKET = Identifier.of(modid, "energy_change");
    public static final Identifier FLUID_CHANGE_PACKET = Identifier.of(modid, "fluid_change");
    public static final Identifier ADVANCED_FLUID_CHANGE_PACKET = Identifier.of(modid, "adv_fluid_change");
    //C2S
    public static final Identifier SWITCH_REACTOR_POWER_PACKET = Identifier.of(modid, "switch_reactor_power");
    public static final Identifier START_MIXER_PACKET = Identifier.of(modid, "start_mixer");

    public static final Identifier CHANGE_MIXER_SLIDER_PACKET = Identifier.of(modid, "change_mixer_slider");
    public static final Identifier FLUID_PIPE_STATE_CHANGE_PACKET = Identifier.of(modid, "fluid_pipe_state_change");
    public static final Identifier FLUID_PIPE_STATE_REQUEST_PACKET = Identifier.of(modid, "fluid_pipe_state_request");
    public static final Identifier FLUID_PIPE_CYCLE_STATE_PACKET = Identifier.of(modid, "fluid_pipe_invert_state");
    public static void registerC2SPackets(){
        LOGGER.info("Registering server bound packets");
        PayloadTypeRegistry.playC2S().register(SwitchReactorPowerPayload.ID,SwitchReactorPowerPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SwitchReactorPowerPayload.ID, C2SPackets::receiveSwitchReactorPower);
        PayloadTypeRegistry.playC2S().register(StartMixerPayload.ID,StartMixerPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(StartMixerPayload.ID, C2SPackets::receiveStartMixer);
        PayloadTypeRegistry.playC2S().register(ChangeMixerHeatPayload.ID,ChangeMixerHeatPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ChangeMixerHeatPayload.ID, C2SPackets::receiveChangeMixerHeat);
        PayloadTypeRegistry.playC2S().register(FluidPipeStateRequestPayload.ID,FluidPipeStateRequestPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(FluidPipeStateRequestPayload.ID, C2SPackets::receivePipeStateRequest);
        PayloadTypeRegistry.playC2S().register(FluidPipeStateCyclePayload.ID,FluidPipeStateCyclePayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(FluidPipeStateCyclePayload.ID, C2SPackets::receivePipeStateCycle);
    }
    public static void registerS2CPackets(){
        PayloadTypeRegistry.playS2C().register(RadiationChangePayload.ID,RadiationChangePayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(RadiationChangePayload.ID, S2CPackets::receiveRadiationChange);
        PayloadTypeRegistry.playS2C().register(EnergyChangePayload.ID,EnergyChangePayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(EnergyChangePayload.ID, S2CPackets::receiveEnergyChange);
        PayloadTypeRegistry.playS2C().register(RadiationPerSecondChangePayload.ID,RadiationPerSecondChangePayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(RadiationPerSecondChangePayload.ID, S2CPackets::receiveRadiationPerSecondChange);
        PayloadTypeRegistry.playS2C().register(FluidChangePayload.ID,FluidChangePayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FluidChangePayload.ID, S2CPackets::receiveFluidChange);
        PayloadTypeRegistry.playS2C().register(AdvancedFluidChangePayload.ID,AdvancedFluidChangePayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(AdvancedFluidChangePayload.ID, S2CPackets::receiveAdvancedFluidChange);
        PayloadTypeRegistry.playS2C().register(FluidPipeStateChangePayload.ID,FluidPipeStateChangePayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(FluidPipeStateChangePayload.ID, S2CPackets::receiveFluidPipeStateChange);
    }

}
