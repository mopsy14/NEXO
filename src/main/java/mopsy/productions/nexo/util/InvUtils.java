package mopsy.productions.nexo.util;

import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.collection.DefaultedList;

public class InvUtils {
    public static SimpleInventory InvOf(DefaultedList<ItemStack> list){
        SimpleInventory inventory = new SimpleInventory(list.size());
        for (int i = 0; i < list.size(); i++) {
            inventory.setStack(i, list.get(i));
        }
        return inventory;
    }
    public static void writeInv(Inventory inventory, NbtCompound nbt){
        NbtList nbtList = new NbtList();

        for(int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack = inventory.getStack(i);
            if (!itemStack.isEmpty()) {
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putByte("Slot", (byte)i);
                itemStack.writeNbt(nbtCompound);
                nbtList.add(nbtCompound);
            }
        }

        if (!nbtList.isEmpty()) {
            nbt.put("Items", nbtList);
        }
    }
    public static void readInv(Inventory inventory, NbtCompound nbt){
        NbtList nbtList = nbt.getList("Items", 10);

        for(int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 255;
            if (j >= 0 && j < inventory.size()) {
                inventory.setStack(j, ItemStack.fromNbt(nbtCompound));
            }
        }
    }
}
