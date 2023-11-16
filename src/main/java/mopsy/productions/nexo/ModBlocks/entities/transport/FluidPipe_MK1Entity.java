package mopsy.productions.nexo.ModBlocks.entities.transport;

import mopsy.productions.nexo.registry.ModdedBlockEntities;
import mopsy.productions.nexo.registry.ModdedBlocks;
import mopsy.productions.nexo.util.DualType;
import mopsy.productions.nexo.util.NEXORotation;
import mopsy.productions.nexo.util.PipeEndState;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.*;

@SuppressWarnings("UnstableApiUsage")
public class FluidPipe_MK1Entity extends BlockEntity {

    public Set<Storage<FluidVariant>> connectedOutputStorages = new HashSet<>(6);
    public Set<Storage<FluidVariant>> connectedInputStorages = new HashSet<>(6);
    public Map<NEXORotation,PipeEndState> endStates = new HashMap<>();
    private boolean initializedStates = false;



    public FluidPipe_MK1Entity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.FLUID_PIPE_MK1, pos, state);
        endStates.putAll(Map.of(
                NEXORotation.UP,PipeEndState.NONE,
                NEXORotation.DOWN,PipeEndState.NONE,
                NEXORotation.NORTH,PipeEndState.NONE,
                NEXORotation.EAST,PipeEndState.NONE,
                NEXORotation.SOUTH,PipeEndState.NONE,
                NEXORotation.WEST,PipeEndState.NONE
        ));
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
    private static PipeEndState getStateForRotation(WorldAccess world, BlockPos pos, NEXORotation rotation){
        if(world.getBlockState(pos.add(rotation.transformToVec3i())).isOf(ModdedBlocks.Blocks.get("fluid_pipe_mk1")))
            return PipeEndState.PIPE;
        else if (FluidStorage.SIDED.find((World)world,pos.add(rotation.transformToVec3i()),rotation.direction)!=null)
            return PipeEndState.IN_OUT;
        else
            return PipeEndState.NONE;

    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, FluidPipe_MK1Entity entity) {
        if(!entity.initializedStates){
            for(NEXORotation rotation : NEXORotation.values()){
                entity.endStates.put(rotation,getStateForRotation(world,entity.pos,rotation));
            }
            entity.initializedStates = true;
        }


        if(world.isClient)return;

        updateStorages(world,blockPos,entity);

        try (Transaction transaction = Transaction.openOuter()) {
            for (Storage<FluidVariant> inputStorage : entity.connectedInputStorages) {
                for(StorageView<FluidVariant> inputStorageView : inputStorage){
                    try (Transaction inputTransaction = transaction.openNested()) {
                        long inputted;
                        try (Transaction simInputTrans = inputTransaction.openNested()) {
                            inputted = inputStorageView.extract(inputStorageView.getResource(), 405, inputTransaction);
                            simInputTrans.abort();
                        }

                        if (inputted > 0) {

                            List<Storage<FluidVariant>> storages = new ArrayList<>(entity.getAllOutputStorages());
                            Collections.shuffle(storages);

                            long totalExchanged = 0;
                            while (!storages.isEmpty() && inputted > 0) {
                                try (Transaction transaction = Transaction.openOuter()) {
                                    totalExchanged += EnergyStorageUtil.move(
                                            entity.energyStorage,
                                            storages.get(storages.size() - 1),
                                            POWER_RATE - totalExchanged,
                                            transaction
                                    );
                                    transaction.commit();
                                }
                                storages.remove(storages.size() - 1);
                            }
                        }else inputTransaction.abort();
                    }
                }
            }
        }

    }
    private Set<Storage<FluidVariant>> getAllOutputStorages(){

        Set<Storage<FluidVariant>> res = new HashSet<>();

        for(FluidPipe_MK1Entity pipe : getAllConnectedPipes()){
            res.addAll(pipe.connectedOutputStorages);
        }

        return res;
    }
    private List<FluidPipe_MK1Entity> getAllConnectedPipes(){
        List<FluidPipe_MK1Entity> res = new ArrayList<>();
        List<FluidPipe_MK1Entity> toCheck = new ArrayList<>();
        List<BlockPos> shouldIgnore = new ArrayList<>();
        shouldIgnore.add(this.pos);
        toCheck.add(this);

        while(!toCheck.isEmpty()){
            int index = toCheck.size()-1;
            res.add(toCheck.get(index));
            toCheck.addAll(getSurroundingPipePosses(toCheck.get(toCheck.size()-1).pos, shouldIgnore));
            toCheck.remove(index);
        }

        return res;
    }
    private List<FluidPipe_MK1Entity> getSurroundingPipePosses(BlockPos pos, List<BlockPos> shouldIgnore){
        List<FluidPipe_MK1Entity> res = new ArrayList<>(6);
        for(Direction direction : Direction.values()){
             BlockPos iteratorPos = pos.offset(direction);
             if(!shouldIgnore.contains(iteratorPos))
                if(world.getBlockEntity(iteratorPos) instanceof FluidPipe_MK1Entity pipe) {
                    res.add(pipe);
                    shouldIgnore.add(iteratorPos);
                }
        }
        return res;
    }
    private static boolean updateStorages(World world, BlockPos pos, FluidPipe_MK1Entity entity){
        Set<DualType<NEXORotation,Storage<FluidVariant>>> storages = entity.getSurroundingStorages(world,pos);
        Set<Storage<FluidVariant>> inputStorages = new HashSet<>(6);
        Set<Storage<FluidVariant>> outputStorages = new HashSet<>(6);
        for (DualType<NEXORotation,Storage<FluidVariant>> dualType : storages) {
            if(entity.endStates.get(dualType.var1).isInput()){
                inputStorages.add(dualType.var2);
            }
            if(entity.endStates.get(dualType.var1).isOutput()){
                outputStorages.add(dualType.var2);
            }
        }
        boolean changed = false;
        if(!entity.connectedInputStorages.equals(inputStorages)) {
            entity.connectedInputStorages = inputStorages;
            changed = true;
        }
        if(!entity.connectedOutputStorages.equals(outputStorages)) {
            entity.connectedOutputStorages = outputStorages;
            changed = true;
        }
        return changed;
    }
    private Set<DualType<NEXORotation,Storage<FluidVariant>>> getSurroundingStorages(World world, BlockPos pos){
        Set<DualType<NEXORotation,Storage<FluidVariant>>> res = new HashSet<>();
        for(NEXORotation rotation : NEXORotation.values()){
            BlockPos calculatedPos = pos.offset(rotation.direction);
            if(!(world.getBlockEntity(calculatedPos) instanceof FluidPipe_MK1Entity)) {
                Storage<FluidVariant> storage = FluidStorage.SIDED.find(world, calculatedPos, rotation.direction);
                if (storage != null)
                    res.add(new DualType<>(rotation,storage));
            }
        }
        return res;
    }
}