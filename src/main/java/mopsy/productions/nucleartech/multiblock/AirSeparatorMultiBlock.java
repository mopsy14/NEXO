package mopsy.productions.nucleartech.multiblock;

import mopsy.productions.nucleartech.interfaces.IMultiBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;

import static mopsy.productions.nucleartech.Main.modid;

public class AirSeparatorMultiBlock extends MultiBlock implements IMultiBlock {
    public static AirSeparatorMultiBlock INSTANCE = new AirSeparatorMultiBlock();
    public static MultiBlockRequirement[] requirements = {
            new MultiBlockRequirement(new Identifier(modid, "cooler"),1),
            new MultiBlockRequirement(new Identifier(modid, "air_pump"),1)
    };

    @Override
    public boolean hasCompleteMB(BlockEntity controller) {
        return MBUtils.hasCompleteMultiBlock(controller, INSTANCE);
    }

    @Override
    public MultiBlockRequirement[] getRequirements() {
        return requirements;
    }
}
