package mopsy.productions.nucleartech.util;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class InvUtils {
    public static SimpleInventory InvOf(DefaultedList<ItemStack> list){
        SimpleInventory inventory = new SimpleInventory(list.size());
        for (int i = 0; i < list.size(); i++) {
            inventory.setStack(i, list.get(i));
        }
        return inventory;
    }
}
