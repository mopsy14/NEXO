package mopsy.productions.nexo.ModBlocks.blocks.ores;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;

public class SaltOreBlock extends Block implements IModID {
    @Override
    public String getID(){return "salt_ore";}
    public SaltOreBlock() {
        super(Settings.create()
                .strength(3.0F, 3.0F)
                .sounds(BlockSoundGroup.STONE)
                .requiresTool()
                .mapColor(MapColor.LIGHT_GRAY)
        );
    }
}
