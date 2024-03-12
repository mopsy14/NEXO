package mopsy.productions.nexo.screen;

import mopsy.productions.nexo.screen.airSeparator.AirSeparatorScreen;
import mopsy.productions.nexo.screen.airSeparator.AirSeparatorScreenHandler;
import mopsy.productions.nexo.screen.ammoniaSynth.AmmoniaSynthesiserScreen;
import mopsy.productions.nexo.screen.ammoniaSynth.AmmoniaSynthesiserScreenHandler;
import mopsy.productions.nexo.screen.centrifuge.CentrifugeScreen;
import mopsy.productions.nexo.screen.centrifuge.CentrifugeScreenHandler;
import mopsy.productions.nexo.screen.crusher.CrusherScreen;
import mopsy.productions.nexo.screen.crusher.CrusherScreenHandler;
import mopsy.productions.nexo.screen.deconShower.DeconShowerScreen;
import mopsy.productions.nexo.screen.deconShower.DeconShowerScreenHandler;
import mopsy.productions.nexo.screen.deconShowerDrain.DeconShowerDrainScreen;
import mopsy.productions.nexo.screen.deconShowerDrain.DeconShowerDrainScreenHandler;
import mopsy.productions.nexo.screen.electricFurnace.ElectricFurnaceScreen;
import mopsy.productions.nexo.screen.electricFurnace.ElectricFurnaceScreenHandler;
import mopsy.productions.nexo.screen.electrolyzer.ElectrolyzerScreen;
import mopsy.productions.nexo.screen.electrolyzer.ElectrolyzerScreenHandler;
import mopsy.productions.nexo.screen.fluidPipe.FluidPipeScreen;
import mopsy.productions.nexo.screen.fluidPipe.FluidPipeScreenHandler;
import mopsy.productions.nexo.screen.furnaceGenerator.FurnaceGeneratorScreen;
import mopsy.productions.nexo.screen.furnaceGenerator.FurnaceGeneratorScreenHandler;
import mopsy.productions.nexo.screen.mixer.MixerScreen;
import mopsy.productions.nexo.screen.mixer.MixerScreenHandler;
import mopsy.productions.nexo.screen.press.PressScreen;
import mopsy.productions.nexo.screen.press.PressScreenHandler;
import mopsy.productions.nexo.screen.smallReactor.SmallReactorScreen;
import mopsy.productions.nexo.screen.smallReactor.SmallReactorScreenHandler;
import mopsy.productions.nexo.screen.steamTurbine.SteamTurbineScreen;
import mopsy.productions.nexo.screen.steamTurbine.SteamTurbineScreenHandler;
import mopsy.productions.nexo.screen.tank.TankScreenHandler_MK1;
import mopsy.productions.nexo.screen.tank.TankScreen_MK1;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static mopsy.productions.nexo.Main.LOGGER;
import static mopsy.productions.nexo.Main.modid;

