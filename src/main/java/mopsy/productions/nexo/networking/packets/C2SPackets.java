package mopsy.productions.nexo.networking.packets;

import mopsy.productions.nexo.ModBlocks.entities.machines.MixerEntity;
import mopsy.productions.nexo.ModBlocks.entities.machines.SmallReactorEntity;
import mopsy.productions.nexo.ModBlocks.entities.transport.FluidPipe_MK1Entity;
import mopsy.productions.nexo.networking.payloads.*;
import mopsy.productions.nexo.screen.fluidPipe.FluidPipeScreenHandler;
import mopsy.productions.nexo.screen.mixer.MixerScreenHandler;
import mopsy.productions.nexo.screen.smallReactor.SmallReactorScreenHandler;
import mopsy.productions.nexo.util.NEXORotation;
import mopsy.productions.nexo.util.PipeEndState;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class C2SPackets {
    public static void receiveSwitchReactorPower(SwitchReactorPowerPayload payload, ServerPlayNetworking.Context context) {
        try(MinecraftServer server = context.server()) {
            BlockPos pos = payload.blockPos();
            server.execute(() -> {
                if (context.player().currentScreenHandler instanceof SmallReactorScreenHandler sh && sh.getBlockPos().equals(pos)) {
                    if (context.player().getWorld().getBlockEntity(pos) instanceof SmallReactorEntity entity) {
                        entity.active = entity.active == 0 ? 1 : 0;
                    }
                }
            });
        }
    }
    public static void receiveStartMixer(StartMixerPayload payload, ServerPlayNetworking.Context context) {
        try(MinecraftServer server = context.server()) {
            BlockPos pos = payload.blockPos();
            server.execute(() -> {
                if (context.player().currentScreenHandler instanceof MixerScreenHandler sh && sh.getBlockPos().equals(pos)) {
                    if (context.player().getWorld().getBlockEntity(pos) instanceof MixerEntity entity) {
                        entity.tryStart = true;
                    }
                }
            });
        }
    }
    public static void receiveChangeMixerHeat(ChangeMixerHeatPayload payload, ServerPlayNetworking.Context context) {
        try(MinecraftServer server = context.server()) {
            BlockPos pos = payload.blockPos();
            int heat = Math.max(Math.min(payload.heat(), 1000), -260);
            server.execute(() -> {
                if (context.player().currentScreenHandler instanceof MixerScreenHandler sh && sh.getBlockPos().equals(pos)) {
                    if (context.player().getWorld().getBlockEntity(pos) instanceof MixerEntity entity) {
                        if (entity.progress < 1) {
                            entity.heat = heat;
                        }
                    }
                }
            });
        }
    }
    public static void receivePipeStateRequest(FluidPipeStateRequestPayload payload, ServerPlayNetworking.Context context) {
        try(MinecraftServer server = context.server()) {
            BlockPos pos = payload.blockPos();
            server.execute(() -> {
                if (context.player().getWorld().getBlockEntity(pos) instanceof FluidPipe_MK1Entity entity) {
                    NEXORotation[] rotations = new NEXORotation[6];
                    PipeEndState[] endStates = new PipeEndState[6];
                    for (int i = 0; i<6; i++) {
                        rotations[i]=NEXORotation.values()[i];
                        endStates[i] = entity.endStates.get(rotations[i]);
                    }
                    context.responseSender().sendPacket(new FluidPipeStateChangePayload(pos,rotations,endStates));
                }
            });
        }
    }
    public static void receivePipeStateCycle(FluidPipeStateCyclePayload payload, ServerPlayNetworking.Context context) {
        try(MinecraftServer server = context.server()) {
            BlockPos pos = payload.blockPos();
            NEXORotation rotation = payload.rotation();

            server.execute(() -> {
                if (context.player().currentScreenHandler instanceof FluidPipeScreenHandler sh && sh.getBlockPos().equals(pos)) {
                    if (context.player().getWorld().getBlockEntity(pos) instanceof FluidPipe_MK1Entity entity) {

                        entity.endStates.put(rotation, entity.endStates.get(rotation).cycleIfEnd());

                        NEXORotation[] rotations = new NEXORotation[6];
                        PipeEndState[] endStates = new PipeEndState[6];
                        for (int i = 0; i<6; i++) {
                            rotations[i]=NEXORotation.values()[i];
                            endStates[i] = entity.endStates.get(rotations[i]);
                        }
                        context.responseSender().sendPacket(new FluidPipeStateChangePayload(pos,rotations,endStates));

                    }
                }
            });
        }
    }
}