package mopsy.productions.nexo.ModItems.tools;

import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.CREATIVE_TOOLS_TAB_KEY;
import static mopsy.productions.nexo.Main.modid;

public class PipeWrench extends Item implements IModID {


    @Override
    public String getID() {
        return "pipe_wrench";
    }
    public PipeWrench() {
        super(new Settings().maxCount(1)
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modid,"pipe_wrench"))));
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TOOLS_TAB_KEY).register(entries -> entries.add(this));
    }

}