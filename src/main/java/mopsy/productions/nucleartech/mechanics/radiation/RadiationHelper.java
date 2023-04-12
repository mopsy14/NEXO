package mopsy.productions.nucleartech.mechanics.radiation;

import mopsy.productions.nucleartech.interfaces.IArmorRadiationProtection;
import mopsy.productions.nucleartech.interfaces.IData;
import mopsy.productions.nucleartech.interfaces.IItemRadiation;
import mopsy.productions.nucleartech.networking.PacketManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import static mopsy.productions.nucleartech.mechanics.radiation.Radiation.getRadiation;
import static mopsy.productions.nucleartech.mechanics.radiation.Radiation.setRadiation;
import static mopsy.productions.nucleartech.registry.ModdedItems.Items;

public class RadiationHelper {
    public static void changePlayerRadiation(ServerPlayerEntity player, float radiation){
        if(radiation != getRadiation((IData) player)) {
            if (radiation < 0)
                setRadiation((IData) player, 0);
            else if (radiation > 150)
                setRadiation((IData) player, 150);
            else
                setRadiation((IData) player, radiation);
            if (player.getInventory().contains(new ItemStack(Items.get("geiger_counter")))) {
                sendRadiationUpdatePackage(player);
            }
        }
    }
    public static void sendRadiationUpdatePackage(ServerPlayerEntity player){
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeFloat(getRadiation((IData) player));
        ServerPlayNetworking.send(player, PacketManager.RADIATION_CHANGE_PACKET, buffer);
    }
    public static void changePlayerRadiationPerSecond(ServerPlayerEntity player, float radiation){
        if(radiation !=getRadiationPerSecond((IData) player)) {
            setRadiationPerSecond((IData) player, radiation);
            if (player.getInventory().contains(new ItemStack(Items.get("geiger_counter")))) {
                sendRadiationPerSecondUpdatePackage(player);
            }
        }
    }
    public static void sendRadiationPerSecondUpdatePackage(ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeFloat(getRadiationPerSecond((IData) player));
        ServerPlayNetworking.send(player, PacketManager.RADIATION_PER_SECOND_CHANGE_PACKET, buffer);
    }

    private static float getRadiationPerSecond(IData player){
        NbtCompound nbt = player.getPersistentData();
        return nbt.getFloat("radiation/s");
    }
    private static void setRadiationPerSecond(IData player, float radiation) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putFloat("radiation/s", radiation);
    }

    public static void updateRadiationPerSecond(MinecraftServer server){
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){
            updatePlayerRadiationPerSecond(player);
        }
    }
    private static void updatePlayerRadiationPerSecond(ServerPlayerEntity player){
        float inventoryRadiation = getRadiationPerSecondFromInventory(player.getInventory());
        float armorRadiationProtection = getRadiationProtectionFromPlayer(player);
        changePlayerRadiationPerSecond(player, Math.max(0,inventoryRadiation*(Math.min(0, 1-armorRadiationProtection))));
    }
    private static float getRadiationPerSecondFromInventory(Inventory inventory){
        float res = 0;
        for(int I =0; I < inventory.size(); I++){
            ItemStack itemStack = inventory.getStack(I);
            if(itemStack.getItem() instanceof IItemRadiation){
                res = res+(((IItemRadiation) itemStack.getItem()).getRadiation() * itemStack.getCount());
            }
        }
        return res;
    }
    private static float getRadiationProtectionFromPlayer(ServerPlayerEntity player){
        float res = 0;
        for(ItemStack stack : player.getArmorItems()){if (stack.getItem() instanceof IArmorRadiationProtection rp) res+=rp.getRadiationProtection();}
        return res;
    }
    public static void updateRadiation(MinecraftServer server){
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){
            if(!player.isCreative()) {
                updatePlayerRadiation(player);
            }
        }
    }
    private static void updatePlayerRadiation(ServerPlayerEntity player){
        float radPerSec = getRadiationPerSecond((IData) player);
        if(!(radPerSec<0.01&&radPerSec> -0.01))
            changePlayerRadiation(player, getRadiation((IData) player)+(radPerSec/2));
    }
    public static void updateRadiationEffects(MinecraftServer server){
        for(ServerPlayerEntity serverPlayer: server.getPlayerManager().getPlayerList()){
            updateRadiationEffects(serverPlayer);
        }
    }
    private static void updateRadiationEffects(ServerPlayerEntity player){
        if(!player.isCreative()){
            float rads = getRadiation((IData) player);
            if(rads>149) {
                player.damage(DamageSource.GENERIC, 20);
            }else if(rads>125) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 3, true, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, 1, true, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 3, true, false, false));
            }else if(rads>100) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 2, true, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, 1, true, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 2, true, false, false));
            }else if(rads>75) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 1, true, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, 1, true, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 1, true, false, false));
            }else if(rads>50){
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 1, true, false, false));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 100, 1, true, false, false));
            } else if(rads>30){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 1, true, false, false));
            }
        }
    }
}
