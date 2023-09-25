package mopsy.productions.nexo.ModBlocks.entities;

import mopsy.productions.nexo.registry.ModdedBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.*;

@SuppressWarnings("UnstableApiUsage")
public class InsulatedCopperCableEntity extends BlockEntity{

    public static final long POWER_CAPACITY = 10000;
    public static final long MAX_POWER_INSERT = 10000;
    public static final long MAX_POWER_EXTRACT = 0;

    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(POWER_CAPACITY,MAX_POWER_INSERT,MAX_POWER_EXTRACT){
        @Override
        protected void onFinalCommit() {
        }
    };

    public Set<EnergyStorage> connectedStorages = new HashSet<>(6);

    public InsulatedCopperCableEntity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.INSULATED_COPPER_CABLE, pos, state);
    }
    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
    }
    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, InsulatedCopperCableEntity entity) {
        if(world.isClient)return;

        updateStorages(world,blockPos,entity);

        if(entity.energyStorage.amount > 0){

            List<InsulatedCopperCableEntity> cables = new ArrayList<>(entity.getConnectedCables());
            Collections.shuffle(cables);


        }
    }
    private Set<EnergyStorage> getAllOutputStorages(){

        Set<EnergyStorage> res = new HashSet<>();

        for(InsulatedCopperCableEntity cable : getConnectedCables()){
            res.addAll(cable.connectedStorages);
        }

        return res;
    }
    private List<InsulatedCopperCableEntity> getAllConnectedCables(){
        List<InsulatedCopperCableEntity> res = new ArrayList<>();
        List<InsulatedCopperCableEntity> toCheck = new ArrayList<>();
        List<BlockPos> shouldIgnore = new ArrayList<>();
        shouldIgnore.add(this.pos);
        toCheck.add(this);

        while(!toCheck.isEmpty()){
            getSurroundingCablePosses(toCheck.get(toCheck.size()-1).pos, shouldIgnore);
        }

        return res;
    }
    private List<InsulatedCopperCableEntity> getSurroundingCablePosses(BlockPos pos, List<BlockPos> shouldIgnore){
        List<InsulatedCopperCableEntity> res = new ArrayList<>(6);
        for(Direction direction : Direction.values()){
             BlockPos iteratorPos = pos.add(direction.getVector());
             if(!shouldIgnore.contains(iteratorPos))
                if(world.getBlockEntity(iteratorPos) instanceof InsulatedCopperCableEntity cable) {
                    res.add(cable);
                    shouldIgnore.add(iteratorPos);
                }
        }
        return res;
    }
    private Set<InsulatedCopperCableEntity> getConnectedCables(){
        Set<InsulatedCopperCableEntity> res = new HashSet<>(6);
        if(world.getBlockEntity(pos.up())instanceof InsulatedCopperCableEntity)
            res.add((InsulatedCopperCableEntity) world.getBlockEntity(pos.up()));
        if(world.getBlockEntity(pos.down())instanceof InsulatedCopperCableEntity)
            res.add((InsulatedCopperCableEntity) world.getBlockEntity(pos.down()));
        if(world.getBlockEntity(pos.north())instanceof InsulatedCopperCableEntity)
            res.add((InsulatedCopperCableEntity) world.getBlockEntity(pos.north()));
        if(world.getBlockEntity(pos.east())instanceof InsulatedCopperCableEntity)
            res.add((InsulatedCopperCableEntity) world.getBlockEntity(pos.east()));
        if(world.getBlockEntity(pos.south())instanceof InsulatedCopperCableEntity)
            res.add((InsulatedCopperCableEntity) world.getBlockEntity(pos.south()));
        if(world.getBlockEntity(pos.west())instanceof InsulatedCopperCableEntity)
            res.add((InsulatedCopperCableEntity) world.getBlockEntity(pos.west()));
        return res;
    }
    private static boolean updateStorages(World world, BlockPos pos, InsulatedCopperCableEntity entity){
        Set<EnergyStorage> storages = entity.getSurroundingStorages(world,pos);
        if(!entity.connectedStorages.equals(storages)){
            entity.connectedStorages = storages;
            return true;
        }
        return false;
    }
    private Set<EnergyStorage> getSurroundingStorages(World world, BlockPos pos){
        Set<EnergyStorage> res = new HashSet<>();
        for(Direction direction : Direction.values()){
            EnergyStorage storage = EnergyStorage.SIDED.find(world,pos.add(direction.getVector()),direction);
            if(storage!=null)
                res.add(storage);
        }
        return res;
    }
}
