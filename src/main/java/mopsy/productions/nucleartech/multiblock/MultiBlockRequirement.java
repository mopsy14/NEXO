package mopsy.productions.nucleartech.multiblock;

import mopsy.productions.nucleartech.interfaces.IModID;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public class MultiBlockRequirement {
    final Identifier blockID;
    final int amount;

    public MultiBlockRequirement(Identifier blockID, int amount){
        this.blockID = blockID;
        this.amount = amount;
    }
    public boolean typeMatches(Block block){
        if(block instanceof IModID){
            return ((IModID)block).getID().equals(blockID.getPath());
        }
        return false;
    }
}
