package mopsy.productions.nexo.ModItems;

import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.modid;

public class NTFuelRodItem extends NTRadiatingItem implements IModID{
    public String ID;

    public NTFuelRodItem(Settings settings, String ID, int depletionTime, float radiation, float heat) {
        super(settings.maxCount(1).maxDamage(depletionTime)
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modid,ID))), ID, radiation, heat);
    }

    public static void addDamage(ItemStack stack){
        stack.setDamage(stack.getDamage()+1);
    }
}
