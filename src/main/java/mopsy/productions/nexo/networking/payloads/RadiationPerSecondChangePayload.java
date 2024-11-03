package mopsy.productions.nexo.networking.payloads;

import mopsy.productions.nexo.networking.PacketManager;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record RadiationPerSecondChangePayload(float radiationPerSecond) implements CustomPayload {
    public static final Id<RadiationPerSecondChangePayload> ID = new Id<>(PacketManager.RADIATION_PER_SECOND_CHANGE_PACKET);
    public static final PacketCodec<RegistryByteBuf, RadiationPerSecondChangePayload> CODEC = PacketCodec.tuple(PacketCodecs.FLOAT, RadiationPerSecondChangePayload::radiationPerSecond, RadiationPerSecondChangePayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
