package mopsy.productions.nexo.networking.payloads;

import mopsy.productions.nexo.networking.PacketManager;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record SwitchReactorPowerPayload(BlockPos blockPos) implements CustomPayload {
    public static final Id<SwitchReactorPowerPayload> ID = new Id<>(PacketManager.SWITCH_REACTOR_POWER_PACKET);
    public static final PacketCodec<RegistryByteBuf, SwitchReactorPowerPayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, SwitchReactorPowerPayload::blockPos,
            SwitchReactorPowerPayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
