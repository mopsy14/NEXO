package mopsy.productions.nexo.ModBlocks.blocks.multiblocks.smallReactor;

import mopsy.productions.nexo.ModBlocks.blocks.multiblocks.SmallReactorHatchesBlock;
import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.block.*;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ControlRodsBlock extends Block implements IModID{
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    private static final VoxelShape SHAPE_INACTIVE = Block.createCuboidShape(0,0,0,16,4,16);
    private static final VoxelShape SHAPE_ACTIVE = Block.createCuboidShape(0,0,0,16,16,16);
    @Override
    public String getID(){return "control_rods";}

    public ControlRodsBlock() {
        super(Settings.create()
                .strength(10.0F, 10.0F)
                .sounds(BlockSoundGroup.METAL)
                .requiresTool()
                .nonOpaque()
                .mapColor(MapColor.BLACK)
        );
        this.setDefaultState(this.stateManager.getDefaultState().with(ACTIVE, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
            return state.get(ACTIVE)? SHAPE_ACTIVE: SHAPE_INACTIVE;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(!world.isClient){
            Block controller = world.getBlockState(pos.down(2)).getBlock();
            if(controller instanceof SmallReactorHatchesBlock reactor) {
                return reactor.onUse(world.getBlockState(pos.down(2)), world, pos.down(2), player, hit);
            }else{
                player.sendMessage(Text.of(BossBar.Color.YELLOW.getTextFormat()+"Multiblock incomplete!"),false);
            }
        }
        return ActionResult.SUCCESS;
    }
}
