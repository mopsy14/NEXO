package mopsy.productions.nexo.CableNetworks;

import mopsy.productions.nexo.ModBlocks.entities.InsulatedCopperCableEntity;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class CNetwork{
    public CNetworkID id;
    public HashMap<BlockPos,InsulatedCopperCableEntity> cableEntities = new HashMap<>();
    public List<EnergyStorage> inputStorages = new ArrayList<>();
    public List<EnergyStorage> outputStorages = new ArrayList<>();
    public CNetwork(World world, int index){
        id = new CNetworkID(world,index);
    }
    public void tick(){
        List<EnergyStorage> usableOutputStorages = new ArrayList<>(outputStorages.size());
        usableOutputStorages.addAll(outputStorages);
        Transaction tx = Transaction.openOuter();
        for (EnergyStorage inputStorage : inputStorages){
            while (Math.floorMod(inputStorage.getAmount(), usableOutputStorages.size())>0&&usableOutputStorages.size()!=0) {
                long moved = moveSpread(inputStorage, usableOutputStorages, tx);
                if(moved==0)break;
            }

            if(inputStorage.getAmount()>0) {
                for (EnergyStorage outputStorage : usableOutputStorages) {
                    long emptyAmount = outputStorage.getCapacity() - outputStorage.getAmount();
                    if (emptyAmount == 0) {
                        usableOutputStorages.remove(outputStorage);
                        continue;
                    }
                        usableOutputStorages.remove(outputStorage);
                    EnergyStorageUtil.move(inputStorage, outputStorage, 1, tx);
                }
            }

            if(usableOutputStorages.size() == 0)
                break;
        }
        tx.commit();
    }

    private long moveSpread(EnergyStorage inputStorage, List<EnergyStorage> usableOutputStorages, Transaction tx){
        if(inputStorage.getAmount()>0) {
            long movePerStorage = Math.floorMod(inputStorage.getAmount(), usableOutputStorages.size());
            long totalMoved = 0;
            for (EnergyStorage outputStorage : usableOutputStorages) {
                long emptyAmount = outputStorage.getCapacity() - outputStorage.getAmount();
                if (emptyAmount == 0) {
                    usableOutputStorages.remove(outputStorage);
                    continue;
                }
                if (emptyAmount < movePerStorage)
                    usableOutputStorages.remove(outputStorage);
                long moved = EnergyStorageUtil.move(inputStorage, outputStorage, movePerStorage, tx);
                totalMoved += moved;
            }
            return totalMoved;
        }
        return 0;
    }

}
