package mopsy.productions.nucleartech.ModItems;

import mopsy.productions.nucleartech.interfaces.IItemRadiation;
import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

import static mopsy.productions.nucleartech.Main.CREATIVE_BLOCK_TAB;

public class UraniumBlockItem extends BlockItem implements IModID, IItemRadiation {

    @Override public float getRadiation() {return 9;}

    @Override public String getID() {return "uranium_block";}

    public UraniumBlockItem(Block block) {
        super(block, new FabricItemSettings().group(CREATIVE_BLOCK_TAB));
    }
}
