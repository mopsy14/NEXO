package mopsy.productions.nexo.networking.payloads;

import mopsy.productions.nexo.networking.PacketManager;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record FluidPipeStateRequestPayload(BlockPos blockPos) implements CustomPayload {
    public static final Id<FluidPipeStateRequestPayload> ID = new Id<>(PacketManager.FLUID_PIPE_STATE_REQUEST_PACKET);
    public static final PacketCodec<RegistryByteBuf, FluidPipeStateRequestPayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, FluidPipeStateRequestPayload::blockPos,
            FluidPipeStateRequestPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
