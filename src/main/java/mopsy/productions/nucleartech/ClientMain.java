package mopsy.productions.nucleartech;

import mopsy.productions.nucleartech.screen.ScreenHandlers;
import mopsy.productions.nucleartech.screen.crusher.CrusherScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ScreenHandlers.CRUSHER, CrusherScreen::new);
    }
}
