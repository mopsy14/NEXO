package mopsy.productions.nexo.ModBlocks.entities.machines;

import mopsy.productions.nexo.interfaces.IEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.ArrayList;

public abstract class AbstractGeneratorEntity extends BlockEntity implements IEnergyStorage {
    public final SimpleEnergyStorage energyStorage;
    public AbstractGeneratorEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, long powerCapacity, long powerMaxInsert, long powerMaxExtract) {
        super(type, pos, state);
        energyStorage = new SimpleEnergyStorage(powerCapacity, powerMaxInsert, powerMaxExtract) {
            @Override
            protected void onFinalCommit() {
                markDirty();
            }
        };
    }
    protected static void tryExportPower(AbstractGeneratorEntity entity) {
        if(entity.energyStorage.amount==0) return;

        ArrayList<EnergyStorage> adjacentStorages = new ArrayList<>();
        Direction direction = Direction.NORTH;
        for (int i = 0; i < 6; i++) {
            EnergyStorage storage;
            direction = direction.rotateYClockwise();
            if (i < 4) {
                storage = EnergyStorage.SIDED.find(entity.world, entity.pos.offset(direction), direction.getOpposite());
            } else if (i == 4) {
                storage = EnergyStorage.SIDED.find(entity.world, entity.pos.offset(Direction.UP), Direction.DOWN);
            } else {
                storage = EnergyStorage.SIDED.find(entity.world, entity.pos.offset(Direction.DOWN), Direction.UP);
            }
            if (storage != null) adjacentStorages.add(storage);
        }
        for (int i = 0; i < adjacentStorages.size();) {
            if(!adjacentStorages.get(i).supportsInsertion()||adjacentStorages.get(i).getCapacity()-adjacentStorages.get(i).getAmount()==0)
                adjacentStorages.remove(adjacentStorages.get(i));
            else i++;
        }
        doExportPowerTransactions(adjacentStorages, entity);
    }
    private static long doExportPowerTransactions(ArrayList<EnergyStorage> adjacentStorages, AbstractGeneratorEntity entity){
        long totalMoved = 0;
        while (totalMoved<entity.energyStorage.maxExtract){
            if(adjacentStorages.size()==0) return totalMoved;
            int divider = adjacentStorages.size();
            int maxTransaction = Math.round(((entity.energyStorage.maxExtract-totalMoved)/(float)divider));
            ArrayList<EnergyStorage> toRemove = new ArrayList<>();
            for(EnergyStorage storage : adjacentStorages) {
                long amountMoved = EnergyStorageUtil.move(
                        entity.energyStorage,
                        storage,
                        maxTransaction==0&&maxTransaction<(entity.energyStorage.maxExtract-totalMoved)/(float)divider?1:maxTransaction,
                        null
                );
                totalMoved +=amountMoved;
                if(amountMoved<maxTransaction)
                    toRemove.add(storage);
                if(totalMoved>=entity.energyStorage.maxExtract)
                    return totalMoved;
            }
            for(EnergyStorage storage : toRemove)
                adjacentStorages.remove(storage);
        }
        return totalMoved;
    }
}