package mopsy.productions.nucleartech.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

import static mopsy.productions.nucleartech.Main.modid;

public class ScreenUtils {
    private static final Identifier TEXTURE = new Identifier(modid, "textures/gui/gui_components.png");

    public static void renderSmallFluidStorage(Screen screen, MatrixStack matrices, int x, int y, long fluidAmount, long maxFluidAmount, FluidVariant fluid){
        if(fluidAmount>0){
            int scaledAmount = getScaledAmount(fluidAmount, maxFluidAmount, 47);
            RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
            Sprite fluidSprite = FluidVariantRendering.getSprite(fluid);

            int fluidColor = FluidVariantRendering.getColor(fluid);
            RenderSystem.setShaderColor((fluidColor >> 16 & 255) / 255.0F, (float) (fluidColor >> 8 & 255) / 255.0F, (float) (fluidColor & 255) / 255.0F, 1F);

            DrawableHelper.drawSprite(matrices, x, y+scaledAmount, 0, 16, 47-scaledAmount, fluidSprite);
            RenderSystem.setShaderColor(1F,1F,1F,1F);
        }

        drawSmallFluidLines(screen, matrices, x, y);
    }
    private static void drawSmallFluidLines(Screen screen, MatrixStack matrices, int x, int y){
        RenderSystem.setShaderTexture(0, TEXTURE);
        screen.drawTexture(matrices, x, y, 0, 64, 11, 47);
    }
    public static void renderBigFluidStorage(Screen screen, MatrixStack matrices, int x, int y, long fluidAmount, long maxFluidAmount, FluidVariant fluid){
        if(fluidAmount>0){
            int scaledAmount = getScaledAmount(fluidAmount, maxFluidAmount, 63);
            RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
            Sprite fluidSprite = FluidVariantRendering.getSprite(fluid);

            int fluidColor = FluidVariantRendering.getColor(fluid);
            RenderSystem.setShaderColor((fluidColor >> 16 & 255) / 255.0F, (float) (fluidColor >> 8 & 255) / 255.0F, (float) (fluidColor & 255) / 255.0F, 1F);

            DrawableHelper.drawSprite(matrices, x, y+scaledAmount, 0, 51, 63-scaledAmount, fluidSprite);
            RenderSystem.setShaderColor(1F,1F,1F,1F);
        }

        drawBigFluidLines(screen, matrices, x, y);
    }
    private static void drawBigFluidLines(Screen handledScreen, MatrixStack matrices, int x, int y){
        RenderSystem.setShaderTexture(0, TEXTURE);
        handledScreen.drawTexture(matrices, x, y, 0, 0, 20, 63);
    }
    public static void renderEnergyStorage(HandledScreen handledScreen, MatrixStack matrices, int x, int y, long power, long maxPower){
        if(power>0){
            RenderSystem.setShaderTexture(0, TEXTURE);
            int scaledPower = getScaledAmount(power, maxPower, 62);
            handledScreen.drawTexture(matrices, x, y+scaledPower, 0, 113+scaledPower, 16, 62-scaledPower);
        }
    }
    private static int getScaledAmount(long amount, long max, int barSize){
        int res = Math.toIntExact(max != 0 && amount != 0 ? amount * barSize / max : 0);
        res = barSize-res;
        return res;
    }
}
