package mopsy.productions.nexo.ModItems.tools.titanium;

import mopsy.productions.nexo.registry.ModdedItems;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class TitaniumToolMaterial implements ToolMaterial {
    public static final TitaniumToolMaterial INSTANCE = new TitaniumToolMaterial();
    @Override
    public int getDurability() {
        return 500;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 6.0f;
    }

    @Override
    public float getAttackDamage() {
        return 2.5f;
    }

    @Override
    public int getMiningLevel() {
        return MiningLevels.IRON;
    }

    @Override
    public int getEnchantability() {
        return 14;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModdedItems.Items.get("titanium_ingot"));
    }
}
