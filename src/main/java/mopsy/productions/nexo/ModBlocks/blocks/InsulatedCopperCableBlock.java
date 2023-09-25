package mopsy.productions.nexo.ModBlocks.blocks;

import mopsy.productions.nexo.ModBlocks.entities.InsulatedCopperCableEntity;
import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

import static net.minecraft.state.property.Properties.*;

@SuppressWarnings("deprecation")
public class InsulatedCopperCableBlock extends BlockWithEntity implements IModID {
    @Override
    public String getID(){return "insulated_copper_cable";}

    public InsulatedCopperCableBlock() {
        super(FabricBlockSettings
                        .of(Material.METAL, MapColor.GRAY)
                        .strength(5.0F, 5.0F)
                        .sounds(BlockSoundGroup.COPPER)
                        .requiresTool()
        );
        this.setDefaultState(this.stateManager.getDefaultState().with(UP, false).with(DOWN, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState res = this.getDefaultState();
        if(isEnergyBlock(ctx.getWorld(),ctx.getBlockPos().up(),Direction.UP))
            res.with(UP,true);
        if(isEnergyBlock(ctx.getWorld(),ctx.getBlockPos().down(),Direction.DOWN))
            res.with(DOWN,true);
        if(isEnergyBlock(ctx.getWorld(),ctx.getBlockPos().north(),Direction.NORTH))
            res.with(NORTH,true);
        if(isEnergyBlock(ctx.getWorld(),ctx.getBlockPos().east(),Direction.EAST))
            res.with(EAST,true);
        if(isEnergyBlock(ctx.getWorld(),ctx.getBlockPos().south(),Direction.SOUTH))
            res.with(SOUTH,true);
        if(isEnergyBlock(ctx.getWorld(),ctx.getBlockPos().west(),Direction.WEST))
            res.with(WEST,true);
        return res;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState originalState, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        switch (direction){
            case UP -> {return originalState.with(UP, isEnergyBlock(world, neighborPos, Direction.UP));}
            case DOWN -> {return originalState.with(DOWN, isEnergyBlock(world, neighborPos, Direction.DOWN));}
            case NORTH -> {return originalState.with(NORTH, isEnergyBlock(world, neighborPos, Direction.NORTH));}
            case EAST -> {return originalState.with(EAST, isEnergyBlock(world, neighborPos, Direction.EAST));}
            case SOUTH -> {return originalState.with(SOUTH, isEnergyBlock(world, neighborPos, Direction.SOUTH));}
            case WEST -> {return originalState.with(WEST, isEnergyBlock(world, neighborPos, Direction.WEST));}
        }
        return originalState;
    }

    private boolean isEnergyBlock(World world, BlockPos pos, Direction direction){
        return EnergyStorage.SIDED.find(world, pos, direction) != null;
    }
    private boolean isEnergyBlock(WorldAccess world, BlockPos pos, Direction direction){
        return EnergyStorage.SIDED.find((World)world, pos, direction) != null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new InsulatedCopperCableEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(!state.isOf(newState.getBlock())){

        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if(world.isClient())return;

        setCorrectState(world, pos, state);
    }

    private void setCorrectState(World world, BlockPos pos, BlockState state){
        state.with(UP, isEnergyBlock(world, pos.up(), Direction.UP));
        state.with(DOWN, isEnergyBlock(world, pos.down(), Direction.DOWN));
        state.with(NORTH, isEnergyBlock(world, pos.north(), Direction.NORTH));
        state.with(EAST, isEnergyBlock(world, pos.east(), Direction.EAST));
        state.with(SOUTH, isEnergyBlock(world, pos.south(), Direction.SOUTH));
        state.with(WEST, isEnergyBlock(world, pos.west(), Direction.WEST));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, WEST, SOUTH);
    }

    private List<InsulatedCopperCableEntity> getConnectedCables(World world, BlockPos pos){
        List<InsulatedCopperCableEntity> res = new ArrayList<>(6);
        if(world.getBlockEntity(pos.up())instanceof InsulatedCopperCableEntity)
            res.add((InsulatedCopperCableEntity) world.getBlockEntity(pos.up()));
        if(world.getBlockEntity(pos.down())instanceof InsulatedCopperCableEntity)
            res.add((InsulatedCopperCableEntity) world.getBlockEntity(pos.down()));
        if(world.getBlockEntity(pos.north())instanceof InsulatedCopperCableEntity)
            res.add((InsulatedCopperCableEntity) world.getBlockEntity(pos.north()));
        if(world.getBlockEntity(pos.east())instanceof InsulatedCopperCableEntity)
            res.add((InsulatedCopperCableEntity) world.getBlockEntity(pos.east()));
        if(world.getBlockEntity(pos.south())instanceof InsulatedCopperCableEntity)
            res.add((InsulatedCopperCableEntity) world.getBlockEntity(pos.south()));
        if(world.getBlockEntity(pos.west())instanceof InsulatedCopperCableEntity)
            res.add((InsulatedCopperCableEntity) world.getBlockEntity(pos.west()));
        return res;
    }


    private Set<BlockPos> getSurroundingBlocks(World world, BlockPos pos, BiPredicate<World,BlockPos> condition){
        Set<BlockPos> blocks = new HashSet<>();
        if(condition.test(world,pos.up()))
            blocks.add(pos.up());
        if(condition.test(world,pos.down()))
            blocks.add(pos.down());
        if(condition.test(world,pos.north()))
            blocks.add(pos.north());
        if(condition.test(world,pos.east()))
            blocks.add(pos.east());
        if(condition.test(world,pos.south()))
            blocks.add(pos.south());
        if(condition.test(world,pos.west()))
            blocks.add(pos.west());

        return blocks;
    }
}
