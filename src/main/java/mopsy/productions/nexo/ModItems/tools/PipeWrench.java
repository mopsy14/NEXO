package mopsy.productions.nexo.ModItems.tools;

import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;

import static mopsy.productions.nexo.Main.CREATIVE_TOOLS_TAB_KEY;

public class PipeWrench extends Item implements IModID {


    @Override
    public String getID() {
        return "pipe_wrench";
    }
    public PipeWrench() {
        super(new Settings().maxCount(1));
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TOOLS_TAB_KEY).register(entries -> entries.add(this));
    }

}