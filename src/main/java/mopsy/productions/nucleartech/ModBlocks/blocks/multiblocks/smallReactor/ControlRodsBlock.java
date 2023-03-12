package mopsy.productions.nucleartech.ModBlocks.blocks.multiblocks.smallReactor;

import mopsy.productions.nucleartech.ModBlocks.blocks.multiblocks.SmallReactorHatchesBlock;
import mopsy.productions.nucleartech.ModBlocks.entities.machines.SmallReactorEntity;
import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ControlRodsBlock extends Block implements IModID{
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    @Override
    public String getID(){return "control_rods";}

    public ControlRodsBlock() {
        super(FabricBlockSettings
                .of(Material.METAL, MapColor.BLACK)
                .strength(10.0F, 10.0F)
                .sounds(BlockSoundGroup.METAL)
                .requiresTool()
                .nonOpaque()
        );
        this.setDefaultState(this.stateManager.getDefaultState().with(ACTIVE, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient){
            Block controller = world.getBlockState(pos.down(2)).getBlock();
            if(controller instanceof SmallReactorHatchesBlock reactor) {
                SmallReactorEntity entity = ((SmallReactorEntity)world.getBlockEntity(pos.down(2)));
                entity.active = entity.active == 0?1:0;
                System.out.println(entity.active);
                return reactor.onUse(world.getBlockState(pos.down(2)), world, pos.down(2), player, hand, hit);
            }else{
                player.sendMessage(Text.of(BossBar.Color.YELLOW.getTextFormat()+"Multiblock incomplete!"));
            }
        }
        return ActionResult.SUCCESS;
    }
}
