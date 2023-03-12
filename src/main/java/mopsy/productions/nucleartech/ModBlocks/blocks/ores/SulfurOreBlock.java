package mopsy.productions.nucleartech.ModBlocks.blocks.ores;

import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.sound.BlockSoundGroup;

public class SulfurOreBlock extends OreBlock implements IModID {
    @Override
    public String getID(){return "sulfur_ore";}
    public SulfurOreBlock() {
        super(FabricBlockSettings
                        .of(Material.STONE, MapColor.GRAY)
                        .strength(3.0F, 3.0F)
                        .sounds(BlockSoundGroup.STONE)
                        .requiresTool()
        );
    }
}
