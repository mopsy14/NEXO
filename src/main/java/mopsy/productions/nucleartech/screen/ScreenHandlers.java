package mopsy.productions.nucleartech.screen;

import mopsy.productions.nucleartech.screen.airSeparator.AirSeparatorScreenHandler;
import mopsy.productions.nucleartech.screen.crusher.CrusherScreenHandler;
import mopsy.productions.nucleartech.screen.press.PressScreenHandler;
import mopsy.productions.nucleartech.screen.tank.TankScreenHandler_MK1;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static mopsy.productions.nucleartech.Main.modid;

public class ScreenHandlers {
    public static ExtendedScreenHandlerType<CrusherScreenHandler> CRUSHER = new ExtendedScreenHandlerType<>(CrusherScreenHandler::new);
    public static ExtendedScreenHandlerType<PressScreenHandler> PRESS = new ExtendedScreenHandlerType<>(PressScreenHandler::new);
    public static ExtendedScreenHandlerType<TankScreenHandler_MK1> Tank_MK1 = new ExtendedScreenHandlerType<>(TankScreenHandler_MK1::new);
    public static ExtendedScreenHandlerType<AirSeparatorScreenHandler> AIR_SEPARATOR = new ExtendedScreenHandlerType<>(AirSeparatorScreenHandler::new);

    public static void regScreenHandlers(){
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "crusher"), CRUSHER);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "press"), PRESS);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "tank_mk1"), Tank_MK1);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "air_separator"), AIR_SEPARATOR);
    }
}
