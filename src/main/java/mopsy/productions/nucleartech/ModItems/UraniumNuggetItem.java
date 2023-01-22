package mopsy.productions.nucleartech.ModItems;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class UraniumNuggetItem extends Item {
    public UraniumNuggetItem() {
        super(new FabricItemSettings().group(CREATIVE_TAB));
    }
}
