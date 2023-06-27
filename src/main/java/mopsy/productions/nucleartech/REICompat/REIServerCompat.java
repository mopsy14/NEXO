package mopsy.productions.nucleartech.REICompat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoRegistry;
import me.shedaniel.rei.api.common.transfer.info.simple.SimpleMenuInfoProvider;
import mopsy.productions.nucleartech.REICompat.categories.CrusherMenuInfo;
import mopsy.productions.nucleartech.screen.crusher.CrusherScreenHandler;

import static mopsy.productions.nucleartech.Main.modid;

public class REIServerCompat implements REIServerPlugin {
    @Override
    public void registerMenuInfo(MenuInfoRegistry registry) {
        registry.register(CategoryIdentifier.of(modid,"crushing"), CrusherScreenHandler.class,
                SimpleMenuInfoProvider.of(CrusherMenuInfo::new)
        );
    }
}
