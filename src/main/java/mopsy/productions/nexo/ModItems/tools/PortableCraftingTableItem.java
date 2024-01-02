package mopsy.productions.nexo.ModItems.tools;

import mopsy.productions.nexo.interfaces.IModID;
import mopsy.productions.nexo.screen.PortableCraftingTableHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static mopsy.productions.nexo.Main.CREATIVE_TOOLS_TAB;

public class PortableCraftingTableItem extends Item implements IModID  {
    public PortableCraftingTableItem() {
        super(new FabricItemSettings().group(CREATIVE_TOOLS_TAB).maxCount(1));
    }

    @Override
    public String getID() {
        return "portable_crafting_table";
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient){
            user.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            PortableCraftingTableHandler.openTable(user);
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return super.use(world, user, hand);
    }
}
