package mopsy.productions.nexo.registry;

import mopsy.productions.nexo.recipes.*;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Function;

import static mopsy.productions.nexo.Main.LOGGER;
import static mopsy.productions.nexo.Main.modid;

public class ModdedRecipes {
    public static void regRecipes(){
        LOGGER.info("Registering recipe types");
        regRecipe(CrusherRecipe.Type.ID, CrusherRecipe.Serializer.INSTANCE, CrusherRecipe.Type.INSTANCE, CrusherRecipe::new);
        regRecipe(PressRecipe.Type.ID, PressRecipe.Serializer.INSTANCE, PressRecipe.Type.INSTANCE, CrusherRecipe::new);
        regRecipe(CentrifugeRecipe.Type.ID, CentrifugeRecipe.Serializer.INSTANCE, CentrifugeRecipe.Type.INSTANCE, CrusherRecipe::new);
        regRecipe(ElectrolyzerRecipe.Type.ID, ElectrolyzerRecipe.Serializer.INSTANCE, ElectrolyzerRecipe.Type.INSTANCE, CrusherRecipe::new);
        regRecipe(MixerRecipe.Type.ID, MixerRecipe.Serializer.INSTANCE, MixerRecipe.Type.INSTANCE, CrusherRecipe::new);
        regRecipe(AirSeparatorRecipe.Type.ID, AirSeparatorRecipe.Serializer.INSTANCE, AirSeparatorRecipe.Type.INSTANCE, CrusherRecipe::new);
        regRecipe(AmmoniaSynthesizerRecipe.Type.ID, AmmoniaSynthesizerRecipe.Serializer.INSTANCE, AmmoniaSynthesizerRecipe.Type.INSTANCE, CrusherRecipe::new);
        regRecipe(FillingRecipe.Type.ID, FillingRecipe.Serializer.INSTANCE, FillingRecipe.Type.INSTANCE, CrusherRecipe::new);
    }
    private static void regRecipe(String ID, RecipeSerializer<?> recipeSerializer, RecipeType<?> recipeType, Function<NEXORecipe,? extends NEXORecipe> converter){
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(modid, ID), recipeSerializer);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(modid, ID), recipeType);
        NEXORecipe.recipeConverters.put(new Identifier(modid, ID), converter);
    }
}
