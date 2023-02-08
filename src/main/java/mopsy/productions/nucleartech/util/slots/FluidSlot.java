package mopsy.productions.nucleartech.util.slots;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class FluidSlot extends Slot {
    private ScreenHandler screenHandler;
    private PlayerEntity player;
    public FluidSlot(Inventory inventory, int index, int x, int y, ScreenHandler screenHandler, PlayerEntity playerEntity) {
        super(inventory, index, x, y);
        this.screenHandler = screenHandler;
        this.player = playerEntity;
    }

    @Override
    public boolean canInsert(ItemStack itemStack) {
            if (super.canInsert(itemStack)) {
                if(ContainerItemContext.ofPlayerCursor(player, screenHandler).getItemVariant().matches(itemStack)) {
                    Storage<FluidVariant> storage = FluidStorage.ITEM.find(itemStack, ContainerItemContext.ofPlayerCursor(player, screenHandler));
                    if (storage != null) {
                        return storage.supportsExtraction() || storage.supportsInsertion();
                        //||FluidStorage.ITEM.find(itemStack, ContainerItemContext.ofPlayerSlot(player, PlayerInventoryStorage.getCursorStorage(screenHandler))).supportsInsertion()
                    }
                }
            }
        return false;
    }
}
