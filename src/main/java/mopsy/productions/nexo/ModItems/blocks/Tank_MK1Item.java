package mopsy.productions.nexo.ModItems.blocks;

import mopsy.productions.nexo.ModBlocks.entities.machines.TankEntity_MK1;
import mopsy.productions.nexo.interfaces.IItemFluidData;
import mopsy.productions.nexo.interfaces.IModID;
import mopsy.productions.nexo.util.FluidDataUtils;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static mopsy.productions.nexo.Main.CREATIVE_FLUIDS_TAB_KEY;
import static mopsy.productions.nexo.Main.modid;


public class Tank_MK1Item extends BlockItem implements IModID, IItemFluidData {
    public static List<FluidVariant> fluidGroupVariants = new ArrayList<>();
    @Override public String getID() {return "tank_mk1";}
    public Tank_MK1Item(Block block) {
        super(block, new Settings().maxCount(1)
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modid,"tank_mk1"))));
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_FLUIDS_TAB_KEY).register(entries -> {
            entries.add(this);
            for (int i = 0; i < 8; i++) {
                entries.add(ItemStack.EMPTY);
            }
            entries.add(FluidDataUtils.setFluidAmount(FluidDataUtils.setFluidType(getDefaultStack(),FluidVariant.of(Fluids.WATER)),8*81000));
            entries.add(FluidDataUtils.setFluidAmount(FluidDataUtils.setFluidType(getDefaultStack(),FluidVariant.of(Fluids.LAVA)),8*81000));
            for(FluidVariant variant:fluidGroupVariants){
                entries.add(FluidDataUtils.setFluidAmount(FluidDataUtils.setFluidType(getDefaultStack(),variant),8*81000));
            }
        });
    }

    public static final long MAX_CAPACITY = 8* FluidConstants.BUCKET;
    @Override
    public long getMaxCapacity() {
        return MAX_CAPACITY;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        long amount = FluidDataUtils.getFluidAmount(stack);
        FluidVariant variant = FluidDataUtils.getFluidType(stack);
        if (amount == 0) {
            tooltip.add(Text.of(Formatting.AQUA + "Tank is empty"));
        } else {
            tooltip.add(Text.of(Formatting.AQUA + getFluidName(variant).getString() + " " + amount / 81 + "mB/" + Tank_MK1Item.MAX_CAPACITY / 81 + "mB"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
    private static Text getFluidName(FluidVariant variant){
        return Text.translatable(variant.getFluid().getDefaultState().getBlockState().getBlock().getTranslationKey());
    }

    @Override
    protected boolean place(ItemPlacementContext context, BlockState state) {
        boolean res = super.place(context, state);
        if(FluidDataUtils.getFluidAmount(context.getStack()) > 0) {
            TankEntity_MK1 tank = (TankEntity_MK1)context.getWorld().getBlockEntity(context.getBlockPos());
            tank.fluidStorage.amount = FluidDataUtils.getFluidAmount(context.getStack());
            tank.fluidStorage.variant = FluidDataUtils.getFluidType(context.getStack());
        }
        return res;
    }
}
