package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModBlocks.IModID;
import mopsy.productions.nucleartech.ModBlocks.compressed.UraniumBlock;
import mopsy.productions.nucleartech.ModBlocks.ores.DeepslateUraniumOreBlock;
import mopsy.productions.nucleartech.ModBlocks.ores.UraniumOreBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

import static mopsy.productions.nucleartech.Main.*;

public class Blocks {
    public static HashMap<String, BlockItem> BlockItems = new HashMap<>();
    public static HashMap<String, Block> Blocks = new HashMap<>();

    public static void regBlocks(){
        //ores
        regBlock(new UraniumBlock());
        regBlock(new UraniumOreBlock());
        //compressed
        regBlock(new DeepslateUraniumOreBlock());
        //machines
    }

    private static void regBlock(Block block){
        if(block instanceof IModID) {
            String name = ((IModID)block).getID();
            Block b = Registry.register(Registry.BLOCK, new Identifier(modid, name), block);
            Blocks.put(name, b);
            BlockItem bi= Registry.register(Registry.ITEM, new Identifier(modid, name), new BlockItem(block, new FabricItemSettings().group(CREATIVE_BLOCK_TAB)));
            BlockItems.put(name, bi);
        }else
            LOGGER.error("Block doesn't implement IModID!");
    }
}
