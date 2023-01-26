package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModItems.*;
import mopsy.productions.nucleartech.interfaces.IModID;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

import static mopsy.productions.nucleartech.Main.LOGGER;
import static mopsy.productions.nucleartech.Main.modid;

public class Items {
    public static HashMap<String, Item> Items = new HashMap<>();


    public static void regItems(){
        //Registries.ITEM in 1.19.3
        regItem(new DebugItem());
        regItem(new RawUraniumItem());
        regItem(new UraniumIngotItem());
        regItem(new UraniumNuggetItem());
        regItem(new GeigerCounterItem());

        
    }

    private static void regItem(Item item){
        if(item instanceof IModID){
            String name = ((IModID) item).getID();
            Items.put(name, Registry.register(Registry.ITEM, new Identifier(modid, name),item));
        }else
            LOGGER.error("Block doesn't implement IModID!");
    }
}
