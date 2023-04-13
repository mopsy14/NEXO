package mopsy.productions.nucleartech.ModItems.armor.hazmat;

import mopsy.productions.nucleartech.ModItems.armor.ModdedArmorMaterials;
import mopsy.productions.nucleartech.interfaces.IArmorRadiationProtection;
import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class HazmatBoots extends ArmorItem implements IModID, IArmorRadiationProtection {
    @Override public String getID() {return "hazmat_boots";}

    public HazmatBoots() {
        super(ModdedArmorMaterials.HAZMAT, EquipmentSlot.FEET, new FabricItemSettings().group(CREATIVE_TAB));
    }

    @Override
    public float getRadiationProtection() {
        return 0.1f;
    }
}