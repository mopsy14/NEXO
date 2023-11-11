package mopsy.productions.nexo.ModBlocks.renderers;

import mopsy.productions.nexo.ModBlocks.entities.transport.FluidPipe_MK1Entity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;

import static mopsy.productions.nexo.Main.modid;

@Environment(EnvType.CLIENT)
public class FluidPipe_MK1EntityRenderer implements BlockEntityRenderer<FluidPipe_MK1Entity> {

    public FluidPipe_MK1EntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(FluidPipe_MK1Entity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();


        MinecraftClient.getInstance().getBlockRenderManager().getModels().getModelManager().getModel(new ModelIdentifier(modid,"fluid_pipe_mk1",""));



        matrices.pop();
    }
}
