package mopsy.productions.nucleartech.ModBlocks.blocks.multiblocks;

import mopsy.productions.nucleartech.interfaces.IMBBlock;
import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class ConduitBlock extends Block implements IModID, IMBBlock {
    @Override
    public String getID(){return "conduit";}
    public ConduitBlock() {
        super(FabricBlockSettings
                        .of(Material.STONE, MapColor.GRAY)
                        .strength(8.0F, 8.0F)
                        .sounds(BlockSoundGroup.STONE)
                        .requiresTool()
        );
    }
}
