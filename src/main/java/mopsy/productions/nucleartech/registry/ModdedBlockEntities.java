package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModBlocks.entities.machines.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import team.reborn.energy.api.EnergyStorage;

import static mopsy.productions.nucleartech.Main.modid;

@SuppressWarnings("UnstableApiUsage")
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

    public static void regBlockEntities() {
        CRUSHER = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modid, "crusher"),
                FabricBlockEntityTypeBuilder.create(CrusherEntity::new, ModdedBlocks.Blocks.get("crusher")).build(null));

        TANK_MK1 = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modid, "tank_mk1"),
                FabricBlockEntityTypeBuilder.create(TankEntity_MK1::new, ModdedBlocks.Blocks.get("tank_mk1")).build(null));

        PRESS = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modid, "press"),
                FabricBlockEntityTypeBuilder.create(PressEntity::new, ModdedBlocks.Blocks.get("press")).build(null));

        ELECTROLYZER = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modid, "electrolyzer"),
                FabricBlockEntityTypeBuilder.create(ElectrolyzerEntity::new, ModdedBlocks.Blocks.get("electrolyzer")).build(null));

        CENTRIFUGE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modid, "centrifuge"),
                FabricBlockEntityTypeBuilder.create(CentrifugeEntity::new, ModdedBlocks.Blocks.get("centrifuge")).build(null));

        AIR_SEPARATOR = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modid, "air_separator"),
                FabricBlockEntityTypeBuilder.create(AirSeparatorEntity::new, ModdedBlocks.Blocks.get("air_separator")).build(null));

        FURNACE_GENERATOR = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modid, "furnace_generator"),
                FabricBlockEntityTypeBuilder.create(FurnaceGeneratorEntity::new, ModdedBlocks.Blocks.get("furnace_generator")).build(null));

        STEAM_TURBINE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modid, "steam_turbine"),
                FabricBlockEntityTypeBuilder.create(SteamTurbineEntity::new, ModdedBlocks.Blocks.get("steam_turbine")).build(null));

        SMALL_REACTOR = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modid, "small_reactor"),
                FabricBlockEntityTypeBuilder.create(SmallReactorEntity::new, ModdedBlocks.Blocks.get("small_reactor_hatches")).build(null));

        //Power
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, CRUSHER);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, PRESS);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, AIR_SEPARATOR);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, ELECTROLYZER);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, CENTRIFUGE);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, FURNACE_GENERATOR);
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, STEAM_TURBINE);
        //Fluids
        FluidStorage.SIDED.registerForBlockEntity(((entity, direction) -> entity.fluidStorage), TANK_MK1);
        FluidStorage.SIDED.registerForBlockEntity((ElectrolyzerEntity::getFluidStorageFromDirection), ELECTROLYZER);
        FluidStorage.SIDED.registerForBlockEntity((SmallReactorEntity::getFluidStorageFromDirection), SMALL_REACTOR);
        FluidStorage.SIDED.registerForBlockEntity((SteamTurbineEntity::getFluidStorageFromDirection), STEAM_TURBINE);
    }
}
