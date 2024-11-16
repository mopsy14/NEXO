package mopsy.productions.nexo.registry;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class ModdedDataComponentTypes {
    public static final ComponentType<FluidVariant> FLUID_VARIANT_COMPONENT_TYPE =
            register("fluid_variant", builder -> builder.codec(FluidVariant.CODEC).packetCodec(FluidVariant.PACKET_CODEC));

    public static final ComponentType<Long> FLUID_AMOUNT_COMPONENT_TYPE =
            register("fluid_amount", builder -> builder.codec(Codec.LONG).packetCodec(PacketCodecs.LONG));


    public static void init(){}

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, (builderOperator.apply(ComponentType.builder())).build());
    }
}
