package mopsy.productions.nucleartech.ModItems;

import mopsy.productions.nucleartech.interfaces.IModID;
import net.minecraft.item.ItemStack;

public class NTFuelRodItem extends NTRadiatingItem implements IModID{
    public String ID;

    public NTFuelRodItem(Settings settings, String ID, int depletionTime, float radiation, float heat) {
        super(settings.maxCount(1).maxDamage(depletionTime), ID, radiation, heat);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }
    public static void addDamage(ItemStack stack){
        stack.setDamage(stack.getDamage()+1);
    }
}
