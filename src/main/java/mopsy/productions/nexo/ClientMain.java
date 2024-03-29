package mopsy.productions.nexo;

import mopsy.productions.nexo.ModBlocks.renderers.FluidPipe_MK1EntityRenderer;
import mopsy.productions.nexo.registry.ModdedBlocks;
import mopsy.productions.nexo.screen.ScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

import static mopsy.productions.nexo.networking.PacketManager.registerS2CPackets;
import static mopsy.productions.nexo.registry.ModdedBlockEntities.FLUID_PIPE_MK1;

@Environment(EnvType.CLIENT)
public class ClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenHandlers.regClientScreens();
        HudRenderCallback.EVENT.register(new mopsy.productions.nexo.HUD.Radiation());
        ItemTooltipCallback.EVENT.register(new mopsy.productions.nexo.registry.ItemCode.TooltipCallbackClass());
        registerS2CPackets();

        BlockEntityRendererFactories.register(FLUID_PIPE_MK1, FluidPipe_MK1EntityRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ModdedBlocks.Blocks.get("steam_turbine"), RenderLayer.getCutout());
    }
}
