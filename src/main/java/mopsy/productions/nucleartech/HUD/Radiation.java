package mopsy.productions.nucleartech.HUD;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static mopsy.productions.nucleartech.Main.modid;

public class Radiation implements HudRenderCallback {
    private static final Identifier RADIATION_BACKGROUND_TEXTURE = new Identifier(modid, "textures/hud/radiation/radiation_background");


    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if(client!=null){
            int width = client.getWindow().getScaledWidth();
            int heigth = client.getWindow().getScaledHeight();

            x = width/2;
            y = heigth;
        }

    }
}
