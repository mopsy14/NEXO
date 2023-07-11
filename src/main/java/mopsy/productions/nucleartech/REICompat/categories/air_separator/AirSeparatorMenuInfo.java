package mopsy.productions.nucleartech.REICompat.categories.air_separator;

import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoContext;
import me.shedaniel.rei.api.common.transfer.info.simple.SimplePlayerInventoryMenuInfo;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import mopsy.productions.nucleartech.screen.airSeparator.AirSeparatorScreenHandler;

import java.util.ArrayList;

public class AirSeparatorMenuInfo implements SimplePlayerInventoryMenuInfo<AirSeparatorScreenHandler, AirSeparatorDisplay> {
    AirSeparatorDisplay display;
    public AirSeparatorMenuInfo(Display display){
        this.display = (AirSeparatorDisplay) display;
    }

    @Override
    public Iterable<SlotAccessor> getInputSlots(MenuInfoContext<AirSeparatorScreenHandler, ?, AirSeparatorDisplay> context) {
        return new ArrayList<>();
    }

    @Override
    public AirSeparatorDisplay getDisplay() {
        return display;
    }
}
