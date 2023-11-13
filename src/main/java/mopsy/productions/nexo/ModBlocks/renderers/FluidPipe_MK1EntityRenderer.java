package mopsy.productions.nexo.ModBlocks.renderers;

import mopsy.productions.nexo.ModBlocks.entities.transport.FluidPipe_MK1Entity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;

import static mopsy.productions.nexo.Main.modid;

@Environment(EnvType.CLIENT)
public class FluidPipe_MK1EntityRenderer implements BlockEntityRenderer<FluidPipe_MK1Entity> {

    private static final Identifier TEXTURE = new Identifier(modid,"textures/blocks/fluid_pipe_mk1.png");

    private static final FluidPipe_MK1Model model = new FluidPipe_MK1Model();

    public FluidPipe_MK1EntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(FluidPipe_MK1Entity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();


        BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
        EntityModelLoader entityModelLoader = MinecraftClient.getInstance().getEntityModelLoader();
        BakedModelManager bakedModelManager = MinecraftClient.getInstance().getBakedModelManager();
        Identifier blockAtlasTexture = SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
        SpriteIdentifier spriteIdentifier = new SpriteIdentifier(blockAtlasTexture, new Identifier(modid,"block/insulated_copper_cable"));



        final VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(TEXTURE));

        model.render(matrices,vertexConsumer,light,overlay,1f,1f,1f,1f);



        matrices.pop();
    }

    private static class FluidPipe_MK1Model extends Model {

        private final ModelPart mainPart;

        public FluidPipe_MK1Model(){
            super(RenderLayer::getEntityCutoutNoCull);

            mainPart = new ModelPart(List.of(new ModelPart.Cuboid(0,0,0f,0f,0f,5f,5f,5f, 0f,0f,0f, true, 5,5)), new HashMap<>());
        }

        @Override
        public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
            mainPart.render(matrices, vertices, light, overlay);
        }
    }
}
