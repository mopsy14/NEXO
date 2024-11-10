package mopsy.productions.nexo.ModBlocks.blocks.machines;

import mopsy.productions.nexo.ModBlocks.entities.machines.TankEntity_MK1;
import mopsy.productions.nexo.interfaces.IModID;
import mopsy.productions.nexo.registry.ModdedBlockEntities;
import mopsy.productions.nexo.registry.ModdedBlocks;
import mopsy.productions.nexo.util.FluidDataUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public class Tank_MK1 extends BlockWithEntity implements IModID, BlockEntityProvider{
    public static final EnumProperty<Direction> FACING;
    private static final VoxelShape SHAPE = Block.createCuboidShape(3,0,3,13,16,13);
    @Override
    public String getID(){return "tank_mk1";}

    public Tank_MK1() {
        super(Settings.create()
                .strength(5.0F, 5.0F)
                .sounds(BlockSoundGroup.METAL)
                .requiresTool()
                .nonOpaque()
                .mapColor(MapColor.GRAY)
        );
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
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
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(!world.isClient){
            NamedScreenHandlerFactory screenHandlerFactory = (TankEntity_MK1)world.getBlockEntity(pos);
            if(screenHandlerFactory != null){
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TankEntity_MK1(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModdedBlockEntities.TANK_MK1, TankEntity_MK1::tick);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(player.isCreative()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof TankEntity_MK1) {
                ItemScatterer.spawn(world, pos, ((TankEntity_MK1) blockEntity).inventory);
                world.updateComparators(pos, this);
            }
        }else{
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof TankEntity_MK1) {
                ItemStack tankItem = new ItemStack(ModdedBlocks.BlockItems.get("tank_mk1"));
                FluidDataUtils.creNbtIfNeeded(tankItem.getOrCreateNbt());
                FluidDataUtils.setFluidType(tankItem.getNbt(), ((TankEntity_MK1) blockEntity).fluidStorage.variant);
                FluidDataUtils.setFluidAmount(tankItem.getNbt(), ((TankEntity_MK1) blockEntity).fluidStorage.amount);
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), tankItem);
                ItemScatterer.spawn(world, pos, ((TankEntity_MK1) blockEntity).inventory);
                world.updateComparators(pos, this);
            }
        }
        return super.onBreak(world, pos, state, player);
    }


    static {
        FACING = HorizontalFacingBlock.FACING;
    }

}
