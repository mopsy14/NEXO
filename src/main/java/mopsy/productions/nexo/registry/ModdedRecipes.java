package mopsy.productions.nexo.registry;

import mopsy.productions.nexo.recipes.*;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static mopsy.productions.nexo.Main.modid;

public class ModdedRecipes {
    public static void regRecipes(){
        regRecipe(CrusherRecipe.Type.ID, CrusherRecipe.Serializer.INSTANCE, CrusherRecipe.Type.INSTANCE);
        regRecipe(PressRecipe.Type.ID, PressRecipe.Serializer.INSTANCE, PressRecipe.Type.INSTANCE);
        regRecipe(CentrifugeRecipe.Type.ID, CentrifugeRecipe.Serializer.INSTANCE, CentrifugeRecipe.Type.INSTANCE);
        regRecipe(ElectrolyzerRecipe.Type.ID, ElectrolyzerRecipe.Serializer.INSTANCE, ElectrolyzerRecipe.Type.INSTANCE);
        regRecipe(MixerRecipe.Type.ID, MixerRecipe.Serializer.INSTANCE, MixerRecipe.Type.INSTANCE);
        regRecipe(AirSeparatorRecipe.Type.ID, AirSeparatorRecipe.Serializer.INSTANCE, AirSeparatorRecipe.Type.INSTANCE);
        regRecipe(AmmoniaSynthesizerRecipe.Type.ID, AmmoniaSynthesizerRecipe.Serializer.INSTANCE, AmmoniaSynthesizerRecipe.Type.INSTANCE);
        regRecipe(FillingRecipe.Type.ID, FillingRecipe.Serializer.INSTANCE, FillingRecipe.Type.INSTANCE);
    }
    private static void regRecipe(String ID, RecipeSerializer<?> recipeSerializer, RecipeType<?> recipeType){
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(modid, ID), recipeSerializer);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(modid, ID), recipeType);
    }
}
