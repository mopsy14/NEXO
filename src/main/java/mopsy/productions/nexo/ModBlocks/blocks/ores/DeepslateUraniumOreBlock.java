package mopsy.productions.nexo.ModBlocks.blocks.ores;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import static mopsy.productions.nexo.Main.modid;

public class DeepslateUraniumOreBlock extends ExperienceDroppingBlock implements IModID {
    @Override
    public String getID(){return "deepslate_uranium_ore";}
    public DeepslateUraniumOreBlock() {
        super(UniformIntProvider.create(0, 10),
                Settings.create()
                        .strength(8.0F, 8.0F)
                        .sounds(BlockSoundGroup.DEEPSLATE)
                        .requiresTool()
                        .mapColor(MapColor.BLACK)
                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modid, "deepslate_uranium_ore")))

        );
    }
}
