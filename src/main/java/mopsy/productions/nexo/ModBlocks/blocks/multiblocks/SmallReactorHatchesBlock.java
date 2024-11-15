package mopsy.productions.nexo.ModBlocks.blocks.multiblocks;

import com.mojang.serialization.MapCodec;
import mopsy.productions.nexo.ModBlocks.blocks.multiblocks.smallReactor.ControlRodsBlock;
import mopsy.productions.nexo.ModBlocks.blocks.multiblocks.smallReactor.SmallReactorBlock;
import mopsy.productions.nexo.ModBlocks.entities.machines.SmallReactorEntity;
import mopsy.productions.nexo.interfaces.IModID;
import mopsy.productions.nexo.registry.ModdedBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static mopsy.productions.nexo.Main.modid;

public class SmallReactorHatchesBlock extends BlockWithEntity implements IModID, BlockEntityProvider{
    @Override
    public String getID(){return "small_reactor_hatches";}
    public static final EnumProperty<Direction> FACING;
    public SmallReactorHatchesBlock() {
        super(Settings.create()
                .strength(10.0F, 10.0F)
                .sounds(BlockSoundGroup.METAL)
                .requiresTool()
                .nonOpaque()
                .mapColor(MapColor.BLACK)
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modid,"small_reactor_hatches")))
        );
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }
    public SmallReactorHatchesBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
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
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(SmallReactorHatchesBlock::new);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(!world.isClient){
            if(world.getBlockState(pos.up()).getBlock() instanceof SmallReactorBlock && world.getBlockState(pos.up(2)).getBlock() instanceof ControlRodsBlock) {
                NamedScreenHandlerFactory screenHandlerFactory = (SmallReactorEntity) world.getBlockEntity(pos);
                if (screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
                }
            }else{
                player.sendMessage(Text.of(BossBar.Color.YELLOW.getTextFormat()+"Multiblock incomplete!"),false);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SmallReactorEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModdedBlockEntities.SMALL_REACTOR, SmallReactorEntity::tick);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SmallReactorEntity) {
            ItemScatterer.spawn(world, pos, (SmallReactorEntity) blockEntity);
            world.updateComparators(pos, this);
        }
        return super.onBreak(world, pos, state, player);
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
    }
}
