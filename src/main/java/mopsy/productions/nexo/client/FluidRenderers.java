package mopsy.productions.nexo.client;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.FlowableFluid;

public class FluidRenderers {
    public static void regFluidRenderer(FlowableFluid fluid, int color){
        FluidRenderHandlerRegistry.INSTANCE.register(fluid, SimpleFluidRenderHandler.coloredWater(color));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), fluid);
    }
}
