package mopsy.productions.nucleartech.ModBlocks.blocks.ores;

import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.sound.BlockSoundGroup;

public class DeepslateLeadOreBlock extends OreBlock implements IModID {
    @Override
    public String getID(){return "deepslate_lead_ore";}
    public DeepslateLeadOreBlock() {
        super(FabricBlockSettings
                        .of(Material.STONE, MapColor.BLACK)
                        .strength(5.0F, 5.0F)
                        .sounds(BlockSoundGroup.DEEPSLATE)
                        .requiresTool()
        );
    }
}
