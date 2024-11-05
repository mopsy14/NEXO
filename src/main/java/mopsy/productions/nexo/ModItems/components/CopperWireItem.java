package mopsy.productions.nexo.ModItems.components;

import mopsy.productions.nexo.interfaces.IModID;
import mopsy.productions.nexo.registry.ModdedItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static mopsy.productions.nexo.Main.CREATIVE_TOOLS_TAB_KEY;

public class CopperWireItem extends Item implements IModID {
    @Override
    public String getID() {
        return "copper_wire";
    }
    public CopperWireItem() {
        super(new Settings().maxDamage(5));
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TOOLS_TAB_KEY).register(entries -> entries.add(this));
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
