package mopsy.productions.nexo.mixin;

import mopsy.productions.nexo.CableNetworks.CNetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class WorldLoadMixin {
    @Inject(method = "createWorlds", at = @At("TAIL"))
    protected void injectWriteMethod(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci){
        CNetworkManager.loadData();
    }
}
