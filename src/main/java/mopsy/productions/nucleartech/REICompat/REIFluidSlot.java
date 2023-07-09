package mopsy.productions.nucleartech.REICompat;

import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.rei.api.client.gui.DrawableConsumer;
import mopsy.productions.nucleartech.util.IntCords2D;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

import static mopsy.productions.nucleartech.Main.modid;

@SuppressWarnings("UnstableApiUsage")
public class REIFluidSlot implements DrawableConsumer {
    private static final Identifier TEXTURE = new Identifier(modid, "textures/gui/rei.png");
    IntCords2D cords;
    FluidVariant variant;
    long amount;

    public REIFluidSlot(IntCords2D cords,FluidVariant variant,long amount){
        this.cords=cords;
        this.variant=variant;
        this.amount=amount;
    }
    @Override
    public void render(DrawableHelper helper, MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        helper.drawTexture(matrices, cords.x, cords.y,0,0,18,18);
        RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
        Sprite fluidSprite = FluidVariantRendering.getSprite(variant);

        int fluidColor = FluidVariantRendering.getColor(variant);
        RenderSystem.setShaderColor((fluidColor >> 16 & 255) / 255.0F, (float) (fluidColor >> 8 & 255) / 255.0F, (float) (fluidColor & 255) / 255.0F, 1F);
        helper.drawTexture(matrices, cords.x+1, cords.y+1, 0, 0, 16,16);


    }
}
