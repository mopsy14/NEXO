package mopsy.productions.nexo.screen;

import mopsy.productions.nexo.screen.airSeparator.AirSeparatorScreen;
import mopsy.productions.nexo.screen.airSeparator.AirSeparatorScreenHandler;
import mopsy.productions.nexo.screen.ammoniaSynth.AmmoniaSynthesiserScreen;
import mopsy.productions.nexo.screen.ammoniaSynth.AmmoniaSynthesiserScreenHandler;
import mopsy.productions.nexo.screen.battery.BatteryMK1Screen;
import mopsy.productions.nexo.screen.battery.BatteryMK1ScreenHandler;
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
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static mopsy.productions.nexo.Main.LOGGER;
import static mopsy.productions.nexo.Main.modid;

public class ScreenHandlers {
    public static final Identifier SHPayloadID = Identifier.of(modid, "default_screen_handler");
    public static final ExtendedScreenHandlerType<CrusherScreenHandler,DefaultSHPayload> CRUSHER = new ExtendedScreenHandlerType<>(CrusherScreenHandler::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<PressScreenHandler,DefaultSHPayload> PRESS = new ExtendedScreenHandlerType<>(PressScreenHandler::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<TankScreenHandler_MK1,DefaultSHPayload> Tank_MK1 = new ExtendedScreenHandlerType<>(TankScreenHandler_MK1::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<AirSeparatorScreenHandler,DefaultSHPayload> AIR_SEPARATOR = new ExtendedScreenHandlerType<>(AirSeparatorScreenHandler::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<ElectrolyzerScreenHandler,DefaultSHPayload> ELECTROLYZER = new ExtendedScreenHandlerType<>(ElectrolyzerScreenHandler::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<CentrifugeScreenHandler,DefaultSHPayload> CENTRIFUGE = new ExtendedScreenHandlerType<>(CentrifugeScreenHandler::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<FurnaceGeneratorScreenHandler,DefaultSHPayload> FURNACE_GENERATOR = new ExtendedScreenHandlerType<>(FurnaceGeneratorScreenHandler::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<SteamTurbineScreenHandler,DefaultSHPayload> STEAM_TURBINE = new ExtendedScreenHandlerType<>(SteamTurbineScreenHandler::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<SmallReactorScreenHandler,DefaultSHPayload> SMALL_REACTOR = new ExtendedScreenHandlerType<>(SmallReactorScreenHandler::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<AmmoniaSynthesiserScreenHandler,DefaultSHPayload> AMMONIA_SYNTHESISER = new ExtendedScreenHandlerType<>(AmmoniaSynthesiserScreenHandler::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<MixerScreenHandler,DefaultSHPayload> MIXER = new ExtendedScreenHandlerType<>(MixerScreenHandler::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<FluidPipeScreenHandler,DefaultSHPayload> FLUID_PIPE = new ExtendedScreenHandlerType<>(FluidPipeScreenHandler::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<DeconShowerScreenHandler,DefaultSHPayload> DECON_SHOWER = new ExtendedScreenHandlerType<>(DeconShowerScreenHandler::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<DeconShowerDrainScreenHandler,DefaultSHPayload> DECON_SHOWER_DRAIN = new ExtendedScreenHandlerType<>(DeconShowerDrainScreenHandler::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<ElectricFurnaceScreenHandler,DefaultSHPayload> ELECTRIC_FURNACE = new ExtendedScreenHandlerType<>(ElectricFurnaceScreenHandler::new,DefaultSHPayload.CODEC);
    public static final ExtendedScreenHandlerType<BatteryMK1ScreenHandler,DefaultSHPayload> BATTERY = new ExtendedScreenHandlerType<>(BatteryMK1ScreenHandler::new,DefaultSHPayload.CODEC);

    public static void regScreenHandlers(){
        LOGGER.info("Registering screen handlers");
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "crusher"), CRUSHER);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "press"), PRESS);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "tank_mk1"), Tank_MK1);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "air_separator"), AIR_SEPARATOR);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "electrolyzer"), ELECTROLYZER);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "centrifuge"), CENTRIFUGE);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "furnace_generator"), FURNACE_GENERATOR);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "steam_turbine"), STEAM_TURBINE);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "small_reactor"), SMALL_REACTOR);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "ammonia_synthesiser"), AMMONIA_SYNTHESISER);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "mixer"), MIXER);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "fluid_pipe"), FLUID_PIPE);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "decon_shower"), DECON_SHOWER);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "decon_shower_drain"), DECON_SHOWER_DRAIN);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "electric_furnace"), ELECTRIC_FURNACE);
        Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "battery"), BATTERY);
    }
    public static void regClientScreens(){
        PayloadTypeRegistry.playS2C().register(DefaultSHPayload.ID,DefaultSHPayload.CODEC);
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
        HandledScreens.register(ScreenHandlers.BATTERY, BatteryMK1Screen::new);
    }
}
