package mopsy.productions.nexo.mixin;

import mopsy.productions.nexo.interfaces.IData;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PersistentStateManager.class)
public abstract class WorldData implements IData {

    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData(){
        if(this.persistentData == null)
            this.persistentData = new NbtCompound();

        return persistentData;
    }

    private NbtCompound createNbt(){
        return new NbtCompound();
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfoReturnable info){
        if(persistentData != null){
            nbt.put("nexo.entitydata", persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info){
        if(nbt.contains("nexo.entitydata", 10)){
            persistentData = nbt.getCompound("nexo.entitydata");
        }
    }
}
