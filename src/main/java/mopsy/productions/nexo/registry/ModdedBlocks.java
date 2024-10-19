package mopsy.productions.nexo.registry;

import mopsy.productions.nexo.ModBlocks.blocks.InsulatedCopperCableBlock;
import mopsy.productions.nexo.ModBlocks.blocks.compressed.UraniumBlock;
import mopsy.productions.nexo.ModBlocks.blocks.energyStorage.BatteryMK1Block;
import mopsy.productions.nexo.ModBlocks.blocks.machines.*;
import mopsy.productions.nexo.ModBlocks.blocks.multiblocks.ConduitBlock;
import mopsy.productions.nexo.ModBlocks.blocks.multiblocks.SmallReactorHatchesBlock;
import mopsy.productions.nexo.ModBlocks.blocks.multiblocks.airSeparator.AirPumpBlock;
import mopsy.productions.nexo.ModBlocks.blocks.multiblocks.airSeparator.CoolerBlock;
import mopsy.productions.nexo.ModBlocks.blocks.multiblocks.deconShower.DeconShowerBlock;
import mopsy.productions.nexo.ModBlocks.blocks.multiblocks.deconShower.DeconShowerDrainBlock;
import mopsy.productions.nexo.ModBlocks.blocks.multiblocks.smallReactor.ControlRodsBlock;
import mopsy.productions.nexo.ModBlocks.blocks.multiblocks.smallReactor.SmallReactorBlock;
import mopsy.productions.nexo.ModBlocks.blocks.ores.*;
import mopsy.productions.nexo.ModBlocks.blocks.transport.FluidPipe_MK1Block;
import mopsy.productions.nexo.ModItems.blocks.BatteryMK1Item;
import mopsy.productions.nexo.ModItems.blocks.Tank_MK1Item;
import mopsy.productions.nexo.ModItems.blocks.UraniumBlockItem;
import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

import static mopsy.productions.nexo.Main.*;
@SuppressWarnings("UnstableApiUsage")
public class ModdedBlocks {
    public static final HashMap<String, BlockItem> BlockItems = new HashMap<>();
    public static final HashMap<String, Block> Blocks = new HashMap<>();

    public static void regBlocks(){
        LOGGER.info("Registering blocks");
        //ores
        regBlock(new UraniumOreBlock());
        regBlock(new DeepslateUraniumOreBlock());
        regBlock(new FluoriteOreBlock());
        regBlock(new TitaniumOreBlock());
        regBlock(new DeepslateTitaniumOreBlock());
        regBlock(new SulfurOreBlock());
        regBlock(new LeadOreBlock());
        regBlock(new DeepslateLeadOreBlock());
        regBlock(new CarobbiiteOreBlock());
        regBlock(new SaltOreBlock());
        regBlock(new VanadiumOreBlock());
        regBlock(new DeepslateVanadiumOreBlock());
        regBlock(new NickelOreBlock());
        regBlock(new DeepslateNickelOreBlock());
        regBlock(new DeepslateOilOreBlock());
        //compressed
        regBlock(new UraniumBlock());
        //cables
        regBlock(new InsulatedCopperCableBlock());
        //pipes
        regBlock(new FluidPipe_MK1Block());
        //machines
        regBlock(new CrusherBlock());
        regBlock(new Tank_MK1());
        regBlock(new PressBlock());
        regBlock(new ElectrolyzerBlock());
        regBlock(new CentrifugeBlock());
        regBlock(new AmmoniaSynthesizerBlock());
        regBlock(new MixerBlock());
        regBlock(new DeconShowerBlock());
        regBlock(new DeconShowerDrainBlock());
        regBlock(new ElectricFurnaceBlock());
        //energy storage
        regBlock(new BatteryMK1Block());
        //generators
        regBlock(new FurnaceGeneratorBlock());
        regBlock(new SteamTurbineBlock());
        //multi block controllers
        regBlock(new AirSeparatorBlock());
        regBlock(new SmallReactorBlock());
        //multi block blocks
        regBlock(new ConduitBlock());
        regBlock(new AirPumpBlock());
        regBlock(new CoolerBlock());

        regBlock(new SmallReactorHatchesBlock());
        regBlock(new ControlRodsBlock());
    }

    private static void regBlock(Block block){
        if(block instanceof IModID) {
            String name = ((IModID)block).getID();
            Block b = Registry.register(Registry.BLOCK, new Identifier(modid, name), block);
            Blocks.put(name, b);
            BlockItem bi = switch (name) {
                default -> Registry.register(Registry.ITEM, new Identifier(modid, name), new BlockItem(block, new FabricItemSettings().group(CREATIVE_BLOCK_TAB)));
                case "uranium_block" -> Registry.register(Registry.ITEM, new Identifier(modid, name), new UraniumBlockItem(block));
                case "tank_mk1" -> Registry.register(Registry.ITEM, new Identifier(modid, name), new Tank_MK1Item(block));
                case "battery_mk1" -> {
                    BlockItem item = Registry.register(Registry.ITEM, new Identifier(modid, name), new BatteryMK1Item(block));
                    //EnergyStorage.ITEM.reg(item);
                    yield item;
                }
            };
            BlockItems.put(name, bi);
        }else
            LOGGER.error("Block doesn't implement IModID!");
    }
}
