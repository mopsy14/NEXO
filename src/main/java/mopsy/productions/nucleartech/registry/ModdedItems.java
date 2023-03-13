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
            new ItemInfo("control_rod"),
            new ItemInfo("reaction_chamber"),
            new ItemInfo("rod_holder"),
            new ItemInfo("reactor_boiler"),

            //plates
            new ItemInfo("gold_plate"),
            new ItemInfo("iron_plate"),
            new ItemInfo("copper_plate"),
            new ItemInfo("titanium_plate"),
            new ItemInfo("diamond_plate"),
            new ItemInfo("lead_plate"),


            //raw's
            new ItemInfo("raw_uranium", 1),
            new ItemInfo("sulfur"),
            new ItemInfo("fluorite"),
            new ItemInfo("raw_titanium"),
            new ItemInfo("raw_lead"),

            //ingots
            new ItemInfo("uranium_ingot", 1),
            new ItemInfo("titanium_ingot"),
            new ItemInfo("graphite_ingot"),
            new ItemInfo("lead_ingot"),

            //nuggets
            new ItemInfo("uranium_nugget", 0.2F),
            new ItemInfo("titanium_nugget"),
            new ItemInfo("lead_nugget"),

            //dusts
            new ItemInfo("coal_dust"),
            new ItemInfo("quartz_dust"),
            new ItemInfo("diamond_dust"),
            new ItemInfo("titanium_dust"),
            new ItemInfo("uranium_dust", 2),
            new ItemInfo("gold_dust"),
            new ItemInfo("iron_dust"),
            new ItemInfo("copper_dust"),
            new ItemInfo("lead_dust"),
            new ItemInfo("graphite_dust"),
            new ItemInfo("iron_dust"),

            //nuclear fuels
            new ItemInfo("empty_fuel_rod"),
            new ItemInfo("empty_dual_fuel_rod"),
            new ItemInfo("empty_quad_fuel_rod"),
            new ItemInfo("uranium_fuel_rod", 0, 1),
            new ItemInfo("uranium_double_fuel_rod", 0, 2.5f),
            new ItemInfo("uranium_quad_fuel_rod", 0, 6f),

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
            else{
                if(info.heat<0.0001F)
                    regItem(new NTRadiatingItem(info.settings, info.ID, info.radiation));
                else
                    regItem(new NTRadiatingItem(info.settings, info.ID, info.radiation, info.heat));
            }
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
