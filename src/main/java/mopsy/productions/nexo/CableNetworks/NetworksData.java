package mopsy.productions.nexo.CableNetworks;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;

import static mopsy.productions.nexo.Main.modid;

public class NetworksData extends PersistentState {
    public static NetworksData INSTANCE;
    public HashMap<World, List<CNetwork>> networks = new HashMap<>();

    NetworksData(){
        INSTANCE = this;
    }
    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("test", networks);
        return nbt;
    }

    public static NetworksData readNbt(NbtCompound tag) {
        NetworksData res = new NetworksData();
        res.networks = tag.getInt("totalFurnacesCrafted");
    }

    public static void getNetworkData(MinecraftServer server) {
        // Return existing data instead of loading it from disk if it already exists
        if(INSTANCE != null)
            return;
        // First we get the persistentStateManager for the OVERWORLD
        PersistentStateManager persistentStateManager = server
                .getWorld(World.OVERWORLD).getPersistentStateManager();

        // Calling this reads the file from the disk if it exists, or creates a new one and saves it to the disk
        // You need to use a unique string as the key. You should already have a MODID variable defined by you somewhere in your code. Use that.
        persistentStateManager.getOrCreate(NetworksData::readNbt, NetworksData::new, modid+"_network_data");
    }
    public static void removeData(){
        INSTANCE = null;
    }
    public static void save(){
        INSTANCE.markDirty();
    }
}
