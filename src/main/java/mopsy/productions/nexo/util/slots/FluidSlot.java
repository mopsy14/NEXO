package mopsy.productions.nexo.util.slots;

import mopsy.productions.nexo.registry.ModdedItems;
import mopsy.productions.nexo.screen.tank.TankScreenHandler_MK1;
import mopsy.productions.nexo.util.FluidUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class FluidSlot extends Slot {
    private final ScreenHandler screenHandler;
    private final PlayerEntity player;
    public FluidSlot(Inventory inventory, int index, int x, int y, ScreenHandler screenHandler, PlayerEntity playerEntity) {
        super(inventory, index, x, y);
        this.screenHandler = screenHandler;
        this.player = playerEntity;
    }

    @Override
    public boolean canInsert(ItemStack itemStack) {
        return (FluidUtils.containsItemStackFluidStorage(itemStack)
                || (ModdedItems.Items.get("empty_geiger_tube").equals(itemStack.getItem())&&screenHandler instanceof TankScreenHandler_MK1))
                && super.canInsert(itemStack);
    }
}
