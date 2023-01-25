package mopsy.productions.nucleartech.mechanics.radiation;

import mopsy.productions.nucleartech.interfaces.IEntityDataSaver;
import mopsy.productions.nucleartech.networking.PacketManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import static mopsy.productions.nucleartech.mechanics.radiation.Radiation.getRadiation;
import static mopsy.productions.nucleartech.mechanics.radiation.Radiation.setRadiation;
import static mopsy.productions.nucleartech.registry.Items.Items;

public class RadiationHelper {
    public static void changePlayerRadiation(ServerPlayerEntity player, float radiation){
        setRadiation((IEntityDataSaver) player, radiation);
        if(player.getInventory().contains(new ItemStack(Items.get("geiger_counter")))) {
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeFloat(getRadiation((IEntityDataSaver) player));
            ServerPlayNetworking.send(player, PacketManager.RADIATION_CHANGE_PACKAGE, buffer);
        }
    }
    public static void changePlayerRadiationPerTick(ServerPlayerEntity player, float radiation){
        setRadiationPerTick((IEntityDataSaver) player, radiation);
        if(player.getInventory().contains(new ItemStack(Items.get("geiger_counter")))) {
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeFloat(getRadiation((IEntityDataSaver) player));
            ServerPlayNetworking.send(player, PacketManager.RADIATION_PER_TICK_CHANGE_PACKAGE, buffer);
        }
    }

    private static float getRadiationPerTick(IEntityDataSaver player){
        NbtCompound nbt = player.getPersistentData();
        return nbt.getFloat("radiation/tick");
    }
    private static void setRadiationPerTick(IEntityDataSaver player, float radiation) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putFloat("radiation/tick", radiation);
    }

    public static void updateRadiationPerTick(MinecraftServer server){
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){
            updatePlayerRadiationPerTick(player);
        }
    }
    private static void updatePlayerRadiationPerTick(ServerPlayerEntity player){
        //changePlayerRadiation(player, Radiation.getRadiation((IEntityDataSaver) player)+1);
    }
    public static void updateRadiation(MinecraftServer server){
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()){
            updatePlayerRadiation(player);
        }
    }
    private static void updatePlayerRadiation(ServerPlayerEntity player){
        changePlayerRadiation(player, getRadiation((IEntityDataSaver) player)+(getRadiationPerTick((IEntityDataSaver) player)/2));
    }
}
