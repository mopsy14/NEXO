package mopsy.productions.nexo.registry;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;

public class ModdedToolMaterials {
    public static final ToolMaterial STEEL = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL,1000,7.0f,3.0f,14,ModdedTags.STEEL_TOOL_MATERIALS);
    public static final ToolMaterial TITANIUM = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL,1000,6.0f,2.5f,14,ModdedTags.TITANIUM_TOOL_MATERIALS);
}
