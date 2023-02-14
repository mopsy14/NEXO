package mopsy.productions.nucleartech.ModBlocks.blocks.machines;

import mopsy.productions.nucleartech.ModBlocks.entities.machines.AirSeparatorEntity;
import mopsy.productions.nucleartech.interfaces.IModID;
import mopsy.productions.nucleartech.interfaces.IMultiBlockController;
import mopsy.productions.nucleartech.multiblock.AirSeparatorMultiBlock;
import mopsy.productions.nucleartech.registry.ModdedBlockEntities;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AirSeparatorBlock extends BlockWithEntity implements IModID, BlockEntityProvider, IMultiBlockController{
    public static final DirectionProperty FACING;
    @Override
    public String getID(){return "air_separator";}

    public AirSeparatorBlock() {
        super(FabricBlockSettings
                .of(Material.METAL, MapColor.BLACK)
                .strength(4.0F, 8.0F)
                .sounds(BlockSoundGroup.METAL)
                .requiresTool()
        );
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient){
            player.sendMessage(Text.of(String.valueOf(AirSeparatorMultiBlock.INSTANCE.hasCompleteMB(world.getBlockEntity(pos)))));
            /*NamedScreenHandlerFactory screenHandlerFactory = (AirSeparatorEntity)world.getBlockEntity(pos);
            if(screenHandlerFactory != null){
                player.openHandledScreen(screenHandlerFactory);
            }
            */
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AirSeparatorEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModdedBlockEntities.AIR_SEPARATOR, AirSeparatorEntity::tick);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!player.isCreative()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AirSeparatorEntity) {
                ItemScatterer.spawn(world, pos, (AirSeparatorEntity) blockEntity);
                world.updateComparators(pos, this);
            }
        }
        super.onBreak(world, pos, state, player);
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
    }

}
