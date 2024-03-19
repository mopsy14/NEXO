package mopsy.productions.nexo.ModItems.blocks;

import mopsy.productions.nexo.ModBlocks.entities.energyStorage.BatteryMK1Entity;
import mopsy.productions.nexo.interfaces.IModID;
import mopsy.productions.nexo.util.DisplayUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyItem;

import java.util.List;

import static mopsy.productions.nexo.Main.CREATIVE_BLOCK_TAB;

@SuppressWarnings("UnstableApiUsage")
public class BatteryMK1Item extends BlockItem implements IModID, SimpleEnergyItem{
    private final long capacity = 10000;
    private final long maxInput = 10;
    private final long maxOutput= 20;

    @Override public String getID() {return "battery_mk1";}
    public BatteryMK1Item(Block block) {
        super(block, new FabricItemSettings().maxCount(1).group(CREATIVE_BLOCK_TAB));
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if(isIn(group)) {
            stacks.add(new ItemStack(this));
            ItemStack chargedStack = new ItemStack(this);
            ((SimpleEnergyItem)chargedStack.getItem()).setStoredEnergy(chargedStack, ((SimpleEnergyItem)chargedStack.getItem()).getEnergyCapacity(chargedStack));
            stacks.add(chargedStack);

        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        BatteryMK1Item item = (BatteryMK1Item)stack.getItem();
        boolean isShiftDown = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344);
        tooltip.add(Text.of(DisplayUtils.getEnergyBarText(
                        item.getStoredEnergy(stack),
                        item.getEnergyCapacity(stack),
                        isShiftDown
                )));
        if(!isShiftDown)
            tooltip.add(Text.of("Hold shift for advanced view"));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    protected boolean place(ItemPlacementContext context, BlockState state) {
        boolean res = super.place(context, state);
        BatteryMK1Entity entity = (BatteryMK1Entity) context.getWorld().getBlockEntity(context.getBlockPos());
        entity.energyStorage.amount = ((SimpleEnergyItem)context.getStack().getItem()).getStoredEnergy(context.getStack());
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
