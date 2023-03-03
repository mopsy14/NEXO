package mopsy.productions.nucleartech.HUD;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static mopsy.productions.nucleartech.Main.modid;
import static mopsy.productions.nucleartech.registry.ModdedItems.Items;

public class Radiation implements HudRenderCallback {
    private static final Identifier RADIATION_BACKGROUND_TEXTURE = new Identifier(modid, "textures/hud/radiation/radiation_background.png");
    private static final Identifier RADIATION_BACKGROUND_TEXT_TEXTURE = new Identifier(modid, "textures/hud/radiation/radiation_background_text.png");
    private static final Identifier RADIATION_BAR_PIECE = new Identifier(modid, "textures/hud/radiation/radiation_bar_piece.png");
    public static float radiation = 0;
    public static float radiationPerTick = 0;

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if(client!=null){
            if(MinecraftClient.getInstance().player.getInventory().contains(new ItemStack(Items.get("geiger_counter")))) {
                int x;
                int y;
                int textureWidth;
                int width = client.getWindow().getScaledWidth();
                int height = client.getWindow().getScaledHeight();

                textureWidth = 77;
                x = 0;
                y = height - 12;


                RenderSystem.setShader(GameRenderer::getPositionShader);
                RenderSystem.setShaderColor(1, 1, 1, 1);
                RenderSystem.setShaderTexture(0, RADIATION_BACKGROUND_TEXTURE);
                DrawableHelper.drawTexture(matrixStack, x, y, 0, 0, textureWidth, 12, textureWidth, 12);

                textureWidth = 100;
                x = 0;
                y = height - 20;
                RenderSystem.setShaderTexture(0, RADIATION_BACKGROUND_TEXT_TEXTURE);
                DrawableHelper.drawTexture(matrixStack, x, y, 0, 0, textureWidth, 10, 100, 10);

                textureWidth = 1;
                RenderSystem.setShaderTexture(0, RADIATION_BAR_PIECE);
                for(int I = 0; I < 75; I++){
                    if(I*2 < radiation){
                        x = I+1;
                        y = height - 11;
                        DrawableHelper.drawTexture(matrixStack, x, y, 0, 0, textureWidth, 10, textureWidth, 10);
                    }
                }
            }
        }
    }
}
