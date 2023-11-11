package mopsy.productions.nexo;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import mopsy.productions.nexo.ModBlocks.renderers.FluidPipe_MK1EntityRenderer;
import mopsy.productions.nexo.screen.ScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

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

        BlockEntityRendererRegistry.register(FLUID_PIPE_MK1, FluidPipe_MK1EntityRenderer::new);
    }
}
