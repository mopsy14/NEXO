package mopsy.productions.nexo.ModItems.tools;

import mopsy.productions.nexo.interfaces.IItemRadiation;
import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static mopsy.productions.nexo.Main.CREATIVE_TOOLS_TAB;

public class DebugItem extends Item implements IModID, IItemRadiation {
    @Override
    public String getID() {
        return "debug_item";
    }
    public DebugItem() {
        super(new FabricItemSettings().group(CREATIVE_TOOLS_TAB).rarity(Rarity.EPIC));
        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(DEBUG_ITEM));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient&&hand==Hand.MAIN_HAND){
            user.sendMessage(Text.of("This is a debug item"));
        }

        return super.use(world, user, hand);
    }

    @Override
    public float getRadiation() {
        return -10;
    }

    @Override
    public float getHeat() {
        return 0;
    }

    @Override
    public boolean hasHeat() {
        return false;
    }
}
