package mopsy.productions.nexo.ModBlocks.blocks.multiblocks.smallReactor;

import mopsy.productions.nexo.ModBlocks.blocks.multiblocks.SmallReactorHatchesBlock;
import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.*;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static mopsy.productions.nexo.Main.modid;

public class SmallReactorBlock extends Block implements IModID {
    @Override
    public String getID(){return "small_reactor";}
    public static final EnumProperty<Direction> FACING;
    public SmallReactorBlock() {
        super(Settings.create()
                .strength(10.0F, 10.0F)
                .sounds(BlockSoundGroup.METAL)
                .requiresTool()
                .nonOpaque()
                .mapColor(MapColor.BLACK)
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modid,"small_reactor")))
        );
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
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(!world.isClient){
            Block controller = world.getBlockState(pos.down()).getBlock();
            if(controller instanceof SmallReactorHatchesBlock reactor) {
                return reactor.onUse(world.getBlockState(pos.down()), world, pos.down(), player, hit);
            }else{
                player.sendMessage(Text.of(BossBar.Color.YELLOW.getTextFormat()+"Multiblock incomplete!"),false);
            }
        }
        return ActionResult.SUCCESS;
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
    }
}
