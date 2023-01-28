package mopsy.productions.nucleartech.ModBlocks.blocks.machines;

import mopsy.productions.nucleartech.ModBlocks.entities.machines.CrusherEntity;
import mopsy.productions.nucleartech.interfaces.IModID;
import mopsy.productions.nucleartech.registry.ModdedBlockEntities;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrusherBlock extends BlockWithEntity implements IModID, BlockEntityProvider {
    @Override
    public String getID(){return "crusher";}

    public CrusherBlock() {
        super(FabricBlockSettings
                .of(Material.METAL, MapColor.BLACK)
                .strength(8.0F, 8.0F)
                .sounds(BlockSoundGroup.METAL)
                .requiresTool()
        );
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock()!= newState.getBlock()){
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof CrusherEntity){
                ItemScatterer.spawn(world, pos, (CrusherEntity)blockEntity);
                world.updateComparators(pos, this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient){
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if(screenHandlerFactory != null){
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrusherEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModdedBlockEntities.CRUSHER, CrusherEntity::tick);
    }
}
