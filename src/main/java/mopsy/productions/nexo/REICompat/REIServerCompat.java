package mopsy.productions.nexo.REICompat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoRegistry;
import me.shedaniel.rei.api.common.transfer.info.simple.SimpleMenuInfoProvider;
import mopsy.productions.nexo.REICompat.categories.air_separator.AirSeparatorDisplay;
import mopsy.productions.nexo.REICompat.categories.air_separator.AirSeparatorMenuInfo;
import mopsy.productions.nexo.REICompat.categories.centrifuge.CentrifugeDisplay;
import mopsy.productions.nexo.REICompat.categories.centrifuge.CentrifugeMenuInfo;
import mopsy.productions.nexo.REICompat.categories.crusher.CrusherMenuInfo;
import mopsy.productions.nexo.REICompat.categories.crusher.CrushingDisplay;
import mopsy.productions.nexo.REICompat.categories.electrolyzer.ElectrolyzerDisplay;
import mopsy.productions.nexo.REICompat.categories.electrolyzer.ElectrolyzerMenuInfo;
import mopsy.productions.nexo.REICompat.categories.filling.FillingDisplay;
import mopsy.productions.nexo.REICompat.categories.filling.FillingMenuInfo;
import mopsy.productions.nexo.REICompat.categories.mixer.MixerDisplay;
import mopsy.productions.nexo.REICompat.categories.mixer.MixerMenuInfo;
import mopsy.productions.nexo.REICompat.categories.press.PressDisplay;
import mopsy.productions.nexo.REICompat.categories.press.PressMenuInfo;
import mopsy.productions.nexo.screen.airSeparator.AirSeparatorScreenHandler;
import mopsy.productions.nexo.screen.centrifuge.CentrifugeScreenHandler;
import mopsy.productions.nexo.screen.crusher.CrusherScreenHandler;
import mopsy.productions.nexo.screen.electrolyzer.ElectrolyzerScreenHandler;
import mopsy.productions.nexo.screen.mixer.MixerScreenHandler;
import mopsy.productions.nexo.screen.press.PressScreenHandler;
import mopsy.productions.nexo.screen.tank.TankScreenHandler_MK1;

import static mopsy.productions.nexo.Main.modid;

public class REIServerCompat implements REIServerPlugin {
    @Override
    public void registerMenuInfo(MenuInfoRegistry registry) {
        registry.register(CategoryIdentifier.of(modid,"crusher"), CrusherScreenHandler.class,
                SimpleMenuInfoProvider.of(CrusherMenuInfo::new)
        );
        registry.register(CategoryIdentifier.of(modid,"press"), PressScreenHandler.class,
                SimpleMenuInfoProvider.of(PressMenuInfo::new)
        );
        registry.register(CategoryIdentifier.of(modid,"mixer"), MixerScreenHandler.class,
                SimpleMenuInfoProvider.of(MixerMenuInfo::new)
        );
        registry.register(CategoryIdentifier.of(modid,"centrifuge"), CentrifugeScreenHandler.class,
                SimpleMenuInfoProvider.of(CentrifugeMenuInfo::new)
        );
        registry.register(CategoryIdentifier.of(modid,"electrolyzer"), ElectrolyzerScreenHandler.class,
                SimpleMenuInfoProvider.of(ElectrolyzerMenuInfo::new)
        );
        registry.register(CategoryIdentifier.of(modid,"air_separator"), AirSeparatorScreenHandler.class,
                SimpleMenuInfoProvider.of(AirSeparatorMenuInfo::new)
        );
        registry.register(CategoryIdentifier.of(modid,"filling"), TankScreenHandler_MK1.class,
                SimpleMenuInfoProvider.of(FillingMenuInfo::new)
        );
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(CategoryIdentifier.of(modid,"crusher"), new DefaultDisplaySerializer(CrushingDisplay::new));
        registry.register(CategoryIdentifier.of(modid,"press"), new DefaultDisplaySerializer(PressDisplay::new));
        registry.register(CategoryIdentifier.of(modid,"mixer"), new DefaultDisplaySerializer(MixerDisplay::new));
        registry.register(CategoryIdentifier.of(modid,"centrifuge"), new DefaultDisplaySerializer(CentrifugeDisplay::new));
        registry.register(CategoryIdentifier.of(modid,"electrolyzer"), new DefaultDisplaySerializer(ElectrolyzerDisplay::new));
        registry.register(CategoryIdentifier.of(modid,"air_separator"), new DefaultDisplaySerializer(AirSeparatorDisplay::new));
        registry.register(CategoryIdentifier.of(modid,"filling"), new DefaultDisplaySerializer(FillingDisplay::new));
    }
}
