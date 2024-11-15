package mopsy.productions.nexo.ModItems.tools;

import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.CREATIVE_TOOLS_TAB_KEY;
import static mopsy.productions.nexo.Main.modid;

public class NAxeItem extends AxeItem implements IModID {
    private final String id;
    @Override
    public String getID() {
        return id;
    }
    public NAxeItem(String id, ToolMaterial material, float attackDamage, float attackSpeed, Settings settings){
        super(material, attackDamage, attackSpeed, settings
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modid,id))));
        this.id = id;
    }
    public NAxeItem(String id, ToolMaterial material, float attackDamage, float attackSpeed){
        this(id,material,attackDamage,attackSpeed,new Settings()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modid,id))));
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TOOLS_TAB_KEY).register(entries -> entries.add(this));
    }
}
