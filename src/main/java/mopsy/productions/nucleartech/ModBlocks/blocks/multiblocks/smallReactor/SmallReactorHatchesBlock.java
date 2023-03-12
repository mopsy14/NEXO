package mopsy.productions.nucleartech.ModBlocks.blocks.multiblocks.smallReactor;

import mopsy.productions.nucleartech.ModBlocks.entities.machines.CentrifugeEntity;
import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SmallReactorHatchesBlock extends Block implements IModID{
    @Override
    public String getID(){return "small_reactor_hatches";}

    public SmallReactorHatchesBlock() {
        super(FabricBlockSettings
                .of(Material.METAL, MapColor.BLACK)
                .strength(10.0F, 10.0F)
                .sounds(BlockSoundGroup.METAL)
                .requiresTool()
                .nonOpaque()
        );
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient){
            NamedScreenHandlerFactory screenHandlerFactory = (CentrifugeEntity)world.getBlockEntity(pos);
            if(screenHandlerFactory != null){
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }
}
