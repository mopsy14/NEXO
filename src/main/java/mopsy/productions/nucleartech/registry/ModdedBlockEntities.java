package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModBlocks.entities.machines.CrusherEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import team.reborn.energy.api.EnergyStorage;

import static mopsy.productions.nucleartech.Main.modid;

public class ModdedBlockEntities {

    public static BlockEntityType<CrusherEntity> CRUSHER;

    public static void regBlockEntities() {
        CRUSHER = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(modid, "crusher"),
                FabricBlockEntityTypeBuilder.create(CrusherEntity::new, ModdedBlocks.Blocks.get("crusher")).build(null));




        //Power
        EnergyStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.energyStorage, CRUSHER);
    }
}
