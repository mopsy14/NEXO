package mopsy.productions.nexo.mixin;

import mopsy.productions.nexo.ModItems.tools.GeigerCounterItem;
import mopsy.productions.nexo.mechanics.radiation.RadiationHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public abstract class PlayerAddItemMixin {

    @Shadow @Final public PlayerEntity player;

    @Inject(method = "addStack(ILnet/minecraft/item/ItemStack;)I", at = @At("HEAD"))
    protected void injectIntoAddStack(int slot, ItemStack stack, CallbackInfoReturnable<Integer> cir){
        if (stack.isOf(GeigerCounterItem.INSTANCE)) {
            if(player instanceof ServerPlayerEntity serverPlayer) {
                RadiationHelper.sendRadiationUpdatePackage(serverPlayer);
                RadiationHelper.sendRadiationPerSecondUpdatePackage(serverPlayer);
            }
        }
    }

    @Inject(method = "setStack", at = @At("HEAD"))
    protected void injectIntoAddStack(int slot, ItemStack stack, CallbackInfo ci){
        if (stack.isOf(GeigerCounterItem.INSTANCE)) {
            if(player instanceof ServerPlayerEntity serverPlayer) {
                RadiationHelper.sendRadiationUpdatePackage(serverPlayer);
                RadiationHelper.sendRadiationPerSecondUpdatePackage(serverPlayer);
            }
        }
    }
}
