package mopsy.productions.nexo.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

import static mopsy.productions.nexo.Main.modid;


public class ScreenUtils {
    private static final Identifier TEXTURE = Identifier.of(modid, "textures/gui/gui_components.png");

    public static Predicate<IntCords2D> renderSmallFluidStorage(Screen screen, DrawContext context, int x, int y, long fluidAmount, long maxFluidAmount, FluidVariant fluid){
        if(fluidAmount>0){
            int scaledAmount = getScaledAmount(fluidAmount, maxFluidAmount, 47);
            RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
            Sprite fluidSprite = FluidVariantRendering.getSprite(fluid);

            int fluidColor = FluidVariantRendering.getColor(fluid);

            context.drawSpriteStretched(RenderLayer::getGuiTextured, fluidSprite, x, y+scaledAmount, 16, 47-scaledAmount, fluidColor);
        }

        drawSmallFluidLines(screen, context, x, y);
        return ic2d -> (
                ic2d.x>x && ic2d.x<x+16 &&
                ic2d.y>y && ic2d.y<y+47
        );
    }
    private static void drawSmallFluidLines(Screen screen, DrawContext context, int x, int y){
        context.drawTexture(RenderLayer::getGuiTextured,TEXTURE, x, y, 0, 64, 11, 47,256,256);
    }
    public static Predicate<IntCords2D> renderBigFluidStorage(Screen screen, DrawContext context, int x, int y, long fluidAmount, long maxFluidAmount, FluidVariant fluid){
        if(fluidAmount>0){
            int scaledAmount = getScaledAmount(fluidAmount, maxFluidAmount, 63);
            RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
            Sprite fluidSprite = FluidVariantRendering.getSprite(fluid);

            int fluidColor = FluidVariantRendering.getColor(fluid);

            context.drawSpriteStretched(RenderLayer::getGuiTextured, fluidSprite, x, y+scaledAmount, 51, 63-scaledAmount, fluidColor);
        }

        drawBigFluidLines(screen, context, x, y);
        return ic2d -> (
                ic2d.x>x && ic2d.x<x+51 &&
                ic2d.y>y && ic2d.y<y+63
        );
    }
    private static void drawBigFluidLines(Screen handledScreen, DrawContext context, int x, int y){
        context.drawTexture(RenderLayer::getGuiTextured,TEXTURE, x, y, 0, 0, 20, 63,256,256);
    }
    public static Predicate<IntCords2D> renderEnergyStorage(HandledScreen handledScreen, DrawContext context, int x, int y, long power, long maxPower){
        if(power>0){
            int scaledPower = getScaledAmount(power, maxPower, 62);
            context.drawTexture(RenderLayer::getGuiTextured,TEXTURE, x, y+scaledPower, 0, 113+scaledPower, 16, 62-scaledPower,256,256);
        }
        return ic2d -> (
                ic2d.x>x && ic2d.x<x+16 &&
                ic2d.y>y && ic2d.y<y+62
        );
    }

    public static Predicate<IntCords2D> renderButton(HandledScreen handledScreen, DrawContext context, int x, int y, boolean isActive){
        if(isActive){
            context.drawTexture(RenderLayer::getGuiTextured,TEXTURE, x, y, 33, 0, 18, 18,256,256);
        }
        return ic2d -> (
                ic2d.x>x && ic2d.x<x+18 &&
                        ic2d.y>y && ic2d.y<y+18
        );
    }
    public static Predicate<IntCords2D> renderSmallButton(HandledScreen handledScreen, DrawContext context, int x, int y, boolean isActive){
        if(isActive){
            context.drawTexture(RenderLayer::getGuiTextured,TEXTURE, x, y, 32, 32, 11, 11,256,256);
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

    public static Predicate<IntCords2D> renderCoreHeatBar(Screen screen, DrawContext context, int x, int y, int coreHeat, Identifier texture){
        if(coreHeat>100){
            int scaledPower = getScaledAmount(coreHeat-100, 900, 20);
            context.drawTexture(RenderLayer::getGuiTextured,texture, x, y+scaledPower, 208, scaledPower, 16, 40-scaledPower,256,256);
        } else if (coreHeat>0) {
            int scaledPower = getScaledAmount(coreHeat, 200, 40);
            context.drawTexture(RenderLayer::getGuiTextured,texture, x, y+scaledPower, 208, scaledPower, 16, 40-scaledPower,256,256);
        }
        return ic2d -> (
                ic2d.x>x && ic2d.x<x+16 &&
                        ic2d.y>y && ic2d.y<y+40
        );
    }
    public static Predicate<IntCords2D> renderFurnaceHeatBar(Screen screen, DrawContext context, int x, int y, int timeLeft, int totalTime, Identifier texture){
        if(timeLeft>0){
            int scaledPower = getScaledAmountType2(totalTime-timeLeft, totalTime, 62);
            context.drawTexture(RenderLayer::getGuiTextured,texture, x, y+scaledPower, 176, scaledPower, 14, 62-scaledPower,256,256);
        }
        return ic2d -> (
                ic2d.x>x && ic2d.x<x+14 &&
                        ic2d.y>y && ic2d.y<y+62
        );
    }
}
