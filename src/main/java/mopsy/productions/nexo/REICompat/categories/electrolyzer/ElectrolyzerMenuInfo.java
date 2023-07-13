package mopsy.productions.nexo.REICompat.categories.electrolyzer;

import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoContext;
import me.shedaniel.rei.api.common.transfer.info.simple.SimplePlayerInventoryMenuInfo;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import mopsy.productions.nexo.screen.electrolyzer.ElectrolyzerScreenHandler;

import java.util.ArrayList;

public class ElectrolyzerMenuInfo implements SimplePlayerInventoryMenuInfo<ElectrolyzerScreenHandler, ElectrolyzerDisplay> {
    ElectrolyzerDisplay display;
    public ElectrolyzerMenuInfo(Display display){
        this.display = (ElectrolyzerDisplay) display;
    }

    @Override
    public Iterable<SlotAccessor> getInputSlots(MenuInfoContext<ElectrolyzerScreenHandler, ?, ElectrolyzerDisplay> context) {
        return new ArrayList<>();
    }

    @Override
    public ElectrolyzerDisplay getDisplay() {
        return display;
    }
}
