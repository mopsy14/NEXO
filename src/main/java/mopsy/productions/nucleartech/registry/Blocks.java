package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModBlocks.ores.UraniumOreBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static mopsy.productions.nucleartech.Main.modid;

public class Blocks {
    public static void regBlocks(){
        Registry.register(Registry.BLOCK, new Identifier(modid, "uranium_ore"), new UraniumOreBlock());
    }
}
