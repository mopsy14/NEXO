package mopsy.productions.nexo.ModItems.tools;

import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

import static mopsy.productions.nexo.Main.CREATIVE_TOOLS_TAB;

public class NSwordItem extends SwordItem implements IModID {
    private final String id;
    @Override
    public String getID() {
        return id;
    }
    public NSwordItem(String id, ToolMaterial material, int attackDamage, float attackSpeed, Settings settings){
        super(material, attackDamage, attackSpeed, settings);
        this.id = id;
    }
    public NSwordItem(String id, ToolMaterial material, int attackDamage, float attackSpeed){
        this(id,material,attackDamage,attackSpeed,new FabricItemSettings().group(CREATIVE_TOOLS_TAB));
    }
}
