package mopsy.productions.nucleartech.ModItems;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class RawUraniumItem extends Item {
    public RawUraniumItem() {
        super(new FabricItemSettings().group(ItemGroup.SEARCH));
    }
}
