package mopsy.productions.nexo.ModBlocks.blocks.ores;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class DeepslateUraniumOreBlock extends ExperienceDroppingBlock implements IModID {
    @Override
    public String getID(){return "deepslate_uranium_ore";}
    public DeepslateUraniumOreBlock() {
        super(UniformIntProvider.create(0,10),
                Settings.create()
                        .strength(8.0F, 8.0F)
                        .sounds(BlockSoundGroup.DEEPSLATE)
                        .requiresTool()
                        .mapColor(MapColor.BLACK)

        );
    }
}
