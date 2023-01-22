package mopsy.productions.nucleartech.ModItems;

import mopsy.productions.nucleartech.ModBlocks.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class UraniumIngotItem extends Item implements IModID {
    @Override
    public String getID() {
        return "uranium_ingot";
    }
    //TODO add radiation mechanics

    public UraniumIngotItem() {
        super(new FabricItemSettings().group(CREATIVE_TAB));

    }
}
