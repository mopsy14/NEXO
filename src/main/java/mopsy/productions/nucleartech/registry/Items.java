package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModItems.DebugItem;
import mopsy.productions.nucleartech.ModItems.RawUraniumItem;
import mopsy.productions.nucleartech.ModItems.UraniumOreItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static mopsy.productions.nucleartech.Main.modid;

public class Items {

    public static void regItems(){
        //Registries.ITEM in 1.19.3
        Registry.register(Registry.ITEM, new Identifier(modid, "debug_item"), new DebugItem());
        Registry.register(Registry.ITEM, new Identifier(modid, "uranium_ore"), new UraniumOreItem());
        Registry.register(Registry.ITEM, new Identifier(modid, "raw_uranium"), new RawUraniumItem());


    }
}
