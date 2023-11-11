package mopsy.productions.nexo.ModBlocks.entities.transport;

import mopsy.productions.nexo.registry.ModdedBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import team.reborn.energy.api.EnergyStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public class FluidPipe_MK1Entity extends BlockEntity {

    public Set<EnergyStorage> connectedStorages = new HashSet<>(6);

    public FluidPipe_MK1Entity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.FLUID_PIPE_MK1, pos, state);
    }
    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        //nbt.putLong("power", energyStorage.amount);
    }
    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        //energyStorage.amount = nbt.getLong("power");
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, FluidPipe_MK1Entity entity) {
        if(world.isClient)return;

        updateStorages(world,blockPos,entity);
/*
        if(entity.energyStorage.amount > 0){

            List<EnergyStorage> storages = new ArrayList<>(entity.getAllOutputStorages());
            Collections.shuffle(storages);

            long totalExchanged = 0;
            while(!storages.isEmpty() && entity.energyStorage.amount>0){
                try (Transaction transaction = Transaction.openOuter()) {
                    totalExchanged += EnergyStorageUtil.move(
                            entity.energyStorage,
                            storages.get(storages.size()-1),
                            POWER_RATE-totalExchanged,
                            transaction
                    );
                    transaction.commit();
                }
                storages.remove(storages.size()-1);
            }
        }
 */
    }
    private Set<EnergyStorage> getAllOutputStorages(){

        Set<EnergyStorage> res = new HashSet<>();

        for(FluidPipe_MK1Entity cable : getAllConnectedCables()){
            res.addAll(cable.connectedStorages);
        }

        return res;
    }
    private List<FluidPipe_MK1Entity> getAllConnectedCables(){
        List<FluidPipe_MK1Entity> res = new ArrayList<>();
        List<FluidPipe_MK1Entity> toCheck = new ArrayList<>();
        List<BlockPos> shouldIgnore = new ArrayList<>();
        shouldIgnore.add(this.pos);
        toCheck.add(this);

        while(!toCheck.isEmpty()){
            int index = toCheck.size()-1;
            res.add(toCheck.get(index));
            toCheck.addAll(getSurroundingCablePosses(toCheck.get(toCheck.size()-1).pos, shouldIgnore));
            toCheck.remove(index);
        }

        return res;
    }
    private List<FluidPipe_MK1Entity> getSurroundingCablePosses(BlockPos pos, List<BlockPos> shouldIgnore){
        List<FluidPipe_MK1Entity> res = new ArrayList<>(6);
        for(Direction direction : Direction.values()){
             BlockPos iteratorPos = pos.offset(direction);
             if(!shouldIgnore.contains(iteratorPos))
                if(world.getBlockEntity(iteratorPos) instanceof FluidPipe_MK1Entity cable) {
                    res.add(cable);
                    shouldIgnore.add(iteratorPos);
                }
        }
        return res;
    }
    private Set<FluidPipe_MK1Entity> getConnectedCables(){
        Set<FluidPipe_MK1Entity> res = new HashSet<>(6);
        if(world.getBlockEntity(pos.up())instanceof FluidPipe_MK1Entity)
            res.add((FluidPipe_MK1Entity) world.getBlockEntity(pos.up()));
        if(world.getBlockEntity(pos.down())instanceof FluidPipe_MK1Entity)
            res.add((FluidPipe_MK1Entity) world.getBlockEntity(pos.down()));
        if(world.getBlockEntity(pos.north())instanceof FluidPipe_MK1Entity)
            res.add((FluidPipe_MK1Entity) world.getBlockEntity(pos.north()));
        if(world.getBlockEntity(pos.east())instanceof FluidPipe_MK1Entity)
            res.add((FluidPipe_MK1Entity) world.getBlockEntity(pos.east()));
        if(world.getBlockEntity(pos.south())instanceof FluidPipe_MK1Entity)
            res.add((FluidPipe_MK1Entity) world.getBlockEntity(pos.south()));
        if(world.getBlockEntity(pos.west())instanceof FluidPipe_MK1Entity)
            res.add((FluidPipe_MK1Entity) world.getBlockEntity(pos.west()));
        return res;
    }
    private static boolean updateStorages(World world, BlockPos pos, FluidPipe_MK1Entity entity){
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
            BlockPos calculatedPos = pos.offset(direction);
            if(!(world.getBlockEntity(calculatedPos) instanceof FluidPipe_MK1Entity)) {
                EnergyStorage storage = EnergyStorage.SIDED.find(world, calculatedPos, direction);
                if (storage != null)
                    res.add(storage);
            }
        }
        return res;
    }
}