package mopsy.productions.nucleartech.ModItems.components;

import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class WireCasingItem extends Item implements IModID {
    @Override
    public String getID() {
        return "wire_casing";
    }
    public WireCasingItem() {
        super(new FabricItemSettings().group(CREATIVE_TAB));
    }
}
