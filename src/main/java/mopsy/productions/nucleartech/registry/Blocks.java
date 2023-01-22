package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModBlocks.ores.DeepslateUraniumOreBlock;
import mopsy.productions.nucleartech.ModBlocks.ores.UraniumOreBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static mopsy.productions.nucleartech.Main.modid;

public class Blocks {

    public static final Block URANIUM_ORE = new UraniumOreBlock();
    public static final Block DEEPSLATE_URANIUM_ORE = new DeepslateUraniumOreBlock();

    public static void regBlocks(){
        regBlock("uranium_ore", URANIUM_ORE);
        regBlock("deepslate_uranium_ore", DEEPSLATE_URANIUM_ORE);
    }

    private static void regBlock(String name, Block block){
        Registry.register(Registry.BLOCK, new Identifier(modid, name), block);
    }
}
