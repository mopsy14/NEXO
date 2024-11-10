package mopsy.productions.nexo.ModBlocks.blocks.ores;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;

public class CarobbiiteOreBlock extends Block implements IModID {
    @Override
    public String getID(){return "carobbiite_ore";}
    public CarobbiiteOreBlock() {
        super(Settings.create()
                .strength(4.0F, 4.0F)
                .sounds(BlockSoundGroup.STONE)
                .requiresTool()
                .mapColor(MapColor.GRAY)
        );
    }
}
