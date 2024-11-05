package mopsy.productions.nexo.ModItems.tools;

import mopsy.productions.nexo.interfaces.IModID;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ClickType;

import static mopsy.productions.nexo.Main.CREATIVE_TOOLS_TAB_KEY;
import static mopsy.productions.nexo.mechanics.radiation.RadiationHelper.sendRadiationPerSecondUpdatePackage;
import static mopsy.productions.nexo.mechanics.radiation.RadiationHelper.sendRadiationUpdatePackage;

public class GeigerCounterItem extends Item implements IModID  {
    public GeigerCounterItem() {
        super(new Settings().maxCount(1));
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TOOLS_TAB_KEY).register(entries -> entries.add(this));
        INSTANCE = this;
    }
    public static GeigerCounterItem INSTANCE;

    @Override
    public String getID() {
        return "geiger_counter";
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if(!player.getWorld().isClient) {
            sendRadiationUpdatePackage((ServerPlayerEntity) player);
            sendRadiationPerSecondUpdatePackage((ServerPlayerEntity) player);
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }
}
