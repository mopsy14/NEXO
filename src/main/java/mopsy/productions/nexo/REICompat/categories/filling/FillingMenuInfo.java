package mopsy.productions.nexo.REICompat.categories.filling;

import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoContext;
import me.shedaniel.rei.api.common.transfer.info.simple.SimplePlayerInventoryMenuInfo;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import mopsy.productions.nexo.screen.tank.TankScreenHandler_MK1;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class FillingMenuInfo implements SimplePlayerInventoryMenuInfo<TankScreenHandler_MK1, FillingDisplay> {
    FillingDisplay display;
    public FillingMenuInfo(Display display){
        this.display = (FillingDisplay) display;
    }

    @Override
    public Iterable<SlotAccessor> getInputSlots(MenuInfoContext<TankScreenHandler_MK1, ?, FillingDisplay> context) {
        List<SlotAccessor> res = new ArrayList<>();
        DefaultedList<Slot> slots= context.getMenu().slots;
        res.add(SlotAccessor.fromSlot(slots.get(0)));
        return res;
    }

    @Override
    public FillingDisplay getDisplay() {
        return display;
    }
}
