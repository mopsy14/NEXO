package mopsy.productions.nexo.ModBlocks.blocks.ores;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.modid;

public class DeepslateTitaniumOreBlock extends Block implements IModID {
    @Override
    public String getID(){return "deepslate_titanium_ore";}
    public DeepslateTitaniumOreBlock() {
        super(Settings.create()
                .strength(6.0F, 6.0F)
                .sounds(BlockSoundGroup.DEEPSLATE)
                .requiresTool()
                .mapColor(MapColor.BLACK)
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modid,"deepslate_titanium_ore")))
        );
    }
}
