package mopsy.productions.nexo.networking.payloads;

import mopsy.productions.nexo.networking.PacketManager;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record StartMixerPayload(BlockPos blockPos) implements CustomPayload {
    public static final Id<StartMixerPayload> ID = new Id<>(PacketManager.START_MIXER_PACKET);
    public static final PacketCodec<RegistryByteBuf, StartMixerPayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, StartMixerPayload::blockPos,
            StartMixerPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
