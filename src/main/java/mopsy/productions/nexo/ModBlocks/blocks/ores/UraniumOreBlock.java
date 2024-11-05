package mopsy.productions.nexo.ModBlocks.blocks.ores;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class UraniumOreBlock extends Block implements IModID {
    @Override
    public String getID(){return "uranium_ore";}
    public UraniumOreBlock() {
        super(Settings.create()
                        .strength(8.0F, 8.0F)
                        .sounds(BlockSoundGroup.STONE)
                        .requiresTool(),
                UniformIntProvider.create(0,10)
        );
    }
}
