package mopsy.productions.nexo.util;

import net.minecraft.item.Item;

public class ItemInfo {
    public final Item.Settings settings;
    public final String ID;
    public final float radiation;
    public final float heat;
    public final int depletionTime;
    public ItemInfo(String ID){
        this(new Item.Settings(),ID,0);
    }
    public ItemInfo(String ID, float radiation){
        this(new Item.Settings(),ID,radiation);
    }
    public ItemInfo(String ID, float radiation, float heat, int depletionTime){
        this(new Item.Settings(),ID,radiation,heat,depletionTime);
    }
    public ItemInfo(Item.Settings settings, String ID, float radiation){
        this.settings = settings;
        this.ID = ID;
        this.radiation = radiation;
        this.heat = 0;
        this.depletionTime = 0;
    }
    public ItemInfo(Item.Settings settings, String ID, float radiation, float heat, int depletionTime){
        this.settings = settings;
        this.ID = ID;
        this.radiation = radiation;
        this.heat = heat;
        this.depletionTime = depletionTime;
    }
}
