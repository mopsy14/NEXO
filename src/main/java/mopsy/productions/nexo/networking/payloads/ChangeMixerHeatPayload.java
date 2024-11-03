package mopsy.productions.nexo.networking.payloads;

import mopsy.productions.nexo.networking.PacketManager;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record ChangeMixerHeatPayload(BlockPos blockPos, int heat) implements CustomPayload {
    public static final Id<ChangeMixerHeatPayload> ID = new Id<>(PacketManager.CHANGE_MIXER_SLIDER_PACKET);
    public static final PacketCodec<RegistryByteBuf, ChangeMixerHeatPayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, ChangeMixerHeatPayload::blockPos,
            PacketCodecs.INTEGER, ChangeMixerHeatPayload::heat,
            ChangeMixerHeatPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
