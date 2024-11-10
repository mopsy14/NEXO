package mopsy.productions.nexo.ModBlocks.blocks.compressed;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;

public class UraniumBlock extends Block implements IModID {
    @Override
    public String getID(){return "uranium_block";}

    public UraniumBlock() {
        super(Settings.create()
                .strength(8.0F, 8.0F)
                .sounds(BlockSoundGroup.STONE)
                .requiresTool()
                .mapColor(MapColor.GRAY)
        );
    }
}
