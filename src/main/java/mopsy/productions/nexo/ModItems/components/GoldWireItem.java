package mopsy.productions.nexo.ModItems.components;

import mopsy.productions.nexo.interfaces.IModID;
import mopsy.productions.nexo.registry.ModdedItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.CREATIVE_TAB_KEY;
import static mopsy.productions.nexo.Main.modid;

public class GoldWireItem extends Item implements IModID {
    @Override
    public String getID() {
        return "gold_wire";
    }
    public GoldWireItem() {
        super(new Settings().maxDamage(5)
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modid,"gold_wire"))));
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TAB_KEY).register(entries -> entries.add(this));
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
