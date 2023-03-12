package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModBlocks.blocks.compressed.UraniumBlock;
import mopsy.productions.nucleartech.ModBlocks.blocks.machines.*;
import mopsy.productions.nucleartech.ModBlocks.blocks.multiblocks.ConduitBlock;
import mopsy.productions.nucleartech.ModBlocks.blocks.multiblocks.SmallReactorBlock;
import mopsy.productions.nucleartech.ModBlocks.blocks.multiblocks.airSeparator.AirPumpBlock;
import mopsy.productions.nucleartech.ModBlocks.blocks.multiblocks.airSeparator.CoolerBlock;
import mopsy.productions.nucleartech.ModBlocks.blocks.multiblocks.smallReactor.ControlRodsBlock;
import mopsy.productions.nucleartech.ModBlocks.blocks.multiblocks.smallReactor.SmallReactorHatchesBlock;
import mopsy.productions.nucleartech.ModBlocks.blocks.ores.*;
import mopsy.productions.nucleartech.ModItems.blocks.Tank_MK1Item;
import mopsy.productions.nucleartech.ModItems.blocks.UraniumBlockItem;
import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

import static mopsy.productions.nucleartech.Main.*;

public class ModdedBlocks {
    public static final HashMap<String, BlockItem> BlockItems = new HashMap<>();
    public static final HashMap<String, Block> Blocks = new HashMap<>();

    public static void regBlocks(){
        //ores
        regBlock(new UraniumOreBlock());
        regBlock(new DeepslateUraniumOreBlock());
        regBlock(new FluoriteOreBlock());
        regBlock(new TitaniumOreBlock());
        regBlock(new DeepslateTitaniumOreBlock());
        regBlock(new SulfurOreBlock());
        //compressed
        regBlock(new UraniumBlock());
        //machines
        regBlock(new CrusherBlock());
        regBlock(new Tank_MK1());
        regBlock(new PressBlock());
        regBlock(new ElectrolyzerBlock());
        regBlock(new CentrifugeBlock());
        //generators
        regBlock(new FurnaceGeneratorBlock());
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
            };
            BlockItems.put(name, bi);
        }else
            LOGGER.error("Block doesn't implement IModID!");
    }
}
