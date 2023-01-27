package mopsy.productions.nucleartech.ModBlocks.blocks.machines;

import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class CrusherBlock extends Block implements IModID {
    @Override
    public String getID(){return "Crusher";}

    public CrusherBlock() {
        super(FabricBlockSettings
                .of(Material.METAL, MapColor.BLACK)
                .strength(8.0F, 8.0F)
                .sounds(BlockSoundGroup.METAL)
                .requiresTool()
        );
    }

}
