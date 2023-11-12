package mopsy.productions.nexo.ModBlocks.renderers;

import mopsy.productions.nexo.ModBlocks.entities.transport.FluidPipe_MK1Entity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
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

import static mopsy.productions.nexo.Main.modid;

@Environment(EnvType.CLIENT)
public class FluidPipe_MK1EntityRenderer implements BlockEntityRenderer<FluidPipe_MK1Entity> {

    public FluidPipe_MK1EntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(FluidPipe_MK1Entity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();


        BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
        EntityModelLoader entityModelLoader = MinecraftClient.getInstance().getEntityModelLoader();
        BakedModelManager bakedModelManager = MinecraftClient.getInstance().getBakedModelManager();
        Identifier blockAtlasTexture = SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
        SpriteIdentifier spriteIdentifier = new SpriteIdentifier(blockAtlasTexture, new Identifier(modid,"block/insulated_copper_cable"));

        //blockRenderManager.getModels().getModel(entity.getWorld().getBlockState(entity.getPos()).with());



        matrices.pop();
    }
}
