package mopsy.productions.nucleartech.util;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class ItemInfo {
    public final FabricItemSettings settings;
    public final String ID;
    public final float radiation;
    public final float heat;
    public ItemInfo(String ID){
        this(new FabricItemSettings().group(CREATIVE_TAB),ID,0);
    }
    public ItemInfo(String ID, float radiation){
        this(new FabricItemSettings().group(CREATIVE_TAB),ID,radiation);
    }
    public ItemInfo(String ID, float radiation, float heat){
        this(new FabricItemSettings().group(CREATIVE_TAB),ID,radiation,heat);
    }
    public ItemInfo(FabricItemSettings settings, String ID, float radiation){
        this.settings = settings;
        this.ID = ID;
        this.radiation = radiation;
        this.heat = 0;
    }
    public ItemInfo(FabricItemSettings settings, String ID, float radiation, float heat){
        this.settings = settings;
        this.ID = ID;
        this.radiation = radiation;
        this.heat = heat;
    }
}
