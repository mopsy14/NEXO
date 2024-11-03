package mopsy.productions.nexo.networking;

import io.netty.buffer.ByteBuf;
import mopsy.productions.nexo.util.NEXORotation;
import mopsy.productions.nexo.util.PipeEndState;
import net.minecraft.network.codec.PacketCodec;

public class NEXOCodecs {
    public static final PacketCodec<ByteBuf, NEXORotation[]> NEXO_ROTATION_ARRAY_PACKET_CODEC = new PacketCodec<>() {
        @Override
        public void encode(ByteBuf buf, NEXORotation[] value) {
            for (int i = 0; i < 6; i++) {
                value[i].writeToPacket(buf);
            }
        }

        public NEXORotation[] decode(ByteBuf buf) {
            NEXORotation[] res = new NEXORotation[6];
            for (int i = 0; i < 6; i++) {
                res[i] = NEXORotation.readPacket(buf);
            }
            return res;
        }
    };
    public static final PacketCodec<ByteBuf, NEXORotation> NEXO_ROTATION_PACKET_CODEC = new PacketCodec<>() {
        @Override
        public void encode(ByteBuf buf, NEXORotation value) {
            value.writeToPacket(buf);
        }

        public NEXORotation decode(ByteBuf buf) {
                return  NEXORotation.readPacket(buf);
        }
    };
    public static final PacketCodec<ByteBuf, PipeEndState[]> PIPE_END_STATE_ARRAY_PACKET_CODEC = new PacketCodec<>() {
        @Override
        public void encode(ByteBuf buf, PipeEndState[] value) {
            for (int i = 0; i < 6; i++) {
                value[i].writeToPacket(buf);
            }
        }

        public PipeEndState[] decode(ByteBuf buf) {
            PipeEndState[] res = new PipeEndState[6];
            for (int i = 0; i < 6; i++) {
                res[i] = PipeEndState.readPacket(buf);
            }
            return res;
        }
    };
}
