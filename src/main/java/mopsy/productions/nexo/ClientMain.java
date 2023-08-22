package mopsy.productions.nexo;

import mopsy.productions.nexo.screen.ScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import static mopsy.productions.nexo.networking.PacketManager.registerS2CPackets;

public class ClientMain implements ClientModInitializer {
    public static boolean clientLoaded = false;
    @Override
    public void onInitializeClient() {

        ScreenHandlers.regClientScreens();
        HudRenderCallback.EVENT.register(new mopsy.productions.nexo.HUD.Radiation());
        ItemTooltipCallback.EVENT.register(new mopsy.productions.nexo.registry.ItemCode.TooltipCallbackClass());
        registerS2CPackets();

        clientLoaded = true;
    }
}
