package mopsy.productions.nucleartech.mechanics.radiation;

import mopsy.productions.nucleartech.interfaces.IData;
import mopsy.productions.nucleartech.interfaces.IItemRadiation;
import mopsy.productions.nucleartech.networking.PacketManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
        float calculatedRadiation = getRadiationPerSecondFromInventory(player.getInventory());
        changePlayerRadiationPerSecond(player, calculatedRadiation);
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
}
