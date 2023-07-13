package mopsy.productions.nexo.interfaces;

import mopsy.productions.nexo.multiblock.MultiBlockRequirement;
import net.minecraft.block.entity.BlockEntity;

public interface IMultiBlock {
    boolean hasCompleteMB(BlockEntity controller);
    MultiBlockRequirement[] getRequirements();
}
