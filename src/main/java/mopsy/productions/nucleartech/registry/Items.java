package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.Main;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Items {
    public static final Item DEBUG_ITEM = new Item(new Item.Settings().group(ItemGroup.SEARCH));
    //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(DEBUG_ITEM));

    public static void regItems(){
        //Registries.ITEM in 1.19.3
        Registry.register(Registry.ITEM, new Identifier(Main.modid, "debug_item"), DEBUG_ITEM);


    }
}
