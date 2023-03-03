package mopsy.productions.nucleartech.util;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class ItemInfo {
    public FabricItemSettings settings;
    public String ID;
    public float radiation;
    public ItemInfo(String ID){
        this(new FabricItemSettings().group(CREATIVE_TAB),ID,0);
    }
    public ItemInfo(FabricItemSettings settings, String ID, float radiation){
        this.settings = settings;
        this.ID = ID;
        this.radiation = radiation;
    }
}
