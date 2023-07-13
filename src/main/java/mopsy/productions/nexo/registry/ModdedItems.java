package mopsy.productions.nexo.registry;

import mopsy.productions.nexo.ModItems.NTFuelRodItem;
import mopsy.productions.nexo.ModItems.NTItem;
import mopsy.productions.nexo.ModItems.NTRadiatingItem;
import mopsy.productions.nexo.ModItems.armor.hazmat.HazmatBoots;
import mopsy.productions.nexo.ModItems.armor.hazmat.HazmatChestplate;
import mopsy.productions.nexo.ModItems.armor.hazmat.HazmatHelmet;
import mopsy.productions.nexo.ModItems.armor.hazmat.HazmatLeggings;
import mopsy.productions.nexo.ModItems.armor.makeshiftHazmat.MakeshiftHazmatBoots;
import mopsy.productions.nexo.ModItems.armor.makeshiftHazmat.MakeshiftHazmatChestplate;
import mopsy.productions.nexo.ModItems.armor.makeshiftHazmat.MakeshiftHazmatHelmet;
import mopsy.productions.nexo.ModItems.armor.makeshiftHazmat.MakeshiftHazmatLeggings;
import mopsy.productions.nexo.ModItems.armor.protectiveHazmat.ProtectiveHazmatBoots;
import mopsy.productions.nexo.ModItems.armor.protectiveHazmat.ProtectiveHazmatChestplate;
import mopsy.productions.nexo.ModItems.armor.protectiveHazmat.ProtectiveHazmatHelmet;
import mopsy.productions.nexo.ModItems.armor.protectiveHazmat.ProtectiveHazmatLeggings;
import mopsy.productions.nexo.ModItems.components.CopperWireItem;
import mopsy.productions.nexo.ModItems.components.GoldWireItem;
import mopsy.productions.nexo.ModItems.tools.DebugItem;
import mopsy.productions.nexo.ModItems.tools.GeigerCounterItem;
import mopsy.productions.nexo.interfaces.IModID;
import mopsy.productions.nexo.util.ItemInfo;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

import static mopsy.productions.nexo.Main.LOGGER;
import static mopsy.productions.nexo.Main.modid;

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
            new ItemInfo("heat_resistant_glass"),
            new ItemInfo("cooling_pipe"),
            new ItemInfo("fan"),
            new ItemInfo("titanium_turbine_blade"),
            new ItemInfo("titanium_turbine"),
            new ItemInfo("wire_casing"),
            new ItemInfo("vanadium"),
            new ItemInfo("gypsum"),
            new ItemInfo("potassium_fluoride"),
            new ItemInfo("uranyl_fluoride"),
            new ItemInfo("triuranium_octoxide", 1),
            new ItemInfo("yellowcake", 3),
            new ItemInfo("uranium_tetrafluoride"),
            new ItemInfo("uranium_fuel_pellet"),
            new ItemInfo("sodium_metavanadate"),
            new ItemInfo("empty_geiger_tube"),
            new ItemInfo("filled_geiger_tube"),


            //plates
            new ItemInfo("gold_plate"),
            new ItemInfo("iron_plate"),
            new ItemInfo("copper_plate"),
            new ItemInfo("titanium_plate"),
            new ItemInfo("diamond_plate"),
            new ItemInfo("lead_plate"),
            new ItemInfo("steel_plate"),

            //raw's
            new ItemInfo("raw_uranium", 1),
            new ItemInfo("sulfur"),
            new ItemInfo("fluorite"),
            new ItemInfo("raw_titanium"),
            new ItemInfo("raw_lead"),
            new ItemInfo("salt"),
            new ItemInfo("raw_carobbiite"),
            new ItemInfo("raw_nickel"),

            //ingots
            new ItemInfo("uranium_ingot", 1),
            new ItemInfo("titanium_ingot"),
            new ItemInfo("graphite_ingot"),
            new ItemInfo("lead_ingot"),
            new ItemInfo("steel_ingot"),
            new ItemInfo("nickel_ingot"),

            //nuggets
            new ItemInfo("uranium_nugget", 0.2F),
            new ItemInfo("titanium_nugget"),
            new ItemInfo("lead_nugget"),
            new ItemInfo("steel_nugget"),

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
            new ItemInfo("glass_dust"),
            new ItemInfo("steel_dust"),
            new ItemInfo("vanadium_ore_dust"),

            //nuclear fuels
            new ItemInfo("empty_fuel_rod"),
            new ItemInfo("empty_dual_fuel_rod"),
            new ItemInfo("empty_quad_fuel_rod"),
            new ItemInfo("uranium_fuel_rod",      5,  1,    100000),
            new ItemInfo("uranium_dual_fuel_rod", 10, 2.5f, 100000),
            new ItemInfo("uranium_quad_fuel_rod", 15, 6f,   100000),

    };


    public static void regItems(){
        //tools
        regItem(new DebugItem());
        regItem(new GeigerCounterItem());

        //components
        regItem(new CopperWireItem());
        regItem(new GoldWireItem());

        //raw's

        //ingots

        //nuggets

        //dusts

        //armor
        regItem(new HazmatHelmet());
        regItem(new HazmatChestplate());
        regItem(new HazmatLeggings());
        regItem(new HazmatBoots());
        regItem(new MakeshiftHazmatHelmet());
        regItem(new MakeshiftHazmatChestplate());
        regItem(new MakeshiftHazmatLeggings());
        regItem(new MakeshiftHazmatBoots());
        regItem(new ProtectiveHazmatHelmet());
        regItem(new ProtectiveHazmatChestplate());
        regItem(new ProtectiveHazmatLeggings());
        regItem(new ProtectiveHazmatBoots());

        for(ItemInfo info : defaultItems){

            if(info.radiation < 0.0001F)
                    regItem(new NTItem(info.settings, info.ID));
            else{
                if(info.heat<0.0001F)
                    regItem(new NTRadiatingItem(info.settings, info.ID, info.radiation));
                else
                    regItem(new NTFuelRodItem(info.settings, info.ID, info.depletionTime, info.radiation, info.heat));
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