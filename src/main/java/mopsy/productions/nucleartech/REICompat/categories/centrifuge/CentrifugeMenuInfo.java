package mopsy.productions.nucleartech.REICompat.categories.centrifuge;

import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoContext;
import me.shedaniel.rei.api.common.transfer.info.simple.SimplePlayerInventoryMenuInfo;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import mopsy.productions.nucleartech.screen.centrifuge.CentrifugeScreenHandler;

import java.util.ArrayList;

public class CentrifugeMenuInfo implements SimplePlayerInventoryMenuInfo<CentrifugeScreenHandler, CentrifugeDisplay> {
    //TODO: Add REI support for the test tubes.
    CentrifugeDisplay display;
    public CentrifugeMenuInfo(Display display){
        this.display = (CentrifugeDisplay) display;
    }

    @Override
    public Iterable<SlotAccessor> getInputSlots(MenuInfoContext<CentrifugeScreenHandler, ?, CentrifugeDisplay> context) {
        return new ArrayList<>();
    }

    @Override
    public CentrifugeDisplay getDisplay() {
        return display;
    }
}