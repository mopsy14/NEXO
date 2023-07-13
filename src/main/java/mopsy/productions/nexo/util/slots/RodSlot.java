package mopsy.productions.nexo.util.slots;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.Predicate;

public class RodSlot extends Slot {
    Predicate<Boolean> isActivePredicate;
    public RodSlot(Inventory inventory, int index, int x, int y, Predicate<Boolean> predicate) {
        super(inventory, index, x, y);
        this.isActivePredicate =predicate;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return isActivePredicate.test(false);
    }
    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        return super.canTakeItems(playerEntity)&&isActivePredicate.test(false);
    }
    @Override
    public boolean canTakePartial(PlayerEntity player) {
        return super.canTakePartial(player)&&isActivePredicate.test(false);
    }
}
