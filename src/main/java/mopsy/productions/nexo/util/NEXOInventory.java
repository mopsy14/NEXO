package mopsy.productions.nexo.util;

import net.minecraft.inventory.SimpleInventory;

public class NEXOInventory extends SimpleInventory {
    public NEXOInventory(int size){
        super(size);
    }
    public SimpleInventory withSize(int size){
        SimpleInventory res = new SimpleInventory(size);
        for (int i = 0; i < size; i++) {
            res.setStack(i,getStack(i));
        }
        return res;
    }
}
