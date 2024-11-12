package mopsy.productions.nexo.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.modid;

public class ModdedTags {
    public static TagKey<Item> STEEL_TOOL_MATERIALS=regTag("steel_tool_materials");
    public static TagKey<Item> TITANIUM_TOOL_MATERIALS=regTag("titanium_tool_materials");
    public static TagKey<Item> MAKESHIFT_HAZMAT_MATERIALS=regTag("makeshift_hazmat_materials");
    public static TagKey<Item> HAZMAT_MATERIALS=regTag("hazmat_materials");
    public static TagKey<Item> PROTECTIVE_HAZMAT_MATERIALS=regTag("protective_hazmat_materials");


    private static TagKey<Item> regTag(String id){
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(modid,id));
    }
}
