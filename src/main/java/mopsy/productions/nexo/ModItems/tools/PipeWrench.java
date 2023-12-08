package mopsy.productions.nexo.ModItems.tools;

import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

import static mopsy.productions.nexo.Main.CREATIVE_TAB;

public class PipeWrench extends Item implements IModID {


    @Override
    public String getID() {
        return "pipe_wrench";
    }
    public PipeWrench() {
        super(new FabricItemSettings().group(CREATIVE_TAB).maxCount(1));
    }

}