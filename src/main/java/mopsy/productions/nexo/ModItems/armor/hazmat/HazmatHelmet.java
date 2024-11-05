package mopsy.productions.nexo.ModItems.armor.hazmat;

import mopsy.productions.nexo.ModItems.armor.ModdedArmorMaterials;
import mopsy.productions.nexo.interfaces.IArmorRadiationProtection;
import mopsy.productions.nexo.interfaces.IModID;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;

public class HazmatHelmet extends ArmorItem implements IModID, IArmorRadiationProtection {
    @Override public String getID() {return "hazmat_helmet";}

    public HazmatHelmet() {
        super(ModdedArmorMaterials.HAZMAT, EquipmentSlot.HEAD, new Settings());ItemGroupEvents.modifyEntriesEvent(CREATIVE_TAB_KEY).register(entries -> entries.add(this));
    }

    @Override
    public float getRadiationProtection() {
        return 0.1f;
    }
}
