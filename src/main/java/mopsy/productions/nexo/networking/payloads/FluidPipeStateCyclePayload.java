package mopsy.productions.nexo.networking.payloads;

import mopsy.productions.nexo.networking.NEXOCodecs;
import mopsy.productions.nexo.networking.PacketManager;
import mopsy.productions.nexo.util.NEXORotation;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record FluidPipeStateCyclePayload(BlockPos blockPos, NEXORotation rotation) implements CustomPayload {
    public static final Id<FluidPipeStateCyclePayload> ID = new Id<>(PacketManager.FLUID_PIPE_CYCLE_STATE_PACKET);
    public static final PacketCodec<RegistryByteBuf, FluidPipeStateCyclePayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, FluidPipeStateCyclePayload::blockPos,
            NEXOCodecs.NEXO_ROTATION_PACKET_CODEC, FluidPipeStateCyclePayload::rotation,
            FluidPipeStateCyclePayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
