package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModItems.dusts.CoalDustItem;
import mopsy.productions.nucleartech.ModItems.dusts.DiamondDustItem;
import mopsy.productions.nucleartech.ModItems.ingots.UraniumIngotItem;
import mopsy.productions.nucleartech.ModItems.nuggets.UraniumNuggetItem;
import mopsy.productions.nucleartech.ModItems.raws.RawUraniumItem;
import mopsy.productions.nucleartech.ModItems.tools.DebugItem;
import mopsy.productions.nucleartech.ModItems.tools.GeigerCounterItem;
import mopsy.productions.nucleartech.ModItems.dusts.QuartzDustItem;
import mopsy.productions.nucleartech.interfaces.IModID;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

import static mopsy.productions.nucleartech.Main.LOGGER;
import static mopsy.productions.nucleartech.Main.modid;

public class ModdedItems {
    public static HashMap<String, Item> Items = new HashMap<>();


    public static void regItems(){
        //Registries.ITEM in 1.19.3
        regItem(new DebugItem());
        regItem(new RawUraniumItem());
        regItem(new UraniumIngotItem());
        regItem(new UraniumNuggetItem());
        regItem(new GeigerCounterItem());
        regItem(new CoalDustItem());
        regItem(new QuartzDustItem());
        regItem(new DiamondDustItem());

        
    }

    private static void regItem(Item item){
        if(item instanceof IModID){
            String name = ((IModID) item).getID();
            Items.put(name, Registry.register(Registry.ITEM, new Identifier(modid, name),item));
        }else
            LOGGER.error("Block doesn't implement IModID!");
    }
}
