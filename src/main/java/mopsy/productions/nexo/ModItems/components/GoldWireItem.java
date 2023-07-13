package mopsy.productions.nexo.ModItems.components;

import mopsy.productions.nexo.interfaces.IModID;
import mopsy.productions.nexo.registry.ModdedItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static mopsy.productions.nexo.Main.CREATIVE_TAB;

public class GoldWireItem extends Item implements IModID {
    @Override
    public String getID() {
        return "gold_wire";
    }
    public GoldWireItem() {
        super(new FabricItemSettings().group(CREATIVE_TAB).maxDamage(5));
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        ItemStack res;
        if(stack.getDamage()<4) {
            res = stack.copy();
            res.setDamage(res.getDamage()+1);
        }else{
            res = new ItemStack(ModdedItems.Items.get("wire_casing"));
        }


        return res;
    }
}
