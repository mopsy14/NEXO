package mopsy.productions.nexo.ModItems.armor;

import mopsy.productions.nexo.registry.ModdedTags;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;

import static mopsy.productions.nexo.Main.modid;

public class ModdedArmorMaterials{
    public static final ArmorMaterial MAKESHIFT_HAZMAT = new ArmorMaterial(2,Util.make(new EnumMap(EquipmentType.class), map -> {
        map.put(EquipmentType.BOOTS, 1);
        map.put(EquipmentType.LEGGINGS, 2);
        map.put(EquipmentType.CHESTPLATE, 2);
        map.put(EquipmentType.HELMET, 1);
        map.put(EquipmentType.BODY, 3);
    }), 5, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, ModdedTags.MAKESHIFT_HAZMAT_MATERIALS, Identifier.of(modid,"makeshift_hazmat"));
    public static final ArmorMaterial HAZMAT = new ArmorMaterial(5, Util.make(new EnumMap(EquipmentType.class), map -> {
        map.put(EquipmentType.BOOTS, 1);
        map.put(EquipmentType.LEGGINGS, 3);
        map.put(EquipmentType.CHESTPLATE, 4);
        map.put(EquipmentType.HELMET, 2);
        map.put(EquipmentType.BODY, 4);
    }), 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, ModdedTags.HAZMAT_MATERIALS, Identifier.of(modid,"hazmat"));
    public static final ArmorMaterial PROTECTIVE_HAZMAT = new ArmorMaterial(2, Util.make(new EnumMap(EquipmentType.class), map -> {
        map.put(EquipmentType.BOOTS, 3);
        map.put(EquipmentType.LEGGINGS, 6);
        map.put(EquipmentType.CHESTPLATE, 8);
        map.put(EquipmentType.HELMET, 3);
        map.put(EquipmentType.BODY, 11);
    }), 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, ModdedTags.PROTECTIVE_HAZMAT_MATERIALS, Identifier.of(modid,"protective_hazmat"));
}

