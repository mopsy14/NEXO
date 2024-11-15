package mopsy.productions.nexo.ModItems.tools;

import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.CREATIVE_TOOLS_TAB_KEY;
import static mopsy.productions.nexo.Main.modid;

public class NPickaxeItem extends PickaxeItem implements IModID {
    private final String id;
    @Override
    public String getID() {
        return id;
    }
    public NPickaxeItem(String id, ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings){
        super(material, attackDamage, attackSpeed, settings
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modid,id))));
        this.id = id;
    }
    public NPickaxeItem(String id, ToolMaterial material, int attackDamage, float attackSpeed){
        this(id,material,attackDamage,attackSpeed,new Settings()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modid,id))));
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TOOLS_TAB_KEY).register(entries -> entries.add(this));
    }
}
