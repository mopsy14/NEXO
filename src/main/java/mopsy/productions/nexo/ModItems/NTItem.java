package mopsy.productions.nexo.ModItems;

import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;

import static mopsy.productions.nexo.Main.CREATIVE_TAB_KEY;
import static mopsy.productions.nexo.Main.CREATIVE_TOOLS_TAB_KEY;

public class NTItem extends Item implements IModID {

    String ID;

    public NTItem(Settings settings, String ID) {
        super(settings);
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TOOLS_TAB_KEY).register(entries -> entries.add(this));
        this.ID = ID;
    }
    public NTItem(String ID){
        super(new Settings());
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TAB_KEY).register(entries -> entries.add(this));
        this.ID = ID;
    }

    @Override
    public String getID() {
        return ID;
    }
}
