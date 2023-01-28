package mopsy.productions.nucleartech.screen;

import mopsy.productions.nucleartech.screen.crusher.CrusherScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandlers {
    public static ScreenHandlerType<CrusherScreenHandler> CRUSHER;

    public static void regScreenHandlers(){
        CRUSHER = new ScreenHandlerType<>(CrusherScreenHandler::new);
    }
}
