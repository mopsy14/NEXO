package mopsy.productions.nucleartech.networking.packets;

import mopsy.productions.nucleartech.ModBlocks.entities.machines.SmallReactorEntity;
import mopsy.productions.nucleartech.screen.smallReactor.SmallReactorScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class C2SPackets {
    public static void receiveSwitchReactorPower(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf buf, PacketSender packetSender) {
        BlockPos pos = buf.readBlockPos();
        server.execute(()->{
            if(player.currentScreenHandler instanceof SmallReactorScreenHandler && ((SmallReactorScreenHandler) player.currentScreenHandler).getBlockPos().equals(pos)){
                if (player.getWorld().getBlockEntity(pos) instanceof SmallReactorEntity entity){
                    entity.active = entity.active==0 ? 1:0;
                }
            }
        });
    }
}