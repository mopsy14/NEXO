package mopsy.productions.nucleartech.HUD;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static mopsy.productions.nucleartech.Main.modid;

public class Radiation implements HudRenderCallback {
    private static final Identifier RADIATION_BACKGROUND_TEXTURE = new Identifier(modid, "textures/hud/radiation/radiation_background.png");
    private static final Identifier RADIATION_BACKGROUND_TEXT_TEXTURE = new Identifier(modid, "textures/hud/radiation/radiation_background_text.png");


    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if(client!=null){
            int x = 0;
            int y = 0;
            int texturewidth = 0;
            int width = client.getWindow().getScaledWidth();
            int heigth = client.getWindow().getScaledHeight();

            texturewidth = width/5;
            x = 0;
            y = heigth-10;


            RenderSystem.setShader(GameRenderer::getPositionShader);
            RenderSystem.setShaderColor(1,1,1,1);
            RenderSystem.setShaderTexture(0, RADIATION_BACKGROUND_TEXTURE);
            DrawableHelper.drawTexture(matrixStack, x, y, 0,0,texturewidth, 10, texturewidth, 10);

            x = 0;
            y = heigth-20;
            RenderSystem.setShaderTexture(0, RADIATION_BACKGROUND_TEXT_TEXTURE);
            DrawableHelper.drawTexture(matrixStack, x, y, 0,0,texturewidth, 10, texturewidth, 10);

        }
    }
}
