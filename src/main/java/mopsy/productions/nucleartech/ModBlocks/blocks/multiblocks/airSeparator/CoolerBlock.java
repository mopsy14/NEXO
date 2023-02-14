package mopsy.productions.nucleartech.ModBlocks.blocks.multiblocks.airSeparator;

import mopsy.productions.nucleartech.interfaces.IMBBlock;
import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class CoolerBlock extends Block implements IModID, IMBBlock {
    @Override
    public String getID(){return "cooler";}
    public CoolerBlock() {
        super(FabricBlockSettings
                        .of(Material.STONE, MapColor.GRAY)
                        .strength(8.0F, 8.0F)
                        .sounds(BlockSoundGroup.STONE)
                        .requiresTool()
        );
    }
}
