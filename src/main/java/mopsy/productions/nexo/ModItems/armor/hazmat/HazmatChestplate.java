package mopsy.productions.nexo.ModItems.armor.hazmat;

import mopsy.productions.nexo.ModItems.armor.ModdedArmorMaterials;
import mopsy.productions.nexo.interfaces.IArmorRadiationProtection;
import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;

public class HazmatChestplate extends ArmorItem implements IModID, IArmorRadiationProtection {
    @Override public String getID() {return "hazmat_chestplate";}

    public HazmatChestplate() {
        super(ModdedArmorMaterials.HAZMAT, EquipmentSlot.CHEST, new Settings());ItemGroupEvents.modifyEntriesEvent(CREATIVE_TAB_KEY).register(entries -> entries.add(this));
    }

    @Override
    public float getRadiationProtection() {
        return 0.25f;
    }
}
