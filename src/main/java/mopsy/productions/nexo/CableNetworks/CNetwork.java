package mopsy.productions.nexo.CableNetworks;

import mopsy.productions.nexo.ModBlocks.entities.InsulatedCopperCableEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class CNetwork{
    public CNetworkID id;
    public HashMap<BlockPos,InsulatedCopperCableEntity> cableEntities = new HashMap<>();
    public CNetwork(World world, int index){
        id = new CNetworkID(world,index);
    }


}
