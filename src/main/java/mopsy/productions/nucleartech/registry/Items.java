package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModItems.DebugItem;
import mopsy.productions.nucleartech.ModItems.RawUraniumItem;
import mopsy.productions.nucleartech.ModItems.UraniumIngotItem;
import mopsy.productions.nucleartech.ModItems.UraniumNuggetItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

import static mopsy.productions.nucleartech.Main.modid;

public class Items {
    public static HashMap<String, Item> Items= new HashMap<>();


    public static void regItems(){
        //Registries.ITEM in 1.19.3
        Items.put("debug_item", Registry.register(Registry.ITEM, new Identifier(modid, "debug_item"), new DebugItem()));
        Items.put("raw_uranium", Registry.register(Registry.ITEM, new Identifier(modid, "raw_uranium"), new RawUraniumItem()));
        Items.put("uranium_ingot", Registry.register(Registry.ITEM, new Identifier(modid, "uranium_ingot"), new UraniumIngotItem()));
        Items.put("uranium_nugget", Registry.register(Registry.ITEM, new Identifier(modid, "uranium_nugget"), new UraniumNuggetItem()));
    }
}
