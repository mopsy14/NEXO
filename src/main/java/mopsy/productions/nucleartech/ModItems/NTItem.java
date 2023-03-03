package mopsy.productions.nucleartech.ModItems;

import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class NTItem extends Item implements IModID {

    String ID;

    public NTItem(Settings settings, String ID) {
        super(settings);
        this.ID = ID;
    }
    public NTItem(String ID){
        super(new FabricItemSettings().group(CREATIVE_TAB));
        this.ID = ID;
    }

    @Override
    public String getID() {
        return ID;
    }
}
