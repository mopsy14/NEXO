package mopsy.productions.nucleartech.ModBlocks.ores;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;


public class UraniumOreBlock extends OreBlock {
    public UraniumOreBlock() {
        super(FabricBlockSettings
                        .of(Material.STONE, MapColor.GRAY)
                        .strength(8.0F, 8.0F)
                        .sounds(BlockSoundGroup.STONE)
                        .luminance(4)
                        .requiresTool(),
                UniformIntProvider.create(0,10)
        );
    }
}
