package mopsy.productions.nexo.ModBlocks.blocks.ores;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.modid;

public class DeepslateVanadiumOreBlock extends Block implements IModID {
    @Override
    public String getID(){return "deepslate_vanadium_ore";}
    public DeepslateVanadiumOreBlock() {
        super(Settings.create()
                .strength(7.0F, 7.0F)
                .sounds(BlockSoundGroup.STONE)
                .requiresTool()
                .mapColor(MapColor.BLACK)
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modid,"deepslate_vanadium_ore")))
        );
    }
}
