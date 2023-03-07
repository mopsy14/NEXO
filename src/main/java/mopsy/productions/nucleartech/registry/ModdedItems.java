package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModItems.NTItem;
import mopsy.productions.nucleartech.ModItems.NTRadiatingItem;
import mopsy.productions.nucleartech.ModItems.components.CopperWireItem;
import mopsy.productions.nucleartech.ModItems.tools.DebugItem;
import mopsy.productions.nucleartech.ModItems.tools.GeigerCounterItem;
import mopsy.productions.nucleartech.interfaces.IModID;
import mopsy.productions.nucleartech.util.ItemInfo;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

import static mopsy.productions.nucleartech.Main.LOGGER;
import static mopsy.productions.nucleartech.Main.modid;

public class ModdedItems {
    public static final HashMap<String, Item> Items = new HashMap<>();
    private static final ItemInfo[] defaultItems = {
            //tools

            //components
            new ItemInfo("test_tube"),
            new ItemInfo("heat_resistant_test_tube"),
            new ItemInfo("piezoelectric_element"),
            new ItemInfo("crushing_wheel"),
            new ItemInfo("electric_motor"),

            //plates
            new ItemInfo("gold_plate"),

            //raw's
            new ItemInfo("raw_uranium", 1),
            new ItemInfo("sulfur"),
            new ItemInfo("fluorite"),
            new ItemInfo("raw_titanium"),

            //ingots
            new ItemInfo("uranium_ingot", 1),
            new ItemInfo("titanium_ingot"),

            //nuggets
            new ItemInfo("uranium_nugget", 0.2F),
            new ItemInfo("titanium_nugget"),

            //dusts
            new ItemInfo("coal_dust"),
            new ItemInfo("quartz_dust"),
            new ItemInfo("diamond_dust"),
            new ItemInfo("titanium_dust"),
            new ItemInfo("uranium_dust", 2),

    };


    public static void regItems(){
        //tools
        regItem(new DebugItem());
        regItem(new GeigerCounterItem());

        //components
        regItem(new CopperWireItem());

        //raw's

        //ingots

        //nuggets

        //dusts


        for(ItemInfo info : defaultItems){
            if(info.radiation < 0.0001F)
                regItem(new NTItem(info.settings, info.ID));
            else
                regItem(new NTRadiatingItem(info.settings, info.ID, info.radiation));
        }
    }

    private static void regItem(Item item){
        if(item instanceof IModID){
            String name = ((IModID) item).getID();
            Items.put(name, Registry.register(Registry.ITEM, new Identifier(modid, name),item));
        }else
            LOGGER.error("Block doesn't implement IModID!");
    }
}
