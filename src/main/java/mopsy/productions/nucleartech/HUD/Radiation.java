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
import static mopsy.productions.nucleartech.registry.Items.Items;

public class Radiation implements HudRenderCallback {
    private static final Identifier RADIATION_BACKGROUND_TEXTURE = new Identifier(modid, "textures/hud/radiation/radiation_background.png");
    private static final Identifier RADIATION_BACKGROUND_TEXT_TEXTURE = new Identifier(modid, "textures/hud/radiation/radiation_background_text.png");

    public static int Radiation = 0;

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if(client!=null){
            if(MinecraftClient.getInstance().player.getInventory().contains(new ItemStack(Items.get("geiger_counter")))) {
                int x = 0;
                int y = 0;
                int texturewidth = 0;
                int width = client.getWindow().getScaledWidth();
                int heigth = client.getWindow().getScaledHeight();

                texturewidth = width / 5;
                x = 0;
                y = heigth - 10;


                RenderSystem.setShader(GameRenderer::getPositionShader);
                RenderSystem.setShaderColor(1, 1, 1, 1);
                RenderSystem.setShaderTexture(0, RADIATION_BACKGROUND_TEXTURE);
                DrawableHelper.drawTexture(matrixStack, x, y, 0, 0, texturewidth, 10, texturewidth, 10);

                texturewidth = 100;
                x = 0;
                y = heigth - 20;
                RenderSystem.setShaderTexture(0, RADIATION_BACKGROUND_TEXT_TEXTURE);
                DrawableHelper.drawTexture(matrixStack, x, y, 0, 0, texturewidth, 10, texturewidth, 10);


                //1.02RAD/line
                //0.99<1.0
                //width_fillable_bar = width/5-(0.02*(width/5))
                //width_bar_piece = width_fillable_bar/98
                //



                for(int I = 1; I < 99; I++){
                    if(1.02*I>Radiation){
                        //print bar piece
                    }
                }
            }
        }
    }
}
