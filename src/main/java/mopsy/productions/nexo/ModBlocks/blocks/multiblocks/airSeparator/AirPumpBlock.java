package mopsy.productions.nexo.ModBlocks.blocks.multiblocks.airSeparator;

import mopsy.productions.nexo.interfaces.IMBBlock;
import mopsy.productions.nexo.interfaces.IModID;
import mopsy.productions.nexo.multiblock.MBUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static mopsy.productions.nexo.Main.modid;

public class AirPumpBlock extends Block implements IModID, IMBBlock {
    @Override
    public String getID(){return "air_pump";}
    public AirPumpBlock() {
        super(Settings.create()
                .strength(8.0F, 8.0F)
                .sounds(BlockSoundGroup.STONE)
                .requiresTool()
                .mapColor(MapColor.GRAY)
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modid,"air_pump")))
        );
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
        if(state.getBlock() != newState.getBlock()){
            MBUtils.updateMultiBlock(pos, world);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        MBUtils.updateMultiBlock(pos, world);
    }
}
