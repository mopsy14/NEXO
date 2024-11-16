package mopsy.productions.nexo.registry;

import mopsy.productions.nexo.recipes.*;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import static mopsy.productions.nexo.Main.LOGGER;
import static mopsy.productions.nexo.Main.modid;

public class ModdedRecipes {
    public static void regRecipes(){
        LOGGER.info("Registering recipe types");
        regRecipe(CrusherRecipe.Type.ID, CrusherRecipe.Serializer.INSTANCE, CrusherRecipe.Type.INSTANCE, CrusherRecipe::new);
        regRecipe(PressRecipe.Type.ID, PressRecipe.Serializer.INSTANCE, PressRecipe.Type.INSTANCE, PressRecipe::new);
        regRecipe(CentrifugeRecipe.Type.ID, CentrifugeRecipe.Serializer.INSTANCE, CentrifugeRecipe.Type.INSTANCE, CentrifugeRecipe::new);
        regRecipe(ElectrolyzerRecipe.Type.ID, ElectrolyzerRecipe.Serializer.INSTANCE, ElectrolyzerRecipe.Type.INSTANCE, ElectrolyzerRecipe::new);
        regRecipe(MixerRecipe.Type.ID, MixerRecipe.Serializer.INSTANCE, MixerRecipe.Type.INSTANCE, MixerRecipe::new);
        regRecipe(AirSeparatorRecipe.Type.ID, AirSeparatorRecipe.Serializer.INSTANCE, AirSeparatorRecipe.Type.INSTANCE, AirSeparatorRecipe::new);
        regRecipe(AmmoniaSynthesizerRecipe.Type.ID, AmmoniaSynthesizerRecipe.Serializer.INSTANCE, AmmoniaSynthesizerRecipe.Type.INSTANCE, AmmoniaSynthesizerRecipe::new);
        regRecipe(FillingRecipe.Type.ID, FillingRecipe.Serializer.INSTANCE, FillingRecipe.Type.INSTANCE, FillingRecipe::new);
    }
    private static void regRecipe(String ID, RecipeSerializer<?> recipeSerializer, RecipeType<?> recipeType, Function<NEXORecipe,? extends NEXORecipe> converter){
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(modid, ID), recipeSerializer);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(modid, ID), recipeType);
        NEXORecipe.recipeConverters.put(Identifier.of(modid, ID), converter);
    }
}
