package mopsy.productions.nucleartech.interfaces;

import mopsy.productions.nucleartech.multiblock.MultiBlockRequirement;
import net.minecraft.block.entity.BlockEntity;

public interface IMultiBlock {
    boolean hasCompleteMB(BlockEntity controller);
    MultiBlockRequirement[] getRequirements();
}
