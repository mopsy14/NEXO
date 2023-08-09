package mopsy.productions.nexo.CableNetworks;

import mopsy.productions.nexo.ModBlocks.entities.InsulatedCopperCableEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static mopsy.productions.nexo.Main.*;

public class CNetworkManager {
    public static HashMap<World, List<CNetwork>> networks = new HashMap<>();
    public static boolean isLoaded = false;
    public static List<Integer> availableIndexes = new ArrayList<>();
    private static final HashMap<World, Integer> worldTickCounters = new HashMap<>();

    public static void loadData(){
        genAvailableIndexes();
        try {
            NbtCompound nbt = NbtIo.readCompressed(getFile(server));
            readNbt(nbt);
        } catch (IOException e) {
            LOGGER.warn("Something went wrong while trying to read the nexo_network_data.dat file");
            e.printStackTrace();
        }
    }

    public static void regEvents(){
        ServerLifecycleEvents.SERVER_STARTING.addPhaseOrdering(new Identifier(modid,"set_server"), new Identifier(modid,"get_networks"));
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            try {
                NbtIo.writeCompressed(writeNbt(new NbtCompound()), getFile(server));
            } catch (IOException e) {
                LOGGER.warn("Something went wrong while trying to read the nexo_network_data.dat file");
                e.printStackTrace();
            }
        });
        ServerTickEvents.END_WORLD_TICK.register((world -> {
            if(!CNetworkManager.isLoaded)return;
            final Integer val = worldTickCounters.get(world);
            if(val!=null){
                if(val == 9){
                    worldTickCounters.put(world, 0);
                    tickWorldNetworks(world);
                }else
                    worldTickCounters.put(world, val+1);
            }else{
                worldTickCounters.put(world,0);
                tickWorldNetworks(world);
            }
        }));
    }
    private static void tickWorldNetworks(World world){
        if(!networks.containsKey(world))networks.put(world, new ArrayList<>());
        for(CNetwork network : networks.get(world))
            network.tick();
    }

    private static void genAvailableIndexes() {
        availableIndexes = new ArrayList<>(131072);
        for (int i = 0; i < 131072; i++) {
            availableIndexes.add(i);
        }
    }

    private static File getFile(MinecraftServer server){
        File file = new File(server.getRunDirectory().getAbsolutePath().concat(File.separator + "data" + File.separator + "nexo_network_data.dat"));
        try {
            if(file.createNewFile()){
                NbtIo.writeCompressed(new NbtCompound(), file);
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    private static void readNbt(NbtCompound nbt){
        networks = CNetworkSerializer.readNetworks(nbt);
        isLoaded = true;
    }
    private static NbtCompound writeNbt(NbtCompound nbt){
        isLoaded = false;
        return CNetworkSerializer.writeNetworks(nbt,networks);
    }
    private static void addNetwork(CNetwork network){
        if(!networks.containsKey(network.id.world())) {
            networks.put(network.id.world(), new ArrayList<>());
        }
        networks.get(network.id.world()).add(network);
    }

    public static CNetwork getNetworkFromID(CNetworkID id){
        for(CNetwork network : networks.get(id.world())){
            if(network.id.equals(id))return network;
        }
        return null;
    }

    public static int getNetworkIndexFromID(CNetworkID id){
        List<CNetwork> networkList = networks.get(id.world());
        for (int i = 0; i < networkList.size(); i++) {
            if(networkList.get(i).id == id)return i;
        }
        return -1;
    }

    public static CNetwork createNetworkFromCable(InsulatedCopperCableEntity entity){
        CNetwork network = new CNetwork(entity.getWorld(),availableIndexes.get(0));
        availableIndexes.remove(0);
        network.cableEntities.put(entity.getPos(),entity);
        addNetwork(network);

        return network;
    }

    public static void mergeNetworks(List<CNetwork> toMergeNetworks, boolean withRemoval){
        if(toMergeNetworks.size()==2)
            merge2Networks(toMergeNetworks.get(0),toMergeNetworks.get(1), withRemoval);
        else if(toMergeNetworks.size()>2){
            for (int i = 1; i < toMergeNetworks.size()-1; i++) {
                merge2Networks(toMergeNetworks.get(0),toMergeNetworks.get(i), withRemoval);
            }
        }else
            LOGGER.error("Can not merge "+ toMergeNetworks.size() +" networks with each other");
    }
    private static void merge2Networks(CNetwork n1, CNetwork n2, boolean withRemoval){
        if(!n1.id.world().equals(n2.id.world()))return;
        for (InsulatedCopperCableEntity entity : n2.cableEntities.values()) {
            n1.cableEntities.put(entity.getPos(),entity);
        }
        if(withRemoval)
            networks.get(n2.id.world()).remove(n2);
    }
}
