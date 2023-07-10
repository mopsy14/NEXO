package mopsy.productions.nucleartech.REICompat;

import me.shedaniel.rei.api.common.display.DisplaySerializer;
import mopsy.productions.nucleartech.Main;
import mopsy.productions.nucleartech.recipes.NEXORecipe;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class DefaultDisplaySerializer implements DisplaySerializer<NEXODisplay> {
    Function<NEXORecipe,NEXODisplay> constructor;
    public DefaultDisplaySerializer(Function<NEXORecipe,NEXODisplay> constructor){
        this.constructor=constructor;
    }

    @Override
    public NbtCompound save(NbtCompound tag, NEXODisplay display) {
        tag.putString("recipe",display.recipe.id.toString());
        return tag;
    }

    @Override
    public NEXODisplay read(NbtCompound tag) {
        var optionalRecipe = Main.server.getRecipeManager().get(Identifier.tryParse(tag.getString("recipe")));

        return optionalRecipe.isPresent() ? constructor.apply((NEXORecipe) optionalRecipe.get()) : null;
    }
}
