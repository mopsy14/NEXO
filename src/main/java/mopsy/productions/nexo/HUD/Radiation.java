package mopsy.productions.nexo.HUD;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.modid;
import static mopsy.productions.nexo.registry.ModdedItems.Items;

public class Radiation implements HudRenderCallback {
    private static final Identifier RADIATION_BACKGROUND_TEXTURE = Identifier.of(modid, "textures/hud/radiation/radiation_background.png");
    private static final Identifier RADIATION_BACKGROUND_TEXT_TEXTURE = Identifier.of(modid, "textures/hud/radiation/radiation_background_text.png");
    private static final Identifier RADIATION_BAR_PIECE = Identifier.of(modid, "textures/hud/radiation/radiation_bar_piece.png");
    public static float radiation = 0;
    public static float radiationPerTick = 0;

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
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

                drawContext.drawTexture(RenderLayer::getGuiTextured, RADIATION_BACKGROUND_TEXTURE, x, y, 0, 0, textureWidth, 12,77,12);

                textureWidth = 100;
                x = 0;
                y = height - 20;
                drawContext.drawTexture(RenderLayer::getGuiTextured, RADIATION_BACKGROUND_TEXT_TEXTURE, x, y, 0, 0, textureWidth, 10, 100, 10);

                textureWidth = 1;
                for(int I = 0; I < 75; I++){
                    if(I*2 < radiation){
                        x = I+1;
                        y = height - 11;
                        drawContext.drawTexture(RenderLayer::getGuiTextured, RADIATION_BAR_PIECE, x, y, 0, 0, textureWidth, 10, 1, 10);
                    }
                }
            }
        }
    }
}
