package mopsy.productions.nucleartech.recipes;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static mopsy.productions.nucleartech.Main.modid;

public class ModdedRecipes {
    public static void regRecipes(){
        regRecipe(CrusherRecipe.Type.ID, CrusherRecipe.Serializer.INSTANCE, CrusherRecipe.Type.INSTANCE);


    }
    private static void regRecipe(String ID, RecipeSerializer<?> recipeSerializer, RecipeType<?> recipeType){
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(modid, ID), recipeSerializer);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(modid, ID), recipeType);
    }
}