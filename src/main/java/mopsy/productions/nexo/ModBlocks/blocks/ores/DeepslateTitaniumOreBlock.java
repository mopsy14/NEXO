package mopsy.productions.nexo.ModBlocks.blocks.ores;

import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.sound.BlockSoundGroup;

public class DeepslateTitaniumOreBlock extends OreBlock implements IModID {
    @Override
    public String getID(){return "deepslate_titanium_ore";}
    public DeepslateTitaniumOreBlock() {
        super(FabricBlockSettings
                        .of(Material.STONE, MapColor.BLACK)
                        .strength(6.0F, 6.0F)
                        .sounds(BlockSoundGroup.DEEPSLATE)
                        .requiresTool()
        );
    }
}
