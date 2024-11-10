package mopsy.productions.nexo.ModBlocks.blocks.ores;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;

public class TitaniumOreBlock extends Block implements IModID {
    @Override
    public String getID(){return "titanium_ore";}
    public TitaniumOreBlock() {
        super(Settings.create()
                .strength(5.0F, 5.0F)
                .sounds(BlockSoundGroup.STONE)
                .requiresTool()
                .mapColor(MapColor.GRAY)
        );
    }
}
