package mopsy.productions.nexo.networking.payloads;

import mopsy.productions.nexo.networking.PacketManager;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record FluidChangePayload(BlockPos blockPos, FluidVariant fluidVariant, long fluidAmount) implements CustomPayload {
    public static final Id<FluidChangePayload> ID = new Id<>(PacketManager.FLUID_CHANGE_PACKET);
    public static final PacketCodec<RegistryByteBuf, FluidChangePayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, FluidChangePayload::blockPos,
            FluidVariant.PACKET_CODEC, FluidChangePayload::fluidVariant,
            PacketCodecs.LONG, FluidChangePayload::fluidAmount,
            FluidChangePayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
