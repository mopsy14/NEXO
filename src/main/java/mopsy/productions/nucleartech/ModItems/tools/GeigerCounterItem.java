package mopsy.productions.nucleartech.ModItems.tools;

import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class GeigerCounterItem extends Item implements IModID  {
    public GeigerCounterItem() {
        super(new FabricItemSettings().group(CREATIVE_TAB).maxCount(1));
    }

    @Override
    public String getID() {
        return "geiger_counter";
    }
}
