package mopsy.productions.nucleartech.REICompat.categories.press;

import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoContext;
import me.shedaniel.rei.api.common.transfer.info.simple.SimplePlayerInventoryMenuInfo;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import mopsy.productions.nucleartech.screen.press.PressScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class PressMenuInfo implements SimplePlayerInventoryMenuInfo<PressScreenHandler, PressDisplay> {
    PressDisplay display;
    public PressMenuInfo(Display display){
        this.display = (PressDisplay) display;
    }

    @Override
    public Iterable<SlotAccessor> getInputSlots(MenuInfoContext<PressScreenHandler, ?, PressDisplay> context) {
        List<SlotAccessor> res = new ArrayList<>();
        DefaultedList<Slot> slots= context.getMenu().slots;
        for (int i = 0; i < 2; i++) {
            res.add(SlotAccessor.fromSlot(slots.get(i)));
        }
        return res;
    }

    @Override
    public PressDisplay getDisplay() {
        return display;
    }
}
