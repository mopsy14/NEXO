package mopsy.productions.nexo.ModItems.blocks;

import mopsy.productions.nexo.ModBlocks.entities.machines.TankEntity_MK1;
import mopsy.productions.nexo.interfaces.IItemFluidData;
import mopsy.productions.nexo.interfaces.IModID;
import mopsy.productions.nexo.util.FluidDataUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static mopsy.productions.nexo.Main.CREATIVE_FLUIDS_TAB;

@SuppressWarnings("UnstableApiUsage")
public class Tank_MK1Item extends BlockItem implements IModID, IItemFluidData {
    public static List<FluidVariant> fluidGroupVariants = new ArrayList<>();
    @Override public String getID() {return "tank_mk1";}
    public Tank_MK1Item(Block block) {
        super(block, new FabricItemSettings().maxCount(1).group(CREATIVE_FLUIDS_TAB));
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack res = super.getDefaultStack();
        FluidDataUtils.creNbtIfNeeded(res.getOrCreateNbt());
        return res;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if(isIn(group)) {
            stacks.add(getDefaultStack());
            for (int i = 0; i < 8; i++) {
                stacks.add(new ItemStack(Items.AIR));
            }
            for (FluidVariant variant:fluidGroupVariants) {
                ItemStack stack = super.getDefaultStack();
                FluidDataUtils.setFluidType(stack.getOrCreateNbt(), variant);
                FluidDataUtils.setFluidAmount(stack.getNbt(), 8000*81);
                stacks.add(stack);
            }
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
        FluidDataUtils.creNbtIfNeeded(context.getStack().getOrCreateNbt());
        if(FluidDataUtils.getFluidAmount(context.getStack().getNbt()) > 0) {
            TankEntity_MK1 tank = (TankEntity_MK1)context.getWorld().getBlockEntity(context.getBlockPos());
            tank.fluidStorage.amount = FluidDataUtils.getFluidAmount(context.getStack().getNbt());
            tank.fluidStorage.variant = FluidDataUtils.getFluidType(context.getStack().getNbt());
        }
        return res;
    }
}
