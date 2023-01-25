package mopsy.productions.nucleartech.ModItems;

import mopsy.productions.nucleartech.interfaces.IItemRadiation;
import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class UraniumIngotItem extends Item implements IModID, IItemRadiation {

    @Override public String getID() {
        return "uranium_ingot";
    }
    @Override public float getRadiation() {return 1;}

    public UraniumIngotItem() {
        super(new FabricItemSettings().group(CREATIVE_TAB));

    }


}
