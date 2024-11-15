package mopsy.productions.nexo.ModBlocks.blocks.ores;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.modid;

public class SulfurOreBlock extends Block implements IModID {
    @Override
    public String getID(){return "sulfur_ore";}
    public SulfurOreBlock() {
        super(Settings.create()
                .strength(3.0F, 3.0F)
                .sounds(BlockSoundGroup.STONE)
                .requiresTool()
                .mapColor(MapColor.GRAY)
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modid,"sulfur_ore")))
        );
    }
}
