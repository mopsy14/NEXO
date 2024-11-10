package mopsy.productions.nexo.ModBlocks.blocks.ores;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;

public class DeepslateNickelOreBlock extends Block implements IModID {
    @Override
    public String getID(){return "deepslate_nickel_ore";}
    public DeepslateNickelOreBlock() {
        super(Settings.create()
                .strength(5.0F, 5.0F)
                .sounds(BlockSoundGroup.DEEPSLATE)
                .requiresTool()
                .mapColor(MapColor.BLACK)
        );
    }
}
