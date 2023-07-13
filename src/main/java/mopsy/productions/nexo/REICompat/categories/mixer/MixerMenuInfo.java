package mopsy.productions.nexo.REICompat.categories.mixer;

import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoContext;
import me.shedaniel.rei.api.common.transfer.info.simple.SimplePlayerInventoryMenuInfo;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import mopsy.productions.nexo.screen.mixer.MixerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class MixerMenuInfo implements SimplePlayerInventoryMenuInfo<MixerScreenHandler, MixerDisplay> {
    MixerDisplay display;
    public MixerMenuInfo(Display display){
        this.display = (MixerDisplay) display;
    }

    @Override
    public Iterable<SlotAccessor> getInputSlots(MenuInfoContext<MixerScreenHandler, ?, MixerDisplay> context) {
        List<SlotAccessor> res = new ArrayList<>();
        DefaultedList<Slot> slots= context.getMenu().slots;
        for (int i = 6; i < 10; i++) {
            res.add(SlotAccessor.fromSlot(slots.get(i)));
        }
        return res;
    }

    @Override
    public MixerDisplay getDisplay() {
        return display;
    }
}
