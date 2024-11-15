package mopsy.productions.nexo.util;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;

import static mopsy.productions.nexo.Main.modid;



public class FluidUtils{

    public static Storage<FluidVariant> getItemFluidStorage(Inventory inv, int inputIndex, int outputIndex){
        InventoryStorage inventoryStorage = InventoryStorage.of(inv,null);
        SingleSlotStorage<ItemVariant> inputSlot = inventoryStorage.getSlot(inputIndex);
        SingleSlotStorage<ItemVariant> outputSlot = inventoryStorage.getSlot(outputIndex);
        ContainerItemContext containerItemContext = new ContainerItemContext()  {
            @Override
            public SingleSlotStorage<ItemVariant> getMainSlot() {
                return inputSlot;
            }
            @Override
            public long insertOverflow(ItemVariant itemVariant, long maxAmount, TransactionContext transactionContext) {
                return outputSlot.insert(itemVariant, maxAmount, transactionContext);
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

    public static boolean containsItemStackFluidStorage(ItemStack itemStack){
        return (ContainerItemContext.withConstant(itemStack).find(FluidStorage.ITEM)!=null)||isTank(itemStack.getItem());
    }
    public static boolean isTank(Item item){
        String[] tankNames = {"tank_mk1","tank_mk2","tank_mk3","tank_mk4"};
        for(String name: tankNames){
            if(Registries.ITEM.getId(item).equals(Identifier.of(modid,name)))
                return true;
        }
        return false;
    }

    public static long dropletsTomB(long droplets){return droplets/81;}
    public static long mBtoDroplets(long mB){return mB*81;}
}
