package mopsy.productions.nucleartech.ModItems.blocks;

import mopsy.productions.nucleartech.ModBlocks.entities.machines.TankEntity_MK1;
import mopsy.productions.nucleartech.interfaces.IItemFluidData;
import mopsy.productions.nucleartech.interfaces.IModID;
import mopsy.productions.nucleartech.util.FluidDataUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static mopsy.productions.nucleartech.Main.CREATIVE_BLOCK_TAB;

@SuppressWarnings("UnstableApiUsage")
public class Tank_MK1Item extends BlockItem implements IModID, IItemFluidData {
    @Override public String getID() {return "Tank_MK1";}
    public Tank_MK1Item(Block block) {
        super(block, new FabricItemSettings().maxCount(1).group(CREATIVE_BLOCK_TAB));
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if(isIn(group)){
            ItemStack res = new ItemStack(this);
            NbtCompound nbt = new NbtCompound();
            FluidDataUtils.creNbtIfNeeded(nbt);
            res.setNbt(nbt);
            stacks.add(res);
        }
    }

    public static final long MAX_CAPACITY = 8* FluidConstants.BUCKET;
    @Override
    public long getMaxCapacity() {
        return MAX_CAPACITY;
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        FluidDataUtils.creNbtIfNeeded(stack.getOrCreateNbt());
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        FluidDataUtils.creNbtIfNeeded(stack.getOrCreateNbt());
        return super.onStackClicked(stack, slot, clickType, player);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(stack.hasNbt()){
            long amount = FluidDataUtils.getFluidAmount(stack.getNbt());
            FluidVariant variant = FluidDataUtils.getFluidType(stack.getNbt());
            if (amount == 0) {
                tooltip.add(Text.of(Formatting.AQUA + "Tank is empty"));
            } else {
                tooltip.add(Text.of(Formatting.AQUA + getFluidName(variant).getString() + " " + amount / 81 + "mB/" + Tank_MK1Item.MAX_CAPACITY / 81 + "mB"));
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
    private static Text getFluidName(FluidVariant variant){
        return Text.translatable(variant.getFluid().getDefaultState().getBlockState().getBlock().getTranslationKey());
    }

    @Override
    protected boolean place(ItemPlacementContext context, BlockState state) {
        boolean res = super.place(context, state);
        if(FluidDataUtils.getFluidAmount(context.getStack().getNbt()) > 0) {
            TankEntity_MK1 tank = (TankEntity_MK1)context.getWorld().getBlockEntity(context.getBlockPos());
            tank.fluidStorage.amount = FluidDataUtils.getFluidAmount(context.getStack().getNbt());
            tank.fluidStorage.variant = FluidDataUtils.getFluidType(context.getStack().getNbt());
        }
        return res;
    }
}
