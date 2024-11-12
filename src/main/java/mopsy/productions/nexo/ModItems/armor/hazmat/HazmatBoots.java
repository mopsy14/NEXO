package mopsy.productions.nexo.ModItems.armor.hazmat;

import mopsy.productions.nexo.ModItems.armor.ModdedArmorMaterials;
import mopsy.productions.nexo.interfaces.IArmorRadiationProtection;
import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.equipment.EquipmentType;

import static mopsy.productions.nexo.Main.CREATIVE_TAB_KEY;

public class HazmatBoots extends ArmorItem implements IModID, IArmorRadiationProtection {
    @Override public String getID() {return "hazmat_boots";}

    public HazmatBoots() {
        super(ModdedArmorMaterials.HAZMAT, EquipmentType.BOOTS, new Settings());
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TAB_KEY).register(entries -> entries.add(this));
    }

    @Override
    public float getRadiationProtection() {
        return 0.1f;
    }
}
