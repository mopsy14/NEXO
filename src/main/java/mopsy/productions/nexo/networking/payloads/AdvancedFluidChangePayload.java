package mopsy.productions.nexo.networking.payloads;

import mopsy.productions.nexo.networking.PacketManager;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record AdvancedFluidChangePayload(BlockPos blockPos, int storageIndex, FluidVariant fluidVariant, long fluidAmount) implements CustomPayload {
    public static final Id<AdvancedFluidChangePayload> ID = new Id<>(PacketManager.ADVANCED_FLUID_CHANGE_PACKET);
    public static final PacketCodec<RegistryByteBuf, AdvancedFluidChangePayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, AdvancedFluidChangePayload::blockPos,
            PacketCodecs.INTEGER, AdvancedFluidChangePayload::storageIndex,
            FluidVariant.PACKET_CODEC, AdvancedFluidChangePayload::fluidVariant,
            PacketCodecs.LONG, AdvancedFluidChangePayload::fluidAmount,
            AdvancedFluidChangePayload::new);
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
