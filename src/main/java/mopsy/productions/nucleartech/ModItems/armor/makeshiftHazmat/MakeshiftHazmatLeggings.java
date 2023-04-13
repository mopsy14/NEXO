package mopsy.productions.nucleartech.ModItems.armor.makeshiftHazmat;

import mopsy.productions.nucleartech.ModItems.armor.ModdedArmorMaterials;
import mopsy.productions.nucleartech.interfaces.IArmorRadiationProtection;
import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class MakeshiftHazmatLeggings extends ArmorItem implements IModID, IArmorRadiationProtection {
    @Override public String getID() {return "makeshift_hazmat_leggings";}

    public MakeshiftHazmatLeggings() {
        super(ModdedArmorMaterials.MAKESHIFT_HAZMAT, EquipmentSlot.LEGS, new FabricItemSettings().group(CREATIVE_TAB));
    }

    @Override
    public float getRadiationProtection() {
        return 0.1f;
    }
}
