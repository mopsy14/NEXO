package mopsy.productions.nucleartech.networking.packets;

import mopsy.productions.nucleartech.HUD.Radiation;
import mopsy.productions.nucleartech.interfaces.IEnergyStorage;
import mopsy.productions.nucleartech.interfaces.IFluidStorage;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("UnstableApiUsage")
public class S2CPackets {
    public static void receiveRadiationChange(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        Radiation.radiation = buf.readFloat();
    }
    public static void receiveRadiationPerSecondChange(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        Radiation.radiationPerTick = buf.readFloat();
    }
    public static void receiveEnergyChange(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        BlockPos blockPos = buf.readBlockPos();
        if(client.world==null)return;
        if (client.world.getBlockEntity(blockPos) instanceof IEnergyStorage){
            ((IEnergyStorage) client.world.getBlockEntity(blockPos)).setPower(buf.readLong());
        }
    }
    public static void receiveFluidChange(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        BlockPos blockPos = buf.readBlockPos();
        if(client.world==null)return;
        if (client.world.getBlockEntity(blockPos) instanceof IFluidStorage){
            ((IFluidStorage) client.world.getBlockEntity(blockPos)).setFluidType(FluidVariant.fromPacket(buf));
            ((IFluidStorage) client.world.getBlockEntity(blockPos)).setFluidAmount(buf.readLong());
        }
    }
    public static void receiveAdvancedFluidChange(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        BlockPos blockPos = buf.readBlockPos();
        if(client.world==null)return;
        if (client.world.getBlockEntity(blockPos) instanceof IFluidStorage){
            int storageIndex = buf.readInt();
            ((IFluidStorage) client.world.getBlockEntity(blockPos)).getFluidStorages().get(storageIndex).variant = FluidVariant.fromPacket(buf);
            ((IFluidStorage) client.world.getBlockEntity(blockPos)).getFluidStorages().get(storageIndex).amount = buf.readLong();
        }
    }
}
