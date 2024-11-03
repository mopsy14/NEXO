package mopsy.productions.nexo.networking.payloads;

import mopsy.productions.nexo.networking.PacketManager;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record RadiationChangePayload(float radiation) implements CustomPayload {
    public static final CustomPayload.Id<RadiationChangePayload> ID = new CustomPayload.Id<>(PacketManager.RADIATION_CHANGE_PACKET);
    public static final PacketCodec<RegistryByteBuf, RadiationChangePayload> CODEC = PacketCodec.tuple(PacketCodecs.FLOAT, RadiationChangePayload::radiation, RadiationChangePayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