public class ScreenHandlers {
    public static final ExtendedScreenHandlerType<CrusherScreenHandler> CRUSHER = new ExtendedScreenHandlerType<>(CrusherScreenHandler::new);
    public static final ExtendedScreenHandlerType<PressScreenHandler> PRESS = new ExtendedScreenHandlerType<>(PressScreenHandler::new);
    public static final ExtendedScreenHandlerType<TankScreenHandler_MK1> Tank_MK1 = new ExtendedScreenHandlerType<>(TankScreenHandler_MK1::new);
    public static final ExtendedScreenHandlerType<AirSeparatorScreenHandler> AIR_SEPARATOR = new ExtendedScreenHandlerType<>(AirSeparatorScreenHandler::new);
    public static final ExtendedScreenHandlerType<ElectrolyzerScreenHandler> ELECTROLYZER = new ExtendedScreenHandlerType<>(ElectrolyzerScreenHandler::new);
    public static final ExtendedScreenHandlerType<CentrifugeScreenHandler> CENTRIFUGE = new ExtendedScreenHandlerType<>(CentrifugeScreenHandler::new);
    public static final ExtendedScreenHandlerType<FurnaceGeneratorScreenHandler> FURNACE_GENERATOR = new ExtendedScreenHandlerType<>(FurnaceGeneratorScreenHandler::new);
    public static final ExtendedScreenHandlerType<SteamTurbineScreenHandler> STEAM_TURBINE = new ExtendedScreenHandlerType<>(SteamTurbineScreenHandler::new);
    public static final ExtendedScreenHandlerType<SmallReactorScreenHandler> SMALL_REACTOR = new ExtendedScreenHandlerType<>(SmallReactorScreenHandler::new);
    public static final ExtendedScreenHandlerType<AmmoniaSynthesiserScreenHandler> AMMONIA_SYNTHESISER = new ExtendedScreenHandlerType<>(AmmoniaSynthesiserScreenHandler::new);
    public static final ExtendedScreenHandlerType<MixerScreenHandler> MIXER = new ExtendedScreenHandlerType<>(MixerScreenHandler::new);
    public static final ExtendedScreenHandlerType<FluidPipeScreenHandler> FLUID_PIPE = new ExtendedScreenHandlerType<>(FluidPipeScreenHandler::new);
    public static final ExtendedScreenHandlerType<DeconShowerScreenHandler> DECON_SHOWER = new ExtendedScreenHandlerType<>(DeconShowerScreenHandler::new);
    public static final ExtendedScreenHandlerType<DeconShowerDrainScreenHandler> DECON_SHOWER_DRAIN = new ExtendedScreenHandlerType<>(DeconShowerDrainScreenHandler::new);
    public static final ExtendedScreenHandlerType<ElectricFurnaceScreenHandler> ELECTRIC_FURNACE = new ExtendedScreenHandlerType<>(ElectricFurnaceScreenHandler::new);

    public static void regScreenHandlers(){
        LOGGER.info("Registering screen handlers");
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "crusher"), CRUSHER);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "press"), PRESS);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "tank_mk1"), Tank_MK1);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "air_separator"), AIR_SEPARATOR);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "electrolyzer"), ELECTROLYZER);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "centrifuge"), CENTRIFUGE);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "furnace_generator"), FURNACE_GENERATOR);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "steam_turbine"), STEAM_TURBINE);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "small_reactor"), SMALL_REACTOR);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "ammonia_synthesiser"), AMMONIA_SYNTHESISER);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "mixer"), MIXER);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "fluid_pipe"), FLUID_PIPE);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "decon_shower"), DECON_SHOWER);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "decon_shower_drain"), DECON_SHOWER_DRAIN);
        Registry.register(Registry.SCREEN_HANDLER, new Identifier(modid, "electric_furnace"), ELECTRIC_FURNACE);
    }
    public static void regClientScreens(){
        HandledScreens.register(ScreenHandlers.CRUSHER, CrusherScreen::new);
        HandledScreens.register(ScreenHandlers.PRESS, PressScreen::new);
        HandledScreens.register(ScreenHandlers.Tank_MK1, TankScreen_MK1::new);
        HandledScreens.register(ScreenHandlers.AIR_SEPARATOR, AirSeparatorScreen::new);
        HandledScreens.register(ScreenHandlers.ELECTROLYZER, ElectrolyzerScreen::new);
        HandledScreens.register(ScreenHandlers.CENTRIFUGE, CentrifugeScreen::new);
        HandledScreens.register(ScreenHandlers.FURNACE_GENERATOR, FurnaceGeneratorScreen::new);
        HandledScreens.register(ScreenHandlers.STEAM_TURBINE, SteamTurbineScreen::new);
        HandledScreens.register(ScreenHandlers.SMALL_REACTOR, SmallReactorScreen::new);
        HandledScreens.register(ScreenHandlers.AMMONIA_SYNTHESISER, AmmoniaSynthesiserScreen::new);
        HandledScreens.register(ScreenHandlers.MIXER, MixerScreen::new);
        HandledScreens.register(ScreenHandlers.FLUID_PIPE, FluidPipeScreen::new);
        HandledScreens.register(ScreenHandlers.DECON_SHOWER, DeconShowerScreen::new);
        HandledScreens.register(ScreenHandlers.DECON_SHOWER_DRAIN, DeconShowerDrainScreen::new);
        HandledScreens.register(ScreenHandlers.ELECTRIC_FURNACE, ElectricFurnaceScreen::new);
    }
}
