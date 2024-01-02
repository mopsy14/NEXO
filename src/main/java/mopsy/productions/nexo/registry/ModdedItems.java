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
import mopsy.productions.nexo.ModItems.tools.*;
import mopsy.productions.nexo.ModItems.tools.steel.SteelToolMaterial;
import mopsy.productions.nexo.ModItems.tools.titanium.TitaniumToolMaterial;
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
            new ItemInfo("nuclear_reaction_chamber"),
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
            new ItemInfo("uranium_fuel_pellet",3),
            new ItemInfo("sodium_metavanadate"),
            new ItemInfo("empty_geiger_tube"),
            new ItemInfo("filled_geiger_tube"),
            new ItemInfo("compressor"),
            new ItemInfo("heating_coil"),
            new ItemInfo("heat_pump"),
            new ItemInfo("reaction_chamber"),
            new ItemInfo("gas_valve"),


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
            new ItemInfo("raw_nickel"),

            //ingots
            new ItemInfo("uranium_ingot", 1),
            new ItemInfo("titanium_ingot"),
            new ItemInfo("graphite_rod"),
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
        regItem(new PipeWrench());
        regItem(new PortableCraftingTableItem());
            //titanium
        regItem(new NSwordItem  ("titanium_sword",   TitaniumToolMaterial.INSTANCE,3,   -2.4f));
        regItem(new NAxeItem    ("titanium_axe",     TitaniumToolMaterial.INSTANCE,5.5f,-3.1f));
        regItem(new NPickaxeItem("titanium_pickaxe", TitaniumToolMaterial.INSTANCE,1,   -2.8f));
        regItem(new NShovelItem ("titanium_shovel",  TitaniumToolMaterial.INSTANCE,1.5f,-3.0f));
        regItem(new NHoeItem    ("titanium_hoe",     TitaniumToolMaterial.INSTANCE,-2,  -1.0f));
            //steel
        regItem(new NSwordItem  ("steel_sword",   SteelToolMaterial.INSTANCE,3,   -2.4f));
        regItem(new NAxeItem    ("steel_axe",     SteelToolMaterial.INSTANCE,5.0f,-3.0f));
        regItem(new NPickaxeItem("steel_pickaxe", SteelToolMaterial.INSTANCE,1,   -2.8f));
        regItem(new NShovelItem ("steel_shovel",  SteelToolMaterial.INSTANCE,1.5f,-3.0f));
        regItem(new NHoeItem    ("steel_hoe",     SteelToolMaterial.INSTANCE,-2,  -1.0f));

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
            LOGGER.error("Item doesn't implement IModID!");
    }
}
