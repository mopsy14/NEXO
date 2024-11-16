package mopsy.productions.nexo.util;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;

public class InvUtils {
    public static void writeInv(RegistryWrapper.WrapperLookup registries, Inventory inventory, NbtCompound nbt){
        NbtList nbtList = new NbtList();

        for(int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack = inventory.getStack(i);
            if (!itemStack.isEmpty()) {
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putByte("Slot", (byte)i);
                nbtList.add(itemStack.toNbt(registries,nbtCompound));
            }
        }

        if (!nbtList.isEmpty()) {
            nbt.put("Items", nbtList);
        }
    }
    public static void readInv(RegistryWrapper.WrapperLookup registries, Inventory inventory, NbtCompound nbt){
        NbtList nbtList = nbt.getList("Items", 10);

        for(int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 255;
            if (j >= 0 && j < inventory.size()) {
                inventory.setStack(j, ItemStack.fromNbt(registries, nbtCompound).orElse(ItemStack.EMPTY));
            }
        }
    }
}
