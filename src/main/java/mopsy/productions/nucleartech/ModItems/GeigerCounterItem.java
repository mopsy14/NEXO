package mopsy.productions.nucleartech.ModItems;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import mopsy.productions.nucleartech.ModBlocks.IModID;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class GeigerCounterItem extends Item implements IModID  {
    public GeigerCounterItem() {
        super(new FabricItemSettings().group(CREATIVE_TAB));
    }

    @Override
    public String getID() {
        return "geiger_counter";
    }
}
