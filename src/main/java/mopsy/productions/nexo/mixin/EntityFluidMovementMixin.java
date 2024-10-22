package mopsy.productions.nexo.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static mopsy.productions.nexo.registry.ModdedFluids.OIL_TAG;

@Mixin(Entity.class)
public abstract class EntityFluidMovementMixin {
    @Shadow public abstract boolean updateMovementInFluid(TagKey<Fluid> tag, double speed);

    @Inject(method="updateWaterState",at=@At("RETURN"), cancellable = true)
    private void updateWaterState(CallbackInfoReturnable<Boolean> cir){
        if(updateMovementInFluid(OIL_TAG,0.01))
            cir.setReturnValue(true);
    }
}
