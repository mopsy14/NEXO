package mopsy.productions.nucleartech.ModBlocks.compressed;

import mopsy.productions.nucleartech.ModBlocks.IModID;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class UraniumBlock extends Block implements IModID {
    @Override
    public String getID(){return "uranium_block";}

    public UraniumBlock() {
        super(FabricBlockSettings
                        .of(Material.STONE, MapColor.GRAY)
                        .strength(8.0F, 8.0F)
                        .sounds(BlockSoundGroup.STONE)
                        .requiresTool()
        );
    }
}
