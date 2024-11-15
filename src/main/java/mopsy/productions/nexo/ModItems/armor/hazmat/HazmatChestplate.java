package mopsy.productions.nexo.ModItems.armor.hazmat;

import mopsy.productions.nexo.ModItems.armor.ModdedArmorMaterials;
import mopsy.productions.nexo.interfaces.IArmorRadiationProtection;
import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.CREATIVE_TAB_KEY;
import static mopsy.productions.nexo.Main.modid;

public class HazmatChestplate extends ArmorItem implements IModID, IArmorRadiationProtection {
    @Override public String getID() {return "hazmat_chestplate";}

    public HazmatChestplate() {
        super(ModdedArmorMaterials.HAZMAT, EquipmentType.CHESTPLATE, new Settings()
                .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modid,"hazmat_chestplate"))));
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TAB_KEY).register(entries -> entries.add(this));
    }

    @Override
    public float getRadiationProtection() {
        return 0.25f;
    }
}
