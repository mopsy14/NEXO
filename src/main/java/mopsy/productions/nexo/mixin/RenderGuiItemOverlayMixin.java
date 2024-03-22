package mopsy.productions.nexo.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import mopsy.productions.nexo.ModItems.blocks.BatteryMK1Item;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.reborn.energy.api.base.SimpleEnergyItem;

@Mixin(ItemRenderer.class)
public abstract class RenderGuiItemOverlayMixin {
    @Shadow protected abstract void renderGuiQuad(BufferBuilder buffer, int x, int y, int width, int height, int red, int green, int blue, int alpha);

    @Inject(method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At("RETURN"))
    protected void injectIntoRenderItemOverlay(TextRenderer renderer, ItemStack stack, int x, int y, String countLabel, CallbackInfo ci){
        if (stack.getItem() instanceof BatteryMK1Item batteryItem) {
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.disableBlend();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            int coloredBarPixels = getScaledPower(batteryItem, stack);
            this.renderGuiQuad(bufferBuilder, x + 2, y + 13, 12, 2, 25, 25, 25, 255);
            this.renderGuiQuad(bufferBuilder, x + 2, y + 13, coloredBarPixels, 1, 255, 240, 0, 255);
            RenderSystem.enableBlend();
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
        }
    }
    @Unique
    private static int getScaledPower(SimpleEnergyItem energyItem, ItemStack itemStack){
        return (int)(energyItem.getEnergyCapacity(itemStack)!=0 && energyItem.getStoredEnergy(itemStack)!=0 ? energyItem.getStoredEnergy(itemStack)*12/energyItem.getEnergyCapacity(itemStack) : 0);
    }
}
