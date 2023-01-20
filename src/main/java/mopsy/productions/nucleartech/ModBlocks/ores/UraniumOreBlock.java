package mopsy.productions.nucleartech.ModBlocks.ores;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;


public class UraniumOreBlock extends Block {
    public UraniumOreBlock() {
        super(FabricBlockSettings
                .of(Material.METAL, MapColor.GRAY)
                .requiresTool()
                .strength(8.0F,8.0F)
                .sounds(BlockSoundGroup.STONE)
        );
    }
}
