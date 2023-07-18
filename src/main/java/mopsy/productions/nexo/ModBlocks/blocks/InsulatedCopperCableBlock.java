package mopsy.productions.nexo.ModBlocks.blocks;

import mopsy.productions.nexo.ModBlocks.entities.InsulatedCopperCableEntity;
import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class InsulatedCopperCableBlock extends BlockWithEntity implements IModID {
    @Override
    public String getID(){return "insulated_copper_cable";}

    public InsulatedCopperCableBlock() {
        super(FabricBlockSettings
                        .of(Material.STONE, MapColor.GRAY)
                        .strength(5.0F, 5.0F)
                        .sounds(BlockSoundGroup.STONE)
                        .requiresTool()
        );
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new InsulatedCopperCableEntity(pos, state);
    }

}
