package mopsy.productions.nucleartech;

import mopsy.productions.nucleartech.screen.ScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.FlowableFluid;

import static mopsy.productions.nucleartech.networking.PacketManager.registerS2CPackets;

public class ClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ScreenHandlers.regClientScreens();
        HudRenderCallback.EVENT.register(new mopsy.productions.nucleartech.HUD.Radiation());
        ItemTooltipCallback.EVENT.register(new mopsy.productions.nucleartech.registry.ItemCode.TooltipCallbackClass());
        registerS2CPackets();
    }
    public static void regFluidRenderer(FlowableFluid fluid, int color){
        FluidRenderHandlerRegistry.INSTANCE.register(fluid, SimpleFluidRenderHandler.coloredWater(color));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), fluid);
    }
}
