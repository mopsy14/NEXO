package mopsy.productions.nucleartech.REICompat;

import me.shedaniel.rei.api.common.display.DisplaySerializer;
import mopsy.productions.nucleartech.Main;
import mopsy.productions.nucleartech.interfaces.INEXODisplay;
import mopsy.productions.nucleartech.recipes.NEXORecipe;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import static mopsy.productions.nucleartech.Main.LOGGER;

public class DefaultDisplaySerializer implements DisplaySerializer<NEXODisplay> {

    @Override
    public NbtCompound save(NbtCompound tag, NEXODisplay display) {
        if(display instanceof INEXODisplay nexoDisplay)
            tag.putString("recipe",nexoDisplay.getID().toString());
        else
            LOGGER.error(display+"Does not implement INEXODisplay");
        return tag;
    }

    @Override
    public NEXODisplay read(NbtCompound tag) {
        var optionalRecipe = Main.server.getRecipeManager().get(Identifier.tryParse(tag.getString("recipe")));

        return optionalRecipe.isPresent() ? new NEXODisplay((NEXORecipe) optionalRecipe.get()) : null;
    }
}
