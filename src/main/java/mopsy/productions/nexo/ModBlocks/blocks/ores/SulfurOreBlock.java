package mopsy.productions.nexo.ModBlocks.blocks.ores;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;

public class SulfurOreBlock extends Block implements IModID {
    @Override
    public String getID(){return "sulfur_ore";}
    public SulfurOreBlock() {
        super(Settings.create()
                .strength(3.0F, 3.0F)
                .sounds(BlockSoundGroup.STONE)
                .requiresTool()
                .mapColor(MapColor.GRAY)
        );
    }
}
