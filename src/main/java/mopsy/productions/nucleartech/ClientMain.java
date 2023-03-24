package mopsy.productions.nucleartech;

import mopsy.productions.nucleartech.registry.ModdedFluids;
import mopsy.productions.nucleartech.screen.ScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.RenderLayer;

public class ClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ScreenHandlers.regClientScreens();
        HudRenderCallback.EVENT.register(new mopsy.productions.nucleartech.HUD.Radiation());
        ItemTooltipCallback.EVENT.register(new mopsy.productions.nucleartech.registry.ItemCode.TooltipCallbackClass());

        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.NITROGEN, SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.OXYGEN, SimpleFluidRenderHandler.coloredWater( 0xA1DCF2FF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.HYDROGEN, SimpleFluidRenderHandler.coloredWater( 0xA1E0F4FF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.SUPER_DENSE_STEAM, SimpleFluidRenderHandler.coloredWater( 0xA1ff8585));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.DENSE_STEAM, SimpleFluidRenderHandler.coloredWater( 0xA1ffa4a4));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.STEAM, SimpleFluidRenderHandler.coloredWater( 0xA1ffd4d4));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.AMMONIA, SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.FLUORINE, SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.HYDROGEN_FLUORIDE, SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.SULFUR_DIOXIDE, SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.SULFURIC_ACID, SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.SULFUR_TRIOXIDE, SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.URANIUM_HEXAFLUORIDE, SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.HKF2, SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.NITROGEN);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.OXYGEN);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.HYDROGEN);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.SUPER_DENSE_STEAM);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.DENSE_STEAM);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.STEAM);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.AMMONIA);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.FLUORINE);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.HYDROGEN_FLUORIDE);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.SULFUR_DIOXIDE);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.SULFURIC_ACID);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.SULFUR_TRIOXIDE);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.URANIUM_HEXAFLUORIDE);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.HKF2);
    }
}
