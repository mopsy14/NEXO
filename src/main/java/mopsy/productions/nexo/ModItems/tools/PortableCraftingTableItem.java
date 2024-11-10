package mopsy.productions.nexo.ModItems.tools;

import mopsy.productions.nexo.interfaces.IModID;
import mopsy.productions.nexo.screen.PortableCraftingTableHandler;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import static mopsy.productions.nexo.Main.CREATIVE_TOOLS_TAB_KEY;

public class PortableCraftingTableItem extends Item implements IModID  {
    public PortableCraftingTableItem() {
        super(new Settings().maxCount(1));
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TOOLS_TAB_KEY).register(entries -> entries.add(this));
    }

    @Override
    public String getID() {
        return "portable_crafting_table";
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient){
            user.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            PortableCraftingTableHandler.openTable(user);
            return ActionResult.SUCCESS;
        }
        return super.use(world, user, hand);
    }
}
