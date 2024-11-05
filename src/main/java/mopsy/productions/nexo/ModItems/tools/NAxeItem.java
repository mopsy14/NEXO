package mopsy.productions.nexo.ModItems.tools;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ToolMaterial;

import static mopsy.productions.nexo.Main.CREATIVE_TOOLS_TAB_KEY;

public class NAxeItem extends AxeItem implements IModID {
    private final String id;
    @Override
    public String getID() {
        return id;
    }
    public NAxeItem(String id, ToolMaterial material, float attackDamage, float attackSpeed, Settings settings){
        super(material, attackDamage, attackSpeed, settings);
        this.id = id;
    }
    public NAxeItem(String id, ToolMaterial material, float attackDamage, float attackSpeed){
        this(id,material,attackDamage,attackSpeed,new Settings());ItemGroupEvents.modifyEntriesEvent(CREATIVE_TOOLS_TAB_KEY).register(entries -> entries.add(this));
    }
}
