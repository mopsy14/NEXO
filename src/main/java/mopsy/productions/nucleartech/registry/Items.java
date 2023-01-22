package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModItems.DebugItem;
import mopsy.productions.nucleartech.ModItems.RawUraniumItem;
import mopsy.productions.nucleartech.ModItems.UraniumIngotItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;
import static mopsy.productions.nucleartech.Main.modid;

public class Items {

    //Block Items
    public static final BlockItem URANIUM_ORE = new BlockItem(Blocks.URANIUM_ORE, new FabricItemSettings().group(CREATIVE_TAB));

    //Items
    public static Item DEBUG_ITEM;
    public static Item RAW_URANIUM;
    public static Item URANIUM_INGOT;


    public static void regItems(){
        //Registries.ITEM in 1.19.3
        DEBUG_ITEM = Registry.register(Registry.ITEM, new Identifier(modid, "debug_item"), new DebugItem());
        RAW_URANIUM = Registry.register(Registry.ITEM, new Identifier(modid, "raw_uranium"), new RawUraniumItem());
        URANIUM_INGOT = Registry.register(Registry.ITEM, new Identifier(modid, "uranium_ingot"), new UraniumIngotItem());

        Registry.register(Registry.ITEM, new Identifier(modid, "uranium_ore"), URANIUM_ORE);


    }
}
