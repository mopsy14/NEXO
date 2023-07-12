package mopsy.productions.nucleartech.REICompat.categories.ammonia_synthesizer;

import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoContext;
import me.shedaniel.rei.api.common.transfer.info.simple.SimplePlayerInventoryMenuInfo;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import mopsy.productions.nucleartech.screen.ammoniaSynth.AmmoniaSynthesiserScreenHandler;

import java.util.ArrayList;

public class AmmoniaSynthesizerMenuInfo implements SimplePlayerInventoryMenuInfo<AmmoniaSynthesiserScreenHandler, AmmoniaSynthesizerDisplay> {
    AmmoniaSynthesizerDisplay display;
    public AmmoniaSynthesizerMenuInfo(Display display){
        this.display = (AmmoniaSynthesizerDisplay) display;
    }

    @Override
    public Iterable<SlotAccessor> getInputSlots(MenuInfoContext<AmmoniaSynthesiserScreenHandler, ?, AmmoniaSynthesizerDisplay> context) {
        return new ArrayList<>();
    }

    @Override
    public AmmoniaSynthesizerDisplay getDisplay() {
        return display;
    }
}
