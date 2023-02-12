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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

import static mopsy.productions.nucleartech.Main.modid;


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

    /*private static long tryInsert(Inventory inv, int slotIndex, long maxAmount, ItemVariant itemVariant, TransactionContext transactionContext){
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
    }*/
    public static boolean containsItemStackFluidStorage(ItemStack itemStack){
        return (ContainerItemContext.withConstant(itemStack).find(FluidStorage.ITEM)!=null)||isTank(itemStack.getItem());
    }
    public static boolean isTank(Item item){
        String[] tankNames = {"tank_mk1","tank_mk2","tank_mk3","tank_mk4"};
        for(String name: tankNames){
            if(Registry.ITEM.getId(item).equals(new Identifier(modid,name)))
                return true;
        }
        return false;
    }
}
