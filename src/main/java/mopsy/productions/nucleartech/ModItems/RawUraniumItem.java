package mopsy.productions.nucleartech.ModItems;

import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class RawUraniumItem extends Item implements IModID {
    @Override
    public String getID() {
        return "raw_uranium";
    }
    public RawUraniumItem() {
        super(new FabricItemSettings().group(CREATIVE_TAB));
    }
}
