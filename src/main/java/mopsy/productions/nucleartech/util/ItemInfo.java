package mopsy.productions.nucleartech.util;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class ItemInfo {
    public final FabricItemSettings settings;
    public final String ID;
    public final float radiation;
    public final float heat;
    public final int depletionTime;
    public ItemInfo(String ID){
        this(new FabricItemSettings().group(CREATIVE_TAB),ID,0);
    }
    public ItemInfo(String ID, float radiation){
        this(new FabricItemSettings().group(CREATIVE_TAB),ID,radiation);
    }
    public ItemInfo(String ID, float radiation, float heat, int depletionTime){
        this(new FabricItemSettings().group(CREATIVE_TAB),ID,radiation,heat,depletionTime);
    }
    public ItemInfo(FabricItemSettings settings, String ID, float radiation){
        this.settings = settings;
        this.ID = ID;
        this.radiation = radiation;
        this.heat = 0;
        this.depletionTime = 0;
    }
    public ItemInfo(FabricItemSettings settings, String ID, float radiation, float heat, int depletionTime){
        this.settings = settings;
        this.ID = ID;
        this.radiation = radiation;
        this.heat = heat;
        this.depletionTime = depletionTime;
    }
}
