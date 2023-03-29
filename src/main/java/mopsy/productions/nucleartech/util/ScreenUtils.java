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

import java.util.function.Predicate;

import static mopsy.productions.nucleartech.Main.modid;

@SuppressWarnings("UnstableApiUsage")
public class ScreenUtils {
    private static final Identifier TEXTURE = new Identifier(modid, "textures/gui/gui_components.png");

    public static Predicate<IntCords2D> renderSmallFluidStorage(Screen screen, MatrixStack matrices, int x, int y, long fluidAmount, long maxFluidAmount, FluidVariant fluid){
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
        return ic2d -> (
                ic2d.x>x && ic2d.x<x+16 &&
                ic2d.y>y && ic2d.y<y+47
        );
    }
    private static void drawSmallFluidLines(Screen screen, MatrixStack matrices, int x, int y){
        RenderSystem.setShaderTexture(0, TEXTURE);
        screen.drawTexture(matrices, x, y, 0, 64, 11, 47);
    }
    public static Predicate<IntCords2D> renderBigFluidStorage(Screen screen, MatrixStack matrices, int x, int y, long fluidAmount, long maxFluidAmount, FluidVariant fluid){
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
        return ic2d -> (
                ic2d.x>x && ic2d.x<x+51 &&
                ic2d.y>y && ic2d.y<y+63
        );
    }
    private static void drawBigFluidLines(Screen handledScreen, MatrixStack matrices, int x, int y){
        RenderSystem.setShaderTexture(0, TEXTURE);
        handledScreen.drawTexture(matrices, x, y, 0, 0, 20, 63);
    }
    public static Predicate<IntCords2D> renderEnergyStorage(HandledScreen handledScreen, MatrixStack matrices, int x, int y, long power, long maxPower){
        if(power>0){
            RenderSystem.setShaderTexture(0, TEXTURE);
            int scaledPower = getScaledAmount(power, maxPower, 62);
            handledScreen.drawTexture(matrices, x, y+scaledPower, 0, 113+scaledPower, 16, 62-scaledPower);
        }
        return ic2d -> (
                ic2d.x>x && ic2d.x<x+16 &&
                ic2d.y>y && ic2d.y<y+62
        );
    }

    public static Predicate<IntCords2D> renderButton(HandledScreen handledScreen, MatrixStack matrices, int x, int y, boolean isActive){
        if(isActive){
            RenderSystem.setShaderTexture(0, TEXTURE);
            handledScreen.drawTexture(matrices, x, y, 33, 0, 18, 18);
        }
        return ic2d -> (
                ic2d.x>x && ic2d.x<x+18 &&
                        ic2d.y>y && ic2d.y<y+18
        );
    }
    public static Predicate<IntCords2D> renderSmallButton(HandledScreen handledScreen, MatrixStack matrices, int x, int y, boolean isActive){
        if(isActive){
            RenderSystem.setShaderTexture(0, TEXTURE);
            handledScreen.drawTexture(matrices, x, y, 32, 32, 11, 11);
        }
        return ic2d -> (
                ic2d.x>x && ic2d.x<x+11 &&
                        ic2d.y>y && ic2d.y<y+11
        );
    }

    private static int getScaledAmount(long amount, long max, int barSize){
        int res = Math.toIntExact(max != 0 && amount != 0 ? amount * barSize / max : 0);
        res = barSize-res;
        return res;
    }
    private static int getScaledAmountType2(int amount, int max, int barSize){
        return max!=0 && amount!=0 ? amount*barSize/max : 0;
    }

    public static Predicate<IntCords2D> renderCoreHeatBar(Screen screen, MatrixStack matrices, int x, int y, int coreHeat, Identifier texture){
        if(coreHeat>100){
            RenderSystem.setShaderTexture(0, texture);
            int scaledPower = getScaledAmount(coreHeat-100, 900, 20);
            screen.drawTexture(matrices, x, y+scaledPower, 208, scaledPower, 16, 40-scaledPower);
        } else if (coreHeat>0) {
            RenderSystem.setShaderTexture(0, texture);
            int scaledPower = getScaledAmount(coreHeat, 200, 40);
            screen.drawTexture(matrices, x, y+scaledPower, 208, scaledPower, 16, 40-scaledPower);
        }
        return ic2d -> (
                ic2d.x>x && ic2d.x<x+16 &&
                        ic2d.y>y && ic2d.y<y+40
        );
    }
    public static Predicate<IntCords2D> renderFurnaceHeatBar(Screen screen, MatrixStack matrices, int x, int y, int timeLeft, int totalTime, Identifier texture){
        if(timeLeft>0){
            RenderSystem.setShaderTexture(0, texture);
            int scaledPower = getScaledAmountType2(totalTime-timeLeft, totalTime, 62);
            screen.drawTexture(matrices, x, y+scaledPower, 176, scaledPower, 14, 62-scaledPower);
        }
        return ic2d -> (
                ic2d.x>x && ic2d.x<x+14 &&
                        ic2d.y>y && ic2d.y<y+62
        );
    }
    public static Predicate<IntCords2D> renderSlider(HandledScreen handledScreen, MatrixStack matrices, int x, int y, int sliderProgress){
        RenderSystem.setShaderTexture(0, TEXTURE);
        handledScreen.drawTexture(matrices, x+sliderProgress, y, 176, 12, 7, 17);
        return ic2d -> (
                ic2d.x>x && ic2d.x<x+7 &&
                        ic2d.y>y && ic2d.y<y+17
        );
    }
}
