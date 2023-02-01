package mopsy.productions.nucleartech.ModItems.nuggets;

import mopsy.productions.nucleartech.interfaces.IItemRadiation;
import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class UraniumNuggetItem extends Item implements IModID, IItemRadiation {
    @Override public String getID() {
        return "uranium_nugget";
    }
    @Override public float getRadiation() {return 0.2F;}
    public UraniumNuggetItem() {
        super(new FabricItemSettings().group(CREATIVE_TAB));
    }
}
