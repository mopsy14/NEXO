package mopsy.productions.nexo.networking.payloads;

import mopsy.productions.nexo.networking.PacketManager;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record EnergyChangePayload(BlockPos blockPos, long energy) implements CustomPayload {
    public static final Id<EnergyChangePayload> ID = new Id<>(PacketManager.ENERGY_CHANGE_PACKET);
    public static final PacketCodec<RegistryByteBuf, EnergyChangePayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, EnergyChangePayload::blockPos,
            PacketCodecs.LONG,EnergyChangePayload::energy,
            EnergyChangePayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
