package mopsy.productions.nucleartech.networking.packets;

import mopsy.productions.nucleartech.HUD.Radiation;
import mopsy.productions.nucleartech.interfaces.IEnergyStorage;
import mopsy.productions.nucleartech.interfaces.IFluidStorage;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import static mopsy.productions.nucleartech.Main.LOGGER;

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
        BlockPos blockPos = buf.readBlockPos();
        if (client.world.getBlockEntity(blockPos) instanceof IEnergyStorage){
            ((IEnergyStorage) client.world.getBlockEntity(blockPos)).setPower(buf.readLong());
        }else{
            LOGGER.error("Block at: "+blockPos+" doesn't contain IEnergyStorage");
        }
    }
    public static void receiveFluidChange(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        BlockPos blockPos = buf.readBlockPos();
        if (client.world.getBlockEntity(blockPos) instanceof IFluidStorage){
            ((IFluidStorage) client.world.getBlockEntity(blockPos)).setFluidType(Registry.FLUID.get(Identifier.tryParse(buf.readString())));
            ((IFluidStorage) client.world.getBlockEntity(blockPos)).setFluidAmount(buf.readLong());
        }else{
            LOGGER.error("Block at: "+blockPos+" doesn't contain IFluidStorage");
        }
    }
}
