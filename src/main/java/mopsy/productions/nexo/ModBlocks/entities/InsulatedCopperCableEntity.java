package mopsy.productions.nexo.ModBlocks.entities;

import mopsy.productions.nexo.CableNetworks.CNetworkID;
import mopsy.productions.nexo.registry.ModdedBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import static mopsy.productions.nexo.Main.server;

@SuppressWarnings("UnstableApiUsage")
public class InsulatedCopperCableEntity extends BlockEntity{

    public CNetworkID networkID;

    public InsulatedCopperCableEntity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.INSULATED_COPPER_CABLE, pos, state);
    }
    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        if(networkID!=null) {
            nbt.putString("id_world", networkID.world().getRegistryKey().getValue().toString());
            nbt.putInt("id_index", networkID.index());
        }
    }
    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        if(nbt.contains("id_world")&&nbt.contains("id_index")) {
            networkID = new CNetworkID(
                    server.getWorld(RegistryKey.of(Registry.WORLD_KEY, Identifier.tryParse(nbt.getString("id_world")))),
                    nbt.getInt("id_index"));
        }
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, InsulatedCopperCableEntity entity) {
        if(world.isClient)return;

    }
}
