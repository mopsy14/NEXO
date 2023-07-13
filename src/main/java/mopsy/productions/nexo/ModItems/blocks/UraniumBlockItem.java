package mopsy.productions.nexo.ModItems.blocks;

import mopsy.productions.nexo.interfaces.IItemRadiation;
import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

import static mopsy.productions.nexo.Main.CREATIVE_BLOCK_TAB;

public class UraniumBlockItem extends BlockItem implements IModID, IItemRadiation {

    @Override public float getRadiation() {return 9;}

    @Override
    public float getHeat() {
        return 0;
    }

    @Override
    public boolean hasHeat() {
        return false;
    }

    @Override public String getID() {return "uranium_block";}

    public UraniumBlockItem(Block block) {
        super(block, new FabricItemSettings().group(CREATIVE_BLOCK_TAB));
    }
}
