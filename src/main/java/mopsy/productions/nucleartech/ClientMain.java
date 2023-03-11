package mopsy.productions.nucleartech;

import mopsy.productions.nucleartech.registry.ModdedFluids;
import mopsy.productions.nucleartech.screen.ScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.render.RenderLayer;

public class ClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ScreenHandlers.regClientScreens();

        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.NITROGEN, SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.OXYGEN, SimpleFluidRenderHandler.coloredWater( 0xA1DCF2FF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.HYDROGEN, SimpleFluidRenderHandler.coloredWater( 0xA1E0F4FF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.SUPER_DENSE_STEAM, SimpleFluidRenderHandler.coloredWater( 0xA1ff8585));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.DENSE_STEAM, SimpleFluidRenderHandler.coloredWater( 0xA1ffa4a4));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.STEAM, SimpleFluidRenderHandler.coloredWater( 0xA1ffd4d4));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.NITROGEN);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.OXYGEN);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.HYDROGEN);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.SUPER_DENSE_STEAM);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.DENSE_STEAM);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.STEAM);
    }
}
