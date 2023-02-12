package mopsy.productions.nucleartech.ModItems.blocks;

import mopsy.productions.nucleartech.interfaces.IItemFluidData;
import mopsy.productions.nucleartech.interfaces.IModID;
import mopsy.productions.nucleartech.util.FluidDataUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

import static mopsy.productions.nucleartech.Main.CREATIVE_BLOCK_TAB;

public class Tank_MK1Item extends BlockItem implements IModID, IItemFluidData {
    @Override public String getID() {return "Tank_MK1";}
    public Tank_MK1Item(Block block) {
        super(block, new FabricItemSettings().group(CREATIVE_BLOCK_TAB));
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
}
