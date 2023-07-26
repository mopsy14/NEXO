package mopsy.productions.nexo.CableNetworks;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static mopsy.productions.nexo.Main.LOGGER;

public class CNetworkManager {
    public static HashMap<World, List<CNetwork>> networks = new HashMap<>();
    public static boolean isLoaded = false;

    public static void regEvents(){
        ServerLifecycleEvents.SERVER_STARTING.register((server)->{
            try {
                NbtCompound nbt = NbtIo.readCompressed(getFile(server));
                readNbt(nbt);
            } catch (IOException e) {
                LOGGER.warn("Something went wrong while trying to read the nexo_network_data.dat file");
                e.printStackTrace();
            }
        });
        ServerLifecycleEvents.SERVER_STOPPING.register((server -> {
            try {
                NbtIo.writeCompressed(writeNbt(new NbtCompound()), getFile(server));
            } catch (IOException e) {
                LOGGER.warn("Something went wrong while trying to read the nexo_network_data.dat file");
                e.printStackTrace();
            }
        }));
    }
    private static File getFile(MinecraftServer server){
        File file = new File(server.getRunDirectory().getAbsolutePath().concat(File.separator + "data" + File.separator + "nexo_network_data.dat"));
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    private static void readNbt(NbtCompound nbt){

    }
    private static NbtCompound writeNbt(NbtCompound nbt){

        return nbt;
    }
}
