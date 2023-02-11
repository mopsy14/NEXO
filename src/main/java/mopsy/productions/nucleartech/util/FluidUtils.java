package mopsy.productions.nucleartech.util;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;


public class FluidUtils{

    public static Storage<FluidVariant> getItemFluidStorage(DefaultedList<ItemStack> inv, int inputIndex, int outputIndex){
        InventoryStorage inventoryStorage = InventoryStorage.of(InvUtils.InvOf(inv),null);
        SingleSlotStorage<ItemVariant> inputSlot = inventoryStorage.getSlot(inputIndex);
        ContainerItemContext containerItemContext = new ContainerItemContext()  {
            @Override
            public SingleSlotStorage<ItemVariant> getMainSlot() {
                return inputSlot;
            }
            @Override
            public long insertOverflow(ItemVariant itemVariant, long maxAmount, TransactionContext transactionContext) {
                return tryInsert(inv, outputIndex, maxAmount, itemVariant,transactionContext);
            }
            @Override
            public long insert(ItemVariant itemVariant, long maxAmount, TransactionContext transactionContext) {
                return insertOverflow(itemVariant, maxAmount, transactionContext);
            }

            @Override
            public List<SingleSlotStorage<ItemVariant>> getAdditionalSlots() {
                return List.of();
            }
        };
        return containerItemContext.find(FluidStorage.ITEM);
    }
    private static long tryInsert(DefaultedList<ItemStack> inv, int slotIndex, long maxAmount, ItemVariant itemVariant, TransactionContext transactionContext){
        StoragePreconditions.notBlankNotNegative(itemVariant, maxAmount);
        if(ItemVariant.of(inv.get(slotIndex)).equals(itemVariant)||inv.get(slotIndex).isEmpty()){
            int insertAmount = (int) Math.min(maxAmount,getSpace(inv,slotIndex));
            if(insertAmount>0){
                try (Transaction nested = transactionContext.openNested()) {
                    nested.addCloseCallback((tr, res) -> {
                        System.out.println(res);
                    });
                    nested.addOuterCloseCallback((res)-> System.out.println("outer"+res));
                    if (inv.get(slotIndex).isEmpty()) {
                        inv.set(slotIndex, new ItemStack(itemVariant.getItem(), insertAmount));
                    } else {

                        inv.set(slotIndex, new ItemStack(inv.get(slotIndex).getItem(), inv.get(slotIndex).getCount() + insertAmount));
                    }
                    return insertAmount;
                }
            }
        }
        return 0;
    }
    private static int getSpace(DefaultedList<ItemStack> inv, int index){
        return inv.get(index).getMaxCount()-inv.get(index).getCount();
    }
    public static boolean containsItemStackFluidStorage(ItemStack itemStack){
        return ContainerItemContext.withConstant(itemStack).find(FluidStorage.ITEM)!=null;
    }
}
