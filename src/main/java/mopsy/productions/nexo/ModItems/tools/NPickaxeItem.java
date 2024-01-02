package mopsy.productions.nexo.ModItems.tools;

import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

import static mopsy.productions.nexo.Main.CREATIVE_TAB;

public class NPickaxeItem extends PickaxeItem implements IModID {
    private final String id;
    @Override
    public String getID() {
        return id;
    }
    public NPickaxeItem(String id, ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings){
        super(material, attackDamage, attackSpeed, settings);
        this.id = id;
    }
    public NPickaxeItem(String id, ToolMaterial material, int attackDamage, float attackSpeed){
        this(id,material,attackDamage,attackSpeed,new FabricItemSettings().group(CREATIVE_TAB));
    }
}
