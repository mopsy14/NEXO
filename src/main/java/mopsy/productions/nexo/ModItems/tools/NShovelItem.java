package mopsy.productions.nexo.ModItems.tools;

import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

import static mopsy.productions.nexo.Main.CREATIVE_TOOLS_TAB;

public class NShovelItem extends ShovelItem implements IModID {
    private final String id;
    @Override
    public String getID() {
        return id;
    }
    public NShovelItem(String id, ToolMaterial material, float attackDamage, float attackSpeed, Settings settings){
        super(material, attackDamage, attackSpeed, settings);
        this.id = id;
    }
    public NShovelItem(String id, ToolMaterial material, float attackDamage, float attackSpeed){
        this(id,material,attackDamage,attackSpeed,new FabricItemSettings().group(CREATIVE_TOOLS_TAB));
    }
}
