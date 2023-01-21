package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModItems.DebugItem;
import mopsy.productions.nucleartech.ModItems.RawUraniumItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static mopsy.productions.nucleartech.Main.modid;

public class Items {

    //Block Items
    public static final BlockItem URANIUM_ORE = new BlockItem(Blocks.URANIUM_ORE, new FabricItemSettings().group(ItemGroup.SEARCH));

    public static void regItems(){
        //Registries.ITEM in 1.19.3
        Registry.register(Registry.ITEM, new Identifier(modid, "debug_item"), new DebugItem());
        Registry.register(Registry.ITEM, new Identifier(modid, "raw_uranium"), new RawUraniumItem());
        Registry.register(Registry.ITEM, new Identifier(modid, "uranium_ore"), URANIUM_ORE);

    }
}
