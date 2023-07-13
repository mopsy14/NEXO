package mopsy.productions.nexo.REICompat.categories.crusher;

import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoContext;
import me.shedaniel.rei.api.common.transfer.info.simple.SimplePlayerInventoryMenuInfo;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import mopsy.productions.nexo.screen.crusher.CrusherScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class CrusherMenuInfo implements SimplePlayerInventoryMenuInfo<CrusherScreenHandler, CrushingDisplay> {
    CrushingDisplay display;
    public CrusherMenuInfo(Display display){
        this.display = (CrushingDisplay) display;
    }

    @Override
    public Iterable<SlotAccessor> getInputSlots(MenuInfoContext<CrusherScreenHandler, ?, CrushingDisplay> context) {
        List<SlotAccessor> res = new ArrayList<>();
        DefaultedList<Slot> slots= context.getMenu().slots;
        for (int i = 0; i < 2; i++) {
            res.add(SlotAccessor.fromSlot(slots.get(i)));
        }
        return res;
    }

    @Override
    public CrushingDisplay getDisplay() {
        return display;
    }
}
