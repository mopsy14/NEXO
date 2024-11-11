package mopsy.productions.nexo.REICompat;

import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import mopsy.productions.nexo.REICompat.categories.air_separator.AirSeparatorDisplay;
import mopsy.productions.nexo.REICompat.categories.centrifuge.CentrifugeDisplay;
import mopsy.productions.nexo.REICompat.categories.crusher.CrushingDisplay;
import mopsy.productions.nexo.REICompat.categories.electrolyzer.ElectrolyzerDisplay;
import mopsy.productions.nexo.REICompat.categories.filling.FillingDisplay;
import mopsy.productions.nexo.REICompat.categories.mixer.MixerDisplay;
import mopsy.productions.nexo.REICompat.categories.press.PressDisplay;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.modid;

public class REIServerCompat implements REICommonPlugin {
    /*@Override
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
     */

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(Identifier.of(modid,"crusher"), CrushingDisplay.SERIALIZER);
        registry.register(Identifier.of(modid,"press"), PressDisplay.SERIALIZER);
        registry.register(Identifier.of(modid,"mixer"), MixerDisplay.SERIALIZER);
        registry.register(Identifier.of(modid,"centrifuge"), CentrifugeDisplay.SERIALIZER);
        registry.register(Identifier.of(modid,"electrolyzer"), ElectrolyzerDisplay.SERIALIZER);
        registry.register(Identifier.of(modid,"air_separator"), AirSeparatorDisplay.SERIALIZER);
        registry.register(Identifier.of(modid,"filling"), FillingDisplay.SERIALIZER);
    }
}
