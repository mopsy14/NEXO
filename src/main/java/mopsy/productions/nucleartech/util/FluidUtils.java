package mopsy.productions.nucleartech.util;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.List;


public class FluidUtils{

    public static Storage<FluidVariant> getItemFluidStorage(Inventory inv, int inputIndex, int outputIndex){
        InventoryStorage inventoryStorage = InventoryStorage.of(inv,null);
        ContainerItemContext containerItemContext = new ContainerItemContext() {
            @Override
            public SingleSlotStorage<ItemVariant> getMainSlot() {
                return inventoryStorage.getSlot(inputIndex);
            }
            @Override
            public long insertOverflow(ItemVariant itemVariant, long maxAmount, TransactionContext transactionContext) {
                return inventoryStorage.getSlot(outputIndex).insert(itemVariant, maxAmount, transactionContext);
            }
            @Override
            public long insert(ItemVariant itemVariant, long maxAmount, TransactionContext transaction) {
                return insertOverflow(itemVariant, maxAmount, transaction);
            }

            @Override
            public List<SingleSlotStorage<ItemVariant>> getAdditionalSlots() {
                return List.of(inventoryStorage.getSlot(outputIndex));
            }
        };
        return containerItemContext.find(FluidStorage.ITEM);
    }
    public static boolean containsItemStackFluidStorage(ItemStack itemStack){
        return ContainerItemContext.withConstant(itemStack).find(FluidStorage.ITEM)!=null;
    }
}
