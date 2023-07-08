package mopsy.productions.nucleartech.REICompat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoRegistry;
import me.shedaniel.rei.api.common.transfer.info.simple.SimpleMenuInfoProvider;
import mopsy.productions.nucleartech.Main;
import mopsy.productions.nucleartech.REICompat.categories.crusher.CrusherMenuInfo;
import mopsy.productions.nucleartech.REICompat.categories.crusher.CrushingDisplay;
import mopsy.productions.nucleartech.recipes.CrusherRecipe;
import mopsy.productions.nucleartech.screen.crusher.CrusherScreenHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;

import java.util.Optional;

import static mopsy.productions.nucleartech.Main.modid;

public class REIServerCompat implements REIServerPlugin {
    @Override
    public void registerMenuInfo(MenuInfoRegistry registry) {
        registry.register(CategoryIdentifier.of(modid,"crushing"), CrusherScreenHandler.class,
                SimpleMenuInfoProvider.of(CrusherMenuInfo::new)
        );
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(CategoryIdentifier.of(modid,"crushing"), new DisplaySerializer<CrushingDisplay>() {
            @Override
            public NbtCompound save(NbtCompound tag, CrushingDisplay display) {
                tag.putString("recipe",display.recipe.id.toString());
                return tag;
            }

            @Override
            public CrushingDisplay read(NbtCompound tag) {
                Optional<? extends Recipe<?>> optionalRecipe = Main.server.getRecipeManager().get(Identifier.tryParse(tag.getString("recipe")));

                return optionalRecipe.isPresent()? new CrushingDisplay((CrusherRecipe) optionalRecipe.get()) : null;
            }
        });
    }
}
