package mopsy.productions.nexo.ModItems.armor.protectiveHazmat;

import mopsy.productions.nexo.ModItems.armor.ModdedArmorMaterials;
import mopsy.productions.nexo.interfaces.IArmorRadiationProtection;
import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;

import static mopsy.productions.nexo.Main.CREATIVE_TAB_KEY;

public class ProtectiveHazmatBoots extends ArmorItem implements IModID, IArmorRadiationProtection {
    @Override public String getID() {return "protective_hazmat_boots";}

    public ProtectiveHazmatBoots() {
        super(ModdedArmorMaterials.PROTECTIVE_HAZMAT, EquipmentSlot.FEET, new Settings());
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TAB_KEY).register(entries -> entries.add(this));
    }

    @Override
    public float getRadiationProtection() {
        return 0.15f;
    }
}
