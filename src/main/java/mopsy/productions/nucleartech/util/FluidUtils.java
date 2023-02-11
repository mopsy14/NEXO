package mopsy.productions.nucleartech.util;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.List;


public class FluidUtils{

    public static Storage<FluidVariant> getItemFluidStorage(Inventory inv, int inputIndex, int outputIndex){
        InventoryStorage inventoryStorage = InventoryStorage.of(inv,null);
        SingleSlotStorage<ItemVariant> inputSlot = inventoryStorage.getSlot(inputIndex);
        SingleSlotStorage<ItemVariant> outputSlot = inventoryStorage.getSlot(outputIndex);
        ContainerItemContext containerItemContext = new ContainerItemContext() {
            @Override
            public SingleSlotStorage<ItemVariant> getMainSlot() {
                return inputSlot;
            }
            @Override
            public long insertOverflow(ItemVariant itemVariant, long maxAmount, TransactionContext transactionContext) {
                return tryInsert(inv, outputIndex);
                return outputSlot.insert(itemVariant, maxAmount, transactionContext);
            }
            @Override
            public long insert(ItemVariant itemVariant, long maxAmount, TransactionContext transaction) {
                return insertOverflow(itemVariant, maxAmount, transaction);
            }

            @Override
            public List<SingleSlotStorage<ItemVariant>> getAdditionalSlots() {
                return List.of();
            }
        };
        return containerItemContext.find(FluidStorage.ITEM);
    }
    private static long tryInsert(Inventory inv, int slotIndex, long maxAmount, ItemVariant itemVariant, TransactionContext transactionContext){
        StoragePreconditions.notBlankNotNegative(itemVariant, maxAmount);
        if(ItemVariant.of(inv.getStack(slotIndex)).equals(itemVariant)||inv.getStack(slotIndex).isEmpty()){
            int insertAmount = (int) Math.min(maxAmount,getSpace(inv,slotIndex));
            if(insertAmount>0){

            }
        }
    }
    private static int getSpace(Inventory inv, int index){
        return inv.getStack(index).getMaxCount()-inv.getStack(index).getCount();
    }
    public static boolean containsItemStackFluidStorage(ItemStack itemStack){
        return ContainerItemContext.withConstant(itemStack).find(FluidStorage.ITEM)!=null;
    }
}
