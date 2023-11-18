package mopsy.productions.nexo.ModBlocks.entities.transport;

import mopsy.productions.nexo.registry.ModdedBlockEntities;
import mopsy.productions.nexo.registry.ModdedBlocks;
import mopsy.productions.nexo.util.DualType;
import mopsy.productions.nexo.util.FluidTransactionUtils;
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
    private int counter = 0;



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
            return PipeEndState.OUT;
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

        if(entity.counter==20) {
            entity.counter=0;
            updateStorages(world, blockPos, entity);

            try (Transaction transaction = Transaction.openOuter()) {
                for (Storage<FluidVariant> inputStorage : entity.connectedInputStorages) {
                    for (StorageView<FluidVariant> inputStorageView : inputStorage) {
                        try (Transaction inputTransaction = transaction.openNested()) {
                            long inputted;
                            if (inputStorageView.isResourceBlank() || inputStorageView.getAmount() == 0)
                                continue;
                            try (Transaction simInputTrans = inputTransaction.openNested()) {
                                inputted = inputStorageView.extract(inputStorageView.getResource(), 16200, simInputTrans);
                                simInputTrans.abort();
                            }

                            if (inputted > 0) {
                                long toBeOutputted = inputted;

                                List<Storage<FluidVariant>> storages = new ArrayList<>(entity.getAllOutputStorages());
                                while (storages.contains(inputStorage))
                                    storages.remove(inputStorage);
                                Collections.shuffle(storages);

                                if (!storages.isEmpty()) {
                                    long lastTrans = exportToStorages(inputTransaction, inputStorageView, storages, toBeOutputted / storages.size());
                                    toBeOutputted -= lastTrans;
                                    while (lastTrans > 0 && !storages.isEmpty() && toBeOutputted > 0) {
                                        lastTrans = exportToStorages(inputTransaction, inputStorageView, storages, toBeOutputted / storages.size());
                                        toBeOutputted -= lastTrans;
                                    }
                                    if(toBeOutputted>0 && !storages.isEmpty())
                                        exportToStorages(inputTransaction,inputStorageView,storages,1);

                                    inputTransaction.commit();
                                }
                            }
                        }
                    }
                }
                transaction.commit();
            }
        }else
            entity.counter++;

    }

    private static long exportToStorages(Transaction transaction, StorageView<FluidVariant> inputStorage, List<Storage<FluidVariant>> outputStorages, long movePerStorage){
        if (movePerStorage<1)
            return 0;
        long res = 0;
        for (int i = outputStorages.size()-1; i >= 0; i--) {
            try (Transaction moveTransaction = transaction.openNested()) {
                long moved = FluidTransactionUtils.move(inputStorage, outputStorages.get(i), movePerStorage, moveTransaction);
                if(moved<movePerStorage)
                    outputStorages.remove(i);
                res+=moved;
                if(moved>0)
                    moveTransaction.commit();
            }
        }
        return res;
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
            BlockPos calculatedPos = pos.add(rotation.transformToVec3i());
            if(!(world.getBlockEntity(calculatedPos) instanceof FluidPipe_MK1Entity)) {
                Storage<FluidVariant> storage = FluidStorage.SIDED.find(world, calculatedPos, rotation.direction);
                if (storage != null)
                    res.add(new DualType<>(rotation,storage));
            }
        }
        return res;
    }
}