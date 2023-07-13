package mopsy.productions.nexo.util.slots;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.Predicate;

public class MixerSlot extends Slot {
    Predicate<Boolean> isIdlePredicate;
    public MixerSlot(Inventory inventory, int index, int x, int y, Predicate<Boolean> predicate) {
        super(inventory, index, x, y);
        this.isIdlePredicate =predicate;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return isIdlePredicate.test(false);
    }
    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        return super.canTakeItems(playerEntity)&&isIdlePredicate.test(false);
    }
    @Override
    public boolean canTakePartial(PlayerEntity player) {
        return super.canTakePartial(player)&&isIdlePredicate.test(false);
    }
}
