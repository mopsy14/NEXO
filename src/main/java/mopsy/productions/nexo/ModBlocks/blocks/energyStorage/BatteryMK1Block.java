package mopsy.productions.nexo.ModBlocks.blocks.energyStorage;

import mopsy.productions.nexo.ModBlocks.entities.energyStorage.BatteryMK1Entity;
import mopsy.productions.nexo.interfaces.IModID;
import mopsy.productions.nexo.registry.ModdedBlockEntities;
import mopsy.productions.nexo.registry.ModdedBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
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
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyItem;

public class BatteryMK1Block extends BlockWithEntity implements IModID, BlockEntityProvider {
    public static final DirectionProperty FACING;
    @Override
    public String getID(){return "battery_mk1";}

    public BatteryMK1Block() {
        super(FabricBlockSettings
                .of(Material.METAL, MapColor.BLACK)
                .strength(4.0F, 8.0F)
                .sounds(BlockSoundGroup.METAL)
                .requiresTool()
        );
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
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
            NamedScreenHandlerFactory screenHandlerFactory = (BatteryMK1Entity)world.getBlockEntity(pos);
            if(screenHandlerFactory != null){
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BatteryMK1Entity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModdedBlockEntities.BATTERY_MK1, BatteryMK1Entity::tick);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(player.isCreative()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BatteryMK1Entity) {
                ItemScatterer.spawn(world, pos, (BatteryMK1Entity) blockEntity);
                world.updateComparators(pos, this);
            }
        }else{
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BatteryMK1Entity entity) {
                ItemScatterer.spawn(world, pos, (BatteryMK1Entity) blockEntity);
                ItemStack batteryStack = new ItemStack(ModdedBlocks.BlockItems.get("battery_mk1"));
                ((SimpleEnergyItem) batteryStack.getItem()).setStoredEnergy(batteryStack, entity.energyStorage.amount);
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), batteryStack);
                world.updateComparators(pos, this);
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        ItemStack res = super.getPickStack(world, pos, state);
        ((SimpleEnergyItem) res.getItem()).setStoredEnergy(res, ((BatteryMK1Entity)world.getBlockEntity(pos)).energyStorage.amount);
        return res;
    }

    static {
        FACING = Properties.FACING;
    }

}
