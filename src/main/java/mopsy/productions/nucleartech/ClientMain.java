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

import static mopsy.productions.nucleartech.networking.PacketManager.registerS2CPackets;

public class ClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ScreenHandlers.regClientScreens();
        HudRenderCallback.EVENT.register(new mopsy.productions.nucleartech.HUD.Radiation());
        ItemTooltipCallback.EVENT.register(new mopsy.productions.nucleartech.registry.ItemCode.TooltipCallbackClass());
        registerS2CPackets();

        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("nitrogen"), SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("oxygen"), SimpleFluidRenderHandler.coloredWater( 0xA1DCF2FF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("hydrogen"), SimpleFluidRenderHandler.coloredWater( 0xA1E0F4FF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("super_dense_steam"), SimpleFluidRenderHandler.coloredWater( 0xA1ff8585));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("dense_steam"), SimpleFluidRenderHandler.coloredWater( 0xA1ffa4a4));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("steam"), SimpleFluidRenderHandler.coloredWater( 0xA1ffd4d4));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("ammonia"), SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("fluorine"), SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("hydrogen_fluoride"), SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("sulfur_dioxide"), SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("sulfuric_acid"), SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("sulfur_trioxide"), SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("uranium_hexafluoride"), SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("uranium_hexafluoride_enriched"), SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("hf_kf"), SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));
        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.stillFluids.get("depleted_uranium_tails"), SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("nitrogen"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("oxygen"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("hydrogen"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("super_dense_steam"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("dense_steam"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("steam"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("ammonia"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("fluorine"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("hydrogen_fluoride"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("sulfur_dioxide"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("sulfuric_acid"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("sulfur_trioxide"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("uranium_hexafluoride"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("uranium_hexafluoride_enriched"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("hf_kf"));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.stillFluids.get("depleted_uranium_tails"));
    }
}
