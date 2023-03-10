package mopsy.productions.nucleartech.screen;

import mopsy.productions.nucleartech.screen.airSeparator.AirSeparatorScreen;
import mopsy.productions.nucleartech.screen.airSeparator.AirSeparatorScreenHandler;
import mopsy.productions.nucleartech.screen.centrifuge.CentrifugeScreen;
import mopsy.productions.nucleartech.screen.centrifuge.CentrifugeScreenHandler;
import mopsy.productions.nucleartech.screen.crusher.CrusherScreen;
import mopsy.productions.nucleartech.screen.crusher.CrusherScreenHandler;
import mopsy.productions.nucleartech.screen.electrolyzer.ElectrolyzerScreen;
import mopsy.productions.nucleartech.screen.electrolyzer.ElectrolyzerScreenHandler;
import mopsy.productions.nucleartech.screen.furnaceGenerator.FurnaceGeneratorScreen;
import mopsy.productions.nucleartech.screen.furnaceGenerator.FurnaceGeneratorScreenHandler;
import mopsy.productions.nucleartech.screen.press.PressScreen;
import mopsy.productions.nucleartech.screen.press.PressScreenHandler;
import mopsy.productions.nucleartech.screen.tank.TankScreenHandler_MK1;
import mopsy.productions.nucleartech.screen.tank.TankScreen_MK1;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static mopsy.productions.nucleartech.Main.modid;

public class ScreenHandlers {
    public static final ExtendedScreenHandlerType<CrusherScreenHandler> CRUSHER = new ExtendedScreenHandlerType<>(CrusherScreenHandler::new);
    public static final ExtendedScreenHandlerType<PressScreenHandler> PRESS = new ExtendedScreenHandlerType<>(PressScreenHandler::new);
    public static final ExtendedScreenHandlerType<TankScreenHandler_MK1> Tank_MK1 = new ExtendedScreenHandlerType<>(TankScreenHandler_MK1::new);
    public static final ExtendedScreenHandlerType<AirSeparatorScreenHandler> AIR_SEPARATOR = new ExtendedScreenHandlerType<>(AirSeparatorScreenHandler::new);
    public static final ExtendedScreenHandlerType<ElectrolyzerScreenHandler> ELECTROLYZER = new ExtendedScreenHandlerType<>(ElectrolyzerScreenHandler::new);
    public static final ExtendedScreenHandlerType<CentrifugeScreenHandler> CENTRIFUGE = new ExtendedScreenHandlerType<>(CentrifugeScreenHandler::new);
    public static final ExtendedScreenHandlerType<FurnaceGeneratorScreenHandler> FURNACE_GENERATOR = new ExtendedScreenHandlerType<>(FurnaceGeneratorScreenHandler::new);

    public static void regScreenHandlers(){
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "crusher"), CRUSHER);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "press"), PRESS);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "tank_mk1"), Tank_MK1);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "air_separator"), AIR_SEPARATOR);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "electrolyzer"), ELECTROLYZER);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "centrifuge"), CENTRIFUGE);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "furnace_generator"), FURNACE_GENERATOR);
    }
    public static void regClientScreens(){
        HandledScreens.register(ScreenHandlers.CRUSHER, CrusherScreen::new);
        HandledScreens.register(ScreenHandlers.PRESS, PressScreen::new);
        HandledScreens.register(ScreenHandlers.Tank_MK1, TankScreen_MK1::new);
        HandledScreens.register(ScreenHandlers.AIR_SEPARATOR, AirSeparatorScreen::new);
        HandledScreens.register(ScreenHandlers.ELECTROLYZER, ElectrolyzerScreen::new);
        HandledScreens.register(ScreenHandlers.CENTRIFUGE, CentrifugeScreen::new);
        HandledScreens.register(ScreenHandlers.FURNACE_GENERATOR, FurnaceGeneratorScreen::new);
    }
}
