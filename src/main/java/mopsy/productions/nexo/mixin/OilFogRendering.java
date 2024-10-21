package mopsy.productions.nexo.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import mopsy.productions.nexo.ModFluids.CrudeOilFluid;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class OilFogRendering {
    @Shadow private static float red;

    @Shadow private static float green;

    @Shadow private static float blue;

    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci){
        if (MinecraftClient.getInstance().world==null)return;
        FluidState fluidState = MinecraftClient.getInstance().world.getFluidState(camera.getBlockPos());
        if (fluidState.getFluid() instanceof CrudeOilFluid){
            RenderSystem.setShaderFogStart(0.20f);
            RenderSystem.setShaderFogEnd(0.6f);
            ci.cancel();
        }
    }
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld$Properties;getHorizonShadingRatio()F"))
    private static void render(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci){
        FluidState fluidState = world.getFluidState(camera.getBlockPos());
        if(fluidState.getFluid() instanceof CrudeOilFluid){
            red = 0.03f;
            green = 0.015f;
            blue = 0;
        }
    }
}
