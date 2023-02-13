package mopsy.productions.nucleartech.ModItems.components;

import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class PiezoelectricElementItem extends Item implements IModID {
    @Override
    public String getID() {
        return "piezoelectric_element";
    }
    public PiezoelectricElementItem() {
        super(new FabricItemSettings().group(CREATIVE_TAB));
    }


}
