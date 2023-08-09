package mopsy.productions.nexo.CableNetworks;

import mopsy.productions.nexo.ModBlocks.entities.InsulatedCopperCableEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.*;

import static mopsy.productions.nexo.CableNetworks.CNetworkManager.availableIndexes;
import static mopsy.productions.nexo.Main.LOGGER;
import static mopsy.productions.nexo.Main.server;

public class CNetworkSerializer {
    //Write network code:
    public static NbtCompound writeNetworks(NbtCompound nbt, HashMap<World, List<CNetwork>> allNetworks){
        allNetworks.forEach((world,networks)->{
            NbtCompound tempNBT = new NbtCompound();
            writeWorldNetworks(tempNBT,world,networks);
            nbt.put(world.getRegistryKey().getValue().toString(),tempNBT);
            System.out.println(networks);
        });

        return nbt;
    }
    private static void writeWorldNetworks(NbtCompound nbt, World world, List<CNetwork> networks){
        for (CNetwork network : networks) {
            NbtCompound tempNBT = new NbtCompound();
            writeNetwork(tempNBT,network);
            nbt.put(String.valueOf(network.id.index()),tempNBT);
            nbt.putIntArray("network_count",indexesFromNetworks(networks));
        }

    }
    private static int[] indexesFromNetworks(List<CNetwork> networks){
        int[] res = new int[networks.size()];
        for (int i = 0; i < networks.size(); i++){
            res[i] = networks.get(i).id.index();
        }

        return res;
    }

    static int writeNetworkI;
    private static void writeNetwork(NbtCompound nbt, CNetwork network){
        writeNetworkI=0;
        network.cableEntities.forEach((blockPos,entity)->{
            NbtCompound tempNbt = new NbtCompound();
            writeBlockPos(tempNbt,blockPos);
            nbt.put(String.valueOf(writeNetworkI),tempNbt);
            writeNetworkI++;
        });
        nbt.putInt("cable_count",writeNetworkI);
    }
    private static void writeBlockPos(NbtCompound nbt, BlockPos blockPos){
        nbt.putInt("x",blockPos.getX());
        nbt.putInt("y",blockPos.getY());
        nbt.putInt("z",blockPos.getZ());
    }



    //Read network code:
    public static HashMap<World, List<CNetwork>> readNetworks(NbtCompound nbt){
        Set<String> keys = nbt.getKeys();
        HashMap<World,List<CNetwork>> res = new HashMap<>(keys.size());
        for (String key : keys){
            World currentWorld = server.getWorld(RegistryKey.of(Registry.WORLD_KEY, Identifier.tryParse(key)));
            if(currentWorld!=null)
                res.put(currentWorld, readWorldNetworks((NbtCompound)Objects.requireNonNull(nbt.get(key)),currentWorld));
            else
                LOGGER.error("Dimension "+key+" could not be found while reading nexo_network_data.dat");
        }
        System.out.println(res);
        return res;
    }
    private static List<CNetwork> readWorldNetworks(NbtCompound nbt, World world){
        int[] networksIndexes = nbt.getIntArray("network_count");
        networksIndexes = Arrays.stream(networksIndexes).sorted().toArray();
        List<CNetwork> res = new ArrayList<>(networksIndexes.length);
        for(int i = networksIndexes.length-1; i >= 0; i--){
            availableIndexes.remove(networksIndexes[i]);
            res.add(readNetwork((NbtCompound)Objects.requireNonNull(nbt.get(String.valueOf(networksIndexes[i]))),world, networksIndexes[i]));
        }

        return res;
    }
    private static CNetwork readNetwork(NbtCompound nbt, World world, int index){
        CNetwork res = new CNetwork(world,index);
        for (int i = 0; i < nbt.getInt("cable_count"); i++) {
            NbtCompound cableNbt = (NbtCompound)Objects.requireNonNull(nbt.get(String.valueOf(i)));
            BlockPos cablePos = readBlockPos(cableNbt);
            res.cableEntities.put(cablePos, (InsulatedCopperCableEntity)world.getBlockEntity(cablePos));
        }

        return res;
    }
    private static BlockPos readBlockPos(NbtCompound nbt){
        return new BlockPos(nbt.getInt("x"),nbt.getInt("y"),nbt.getInt("z"));
    }
}
