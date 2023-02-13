package mopsy.productions.nucleartech;

import mopsy.productions.nucleartech.registry.ModdedFluids;
import mopsy.productions.nucleartech.screen.ScreenHandlers;
import mopsy.productions.nucleartech.screen.crusher.CrusherScreen;
import mopsy.productions.nucleartech.screen.press.PressScreen;
import mopsy.productions.nucleartech.screen.tank.TankScreen_MK1;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public class ClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ScreenHandlers.CRUSHER, CrusherScreen::new);
        HandledScreens.register(ScreenHandlers.PRESS, PressScreen::new);
        HandledScreens.register(ScreenHandlers.Tank_MK1, TankScreen_MK1::new);

        FluidRenderHandlerRegistry.INSTANCE.register(ModdedFluids.NITROGEN, SimpleFluidRenderHandler.coloredWater( 0xA1FFFFFF));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModdedFluids.NITROGEN);
    }
}
