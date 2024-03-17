package mopsy.productions.nexo.networking.packets;

import mopsy.productions.nexo.ModBlocks.entities.machines.MixerEntity;
import mopsy.productions.nexo.ModBlocks.entities.machines.SmallReactorEntity;
import mopsy.productions.nexo.ModBlocks.entities.transport.FluidPipe_MK1Entity;
import mopsy.productions.nexo.screen.fluidPipe.FluidPipeScreenHandler;
import mopsy.productions.nexo.screen.mixer.MixerScreenHandler;
import mopsy.productions.nexo.screen.smallReactor.SmallReactorScreenHandler;
import mopsy.productions.nexo.util.NEXORotation;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import static mopsy.productions.nexo.networking.PacketManager.FLUID_PIPE_STATE_CHANGE_PACKET;

public class C2SPackets {
    public static void receiveSwitchReactorPower(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf buf, PacketSender packetSender) {
        BlockPos pos = buf.readBlockPos();
        server.execute(()->{
            if(player.currentScreenHandler instanceof SmallReactorScreenHandler sh && sh.getBlockPos().equals(pos)){
                if (player.getWorld().getBlockEntity(pos) instanceof SmallReactorEntity entity){
                    entity.active = entity.active==0 ? 1:0;
                }
            }
        });
    }
    public static void receiveStartMixer(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf buf, PacketSender packetSender) {
        BlockPos pos = buf.readBlockPos();
        server.execute(()->{
            if(player.currentScreenHandler instanceof MixerScreenHandler sh && sh.getBlockPos().equals(pos)){
                if (player.getWorld().getBlockEntity(pos) instanceof MixerEntity entity){
                    entity.tryStart = true;
                }
            }
        });
    }
    public static void receiveChangeMixerHeat(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf buf, PacketSender packetSender) {
        BlockPos pos = buf.readBlockPos();
        int heat = Math.max(Math.min(buf.readInt(), 1000), -260);
        server.execute(()->{
            if(player.currentScreenHandler instanceof MixerScreenHandler sh && sh.getBlockPos().equals(pos)){
                if (player.getWorld().getBlockEntity(pos) instanceof MixerEntity entity){
                    if(entity.progress<1){
                        entity.heat = heat;
                    }
                }
            }
        });
    }
    public static void receivePipeStateRequest(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf buf, PacketSender packetSender) {
        BlockPos pos = buf.readBlockPos();
        server.execute(()->{
            if (player.getWorld().getBlockEntity(pos) instanceof FluidPipe_MK1Entity entity){
                PacketByteBuf sendBuf = PacketByteBufs.create();
                sendBuf.writeBlockPos(pos);
                for(NEXORotation rotation : NEXORotation.values()){
                    rotation.writeToPacket(sendBuf);
                    entity.endStates.get(rotation).writeToPacket(sendBuf);
                }
                packetSender.sendPacket(FLUID_PIPE_STATE_CHANGE_PACKET,sendBuf);
            }
        });
    }
    public static void receivePipeStateCycle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf buf, PacketSender packetSender) {
        BlockPos pos = buf.readBlockPos();
        NEXORotation rotation = NEXORotation.readPacket(buf);

        server.execute(()->{
            if(player.currentScreenHandler instanceof FluidPipeScreenHandler sh && sh.getBlockPos().equals(pos)){
                if (player.getWorld().getBlockEntity(pos) instanceof FluidPipe_MK1Entity entity){

                    entity.endStates.put(rotation, entity.endStates.get(rotation).cycleIfEnd());

                    PacketByteBuf sendBuf = PacketByteBufs.create();
                    sendBuf.writeBlockPos(pos);
                    for(NEXORotation iRotation : NEXORotation.values()){
                        iRotation.writeToPacket(sendBuf);
                        entity.endStates.get(iRotation).writeToPacket(sendBuf);
                    }
                    packetSender.sendPacket(FLUID_PIPE_STATE_CHANGE_PACKET,sendBuf);

                }
            }
        });
    }
}