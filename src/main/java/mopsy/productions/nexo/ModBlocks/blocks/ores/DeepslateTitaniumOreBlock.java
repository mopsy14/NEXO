package mopsy.productions.nexo.ModBlocks.blocks.ores;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;

public class DeepslateTitaniumOreBlock extends Block implements IModID {
    @Override
    public String getID(){return "deepslate_titanium_ore";}
    public DeepslateTitaniumOreBlock() {
        super(Settings.create()
                .strength(6.0F, 6.0F)
                .sounds(BlockSoundGroup.DEEPSLATE)
                .requiresTool()
                .mapColor(MapColor.BLACK)
        );
    }
}
