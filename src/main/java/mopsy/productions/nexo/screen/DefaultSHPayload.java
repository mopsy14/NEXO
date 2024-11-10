package mopsy.productions.nexo.screen;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record DefaultSHPayload(BlockPos blockPos) implements CustomPayload{
    public static final CustomPayload.Id<DefaultSHPayload> ID = new CustomPayload.Id<>(ScreenHandlers.SHPayloadID);
    public static final PacketCodec<RegistryByteBuf, DefaultSHPayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, DefaultSHPayload::blockPos,
            DefaultSHPayload::new);
    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
