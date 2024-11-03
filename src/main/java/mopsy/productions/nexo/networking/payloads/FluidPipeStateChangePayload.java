package mopsy.productions.nexo.networking.payloads;

import mopsy.productions.nexo.networking.NEXOCodecs;
import mopsy.productions.nexo.networking.PacketManager;
import mopsy.productions.nexo.util.NEXORotation;
import mopsy.productions.nexo.util.PipeEndState;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record FluidPipeStateChangePayload(BlockPos blockPos, NEXORotation[] rotation, PipeEndState[] pipeEndState) implements CustomPayload {
    public static final Id<FluidPipeStateChangePayload> ID = new Id<>(PacketManager.FLUID_PIPE_STATE_CHANGE_PACKET);
    public static final PacketCodec<RegistryByteBuf, FluidPipeStateChangePayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, FluidPipeStateChangePayload::blockPos,
            NEXOCodecs.NEXO_ROTATION_ARRAY_PACKET_CODEC,FluidPipeStateChangePayload::rotation,
            NEXOCodecs.PIPE_END_STATE_ARRAY_PACKET_CODEC,FluidPipeStateChangePayload::pipeEndState,
            FluidPipeStateChangePayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
