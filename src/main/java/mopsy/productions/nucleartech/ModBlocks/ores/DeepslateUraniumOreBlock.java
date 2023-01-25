package mopsy.productions.nucleartech.ModBlocks.ores;

import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class DeepslateUraniumOreBlock extends OreBlock implements IModID {
    @Override
    public String getID(){return "deepslate_uranium_ore";}
    public DeepslateUraniumOreBlock() {
        super(FabricBlockSettings
                        .of(Material.STONE, MapColor.BLACK)
                        .strength(8.0F, 8.0F)
                        .sounds(BlockSoundGroup.DEEPSLATE)
                        .requiresTool(),
                UniformIntProvider.create(0,10)
        );
    }
}
