package mopsy.productions.nexo.registry;

import mopsy.productions.nexo.ModBlocks.entities.InsulatedCopperCableEntity;
import mopsy.productions.nexo.ModBlocks.entities.deconShower.DeconShowerDrainEntity;
import mopsy.productions.nexo.ModBlocks.entities.deconShower.DeconShowerEntity;
import mopsy.productions.nexo.ModBlocks.entities.energyStorage.BatteryMK1Entity;
import mopsy.productions.nexo.ModBlocks.entities.machines.*;
import mopsy.productions.nexo.ModBlocks.entities.transport.FluidPipe_MK1Entity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import team.reborn.energy.api.EnergyStorage;

import static mopsy.productions.nexo.Main.LOGGER;
import static mopsy.productions.nexo.Main.modid;


public class ModdedBlockEntities {

    public static BlockEntityType<CrusherEntity> CRUSHER;
    public static BlockEntityType<TankEntity_MK1> TANK_MK1;
    public static BlockEntityType<PressEntity> PRESS;
    public static BlockEntityType<ElectrolyzerEntity> ELECTROLYZER;
    public static BlockEntityType<CentrifugeEntity> CENTRIFUGE;
    public static BlockEntityType<AirSeparatorEntity> AIR_SEPARATOR;
    public static BlockEntityType<FurnaceGeneratorEntity> FURNACE_GENERATOR;
    public static BlockEntityType<SteamTurbineEntity> STEAM_TURBINE;
    public static BlockEntityType<SmallReactorEntity> SMALL_REACTOR;
    public static BlockEntityType<AmmoniaSynthesizerEntity> AMMONIA_SYNTHESIZER;
    public static BlockEntityType<MixerEntity> MIXER;
    public static BlockEntityType<InsulatedCopperCableEntity> INSULATED_COPPER_CABLE;
    public static BlockEntityType<FluidPipe_MK1Entity> FLUID_PIPE_MK1;
    public static BlockEntityType<DeconShowerEntity> DECON_SHOWER;
    public static BlockEntityType<DeconShowerDrainEntity> DECON_SHOWER_DRAIN;
    public static BlockEntityType<ElectricFurnaceEntity> ELECTRIC_FURNACE;
    public static BlockEntityType<BatteryMK1Entity> BATTERY_MK1;

    public static void regBlockEntities() {
        LOGGER.info("Registering block entities");
        CRUSHER = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "crusher"),
                FabricBlockEntityTypeBuilder.create(CrusherEntity::new, ModdedBlocks.Blocks.get("crusher")).build(null));

        TANK_MK1 = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "tank_mk1"),
                FabricBlockEntityTypeBuilder.create(TankEntity_MK1::new, ModdedBlocks.Blocks.get("tank_mk1")).build(null));

        PRESS = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "press"),
                FabricBlockEntityTypeBuilder.create(PressEntity::new, ModdedBlocks.Blocks.get("press")).build(null));

        ELECTROLYZER = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "electrolyzer"),
                FabricBlockEntityTypeBuilder.create(ElectrolyzerEntity::new, ModdedBlocks.Blocks.get("electrolyzer")).build(null));

        CENTRIFUGE = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "centrifuge"),
                FabricBlockEntityTypeBuilder.create(CentrifugeEntity::new, ModdedBlocks.Blocks.get("centrifuge")).build(null));

        AIR_SEPARATOR = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "air_separator"),
                FabricBlockEntityTypeBuilder.create(AirSeparatorEntity::new, ModdedBlocks.Blocks.get("air_separator")).build(null));

        FURNACE_GENERATOR = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "furnace_generator"),
                FabricBlockEntityTypeBuilder.create(FurnaceGeneratorEntity::new, ModdedBlocks.Blocks.get("furnace_generator")).build(null));

        STEAM_TURBINE = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "steam_turbine"),
                FabricBlockEntityTypeBuilder.create(SteamTurbineEntity::new, ModdedBlocks.Blocks.get("steam_turbine")).build(null));

        SMALL_REACTOR = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "small_reactor"),
                FabricBlockEntityTypeBuilder.create(SmallReactorEntity::new, ModdedBlocks.Blocks.get("small_reactor_hatches")).build(null));

        AMMONIA_SYNTHESIZER = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "ammonia_synthesizer"),
                FabricBlockEntityTypeBuilder.create(AmmoniaSynthesizerEntity::new, ModdedBlocks.Blocks.get("ammonia_synthesizer")).build(null));

        MIXER = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "mixer"),
                FabricBlockEntityTypeBuilder.create(MixerEntity::new, ModdedBlocks.Blocks.get("mixer")).build(null));

        INSULATED_COPPER_CABLE = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "insulated_copper_cable"),
                FabricBlockEntityTypeBuilder.create(InsulatedCopperCableEntity::new, ModdedBlocks.Blocks.get("insulated_copper_cable")).build(null));

        FLUID_PIPE_MK1 = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "fluid_pipe_mk1"),
                FabricBlockEntityTypeBuilder.create(FluidPipe_MK1Entity::new, ModdedBlocks.Blocks.get("fluid_pipe_mk1")).build(null));

        DECON_SHOWER = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "decon_shower"),
                FabricBlockEntityTypeBuilder.create(DeconShowerEntity::new, ModdedBlocks.Blocks.get("decon_shower")).build(null));

        DECON_SHOWER_DRAIN = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "decon_shower_drain"),
                FabricBlockEntityTypeBuilder.create(DeconShowerDrainEntity::new, ModdedBlocks.Blocks.get("decon_shower_drain")).build(null));

        ELECTRIC_FURNACE = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "electric_furnace"),
                FabricBlockEntityTypeBuilder.create(ElectricFurnaceEntity::new, ModdedBlocks.Blocks.get("electric_furnace")).build(null));

        BATTERY_MK1 = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(modid, "battery_mk1"),
                FabricBlockEntityTypeBuilder.create(BatteryMK1Entity::new, ModdedBlocks.Blocks.get("battery_mk1")).build(null));

        //Power
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, CRUSHER);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, PRESS);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, AIR_SEPARATOR);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, ELECTROLYZER);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, CENTRIFUGE);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, FURNACE_GENERATOR);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, STEAM_TURBINE);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, AMMONIA_SYNTHESIZER);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, MIXER);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, INSULATED_COPPER_CABLE);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, ELECTRIC_FURNACE);
        EnergyStorage.SIDED.registerForBlockEntity(BatteryMK1Entity::getEnergyStorageFromDirection, BATTERY_MK1);
        //Fluids
        FluidStorage.SIDED.registerForBlockEntity((TankEntity_MK1::getFluidStorageFromDirection), TANK_MK1);
        FluidStorage.SIDED.registerForBlockEntity((ElectrolyzerEntity::getFluidStorageFromDirection), ELECTROLYZER);
        FluidStorage.SIDED.registerForBlockEntity((SmallReactorEntity::getFluidStorageFromDirection), SMALL_REACTOR);
        FluidStorage.SIDED.registerForBlockEntity((SteamTurbineEntity::getFluidStorageFromDirection), STEAM_TURBINE);
        FluidStorage.SIDED.registerForBlockEntity((AmmoniaSynthesizerEntity::getFluidStorageFromDirection), AMMONIA_SYNTHESIZER);
        FluidStorage.SIDED.registerForBlockEntity((DeconShowerEntity::getFluidStorageFromDirection), DECON_SHOWER);
        FluidStorage.SIDED.registerForBlockEntity((DeconShowerDrainEntity::getFluidStorageFromDirection), DECON_SHOWER_DRAIN);
    }
}
