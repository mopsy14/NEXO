package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.Main;
import mopsy.productions.nucleartech.ModItems.DebugItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static mopsy.productions.nucleartech.Main.modid;

public class Items {

    public static void regItems(){
        //Registries.ITEM in 1.19.3
        Registry.register(Registry.ITEM, new Identifier(modid, "debug_item"), new DebugItem());


    }
}
