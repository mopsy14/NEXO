package mopsy.productions.nexo.networking.packets;

import mopsy.productions.nexo.HUD.Radiation;
import mopsy.productions.nexo.ModBlocks.entities.transport.FluidPipe_MK1Entity;
import mopsy.productions.nexo.interfaces.IEnergyStorage;
import mopsy.productions.nexo.interfaces.IFluidStorage;
import mopsy.productions.nexo.networking.payloads.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;


public class S2CPackets {
    public static void receiveRadiationChange(RadiationChangePayload payload, ClientPlayNetworking.Context context){
        Radiation.radiation = payload.radiation();
    }
    public static void receiveRadiationPerSecondChange(RadiationPerSecondChangePayload payload, ClientPlayNetworking.Context context){
        Radiation.radiationPerTick = payload.radiationPerSecond();
    }
    public static void receiveEnergyChange(EnergyChangePayload payload, ClientPlayNetworking.Context context) {
        MinecraftClient client = context.client();
        if (client.world == null) return;
        if (client.world.getBlockEntity(payload.blockPos()) instanceof IEnergyStorage energyStorage) {
            energyStorage.setPower(payload.energy());
        }
    }
    public static void receiveFluidChange(FluidChangePayload payload, ClientPlayNetworking.Context context) {
        MinecraftClient client = context.client();
        BlockPos blockPos = payload.blockPos();
        if (client.world == null) return;
        if (client.world.getBlockEntity(blockPos) instanceof IFluidStorage fluidStorage) {
            fluidStorage.setFluidType(payload.fluidVariant());
            fluidStorage.setFluidAmount(payload.fluidAmount());
        }
    }
    public static void receiveAdvancedFluidChange(AdvancedFluidChangePayload payload, ClientPlayNetworking.Context context) {
        MinecraftClient client = context.client();
        if (client.world == null) return;
        if (client.world.getBlockEntity(payload.blockPos()) instanceof IFluidStorage fluidStorage) {
            int storageIndex = payload.storageIndex();
            fluidStorage.getFluidStorages().get(storageIndex).variant = payload.fluidVariant();
            fluidStorage.getFluidStorages().get(storageIndex).amount = payload.fluidAmount();
        }
    }
    public static void receiveFluidPipeStateChange(FluidPipeStateChangePayload payload, ClientPlayNetworking.Context context) {
        MinecraftClient client = context.client();
        BlockPos blockPos = payload.blockPos();
        if (client.world == null) return;
        if (client.world.getBlockEntity(blockPos) instanceof FluidPipe_MK1Entity fluidPipe) {
            for (int i = 0; i < 6; i++)
                fluidPipe.endStates.put(payload.rotation()[i], payload.pipeEndState()[i]);
        }
    }
}
