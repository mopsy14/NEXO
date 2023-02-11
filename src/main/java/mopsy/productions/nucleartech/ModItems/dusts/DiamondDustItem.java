package mopsy.productions.nucleartech.ModItems.dusts;

import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class DiamondDustItem extends Item implements IModID {
    @Override
    public String getID() {
        return "diamond_dust";
    }
    public DiamondDustItem() {
        super(new FabricItemSettings().group(CREATIVE_TAB));
    }


}

