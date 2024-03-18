package mopsy.productions.nexo.ModItems.blocks;

import mopsy.productions.nexo.ModBlocks.entities.energyStorage.BatteryMK1Entity;
import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleEnergyItem;

import static mopsy.productions.nexo.Main.CREATIVE_BLOCK_TAB;

@SuppressWarnings("UnstableApiUsage")
public class BatteryMK1Item extends BlockItem implements IModID, SimpleEnergyItem{
    private final long capacity = 10000;
    private final long maxInput = 10;
    private final long maxOutput= 10;

    @Override public String getID() {return "battery_mk1";}
    public BatteryMK1Item(Block block) {
        super(block, new FabricItemSettings().maxCount(1).group(CREATIVE_BLOCK_TAB));
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if(isIn(group)) {
            this.getBlock().appendStacks(group, stacks);
        }
    }

    @Override
    protected boolean place(ItemPlacementContext context, BlockState state) {
        boolean res = super.place(context, state);
        BatteryMK1Entity entity = (BatteryMK1Entity) context.getWorld().getBlockEntity(context.getBlockPos());
        entity.energyStorage.amount = EnergyStorage.ITEM.find(context.getStack(), ContainerItemContext.ofPlayerHand(context.getPlayer(), context.getHand())).getAmount();
        return res;
    }

    @Override
    public long getEnergyCapacity(ItemStack stack) {
        return capacity;
    }

    @Override
    public long getEnergyMaxInput(ItemStack stack) {
        return maxInput;
    }

    @Override
    public long getEnergyMaxOutput(ItemStack stack) {
        return maxOutput;
    }
}
