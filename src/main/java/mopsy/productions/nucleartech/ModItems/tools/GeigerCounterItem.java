package mopsy.productions.nucleartech.ModItems.tools;

import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ClickType;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;
import static mopsy.productions.nucleartech.mechanics.radiation.RadiationHelper.sendRadiationPerSecondUpdatePackage;
import static mopsy.productions.nucleartech.mechanics.radiation.RadiationHelper.sendRadiationUpdatePackage;

public class GeigerCounterItem extends Item implements IModID  {
    public GeigerCounterItem() {
        super(new FabricItemSettings().group(CREATIVE_TAB).maxCount(1));
    }

    @Override
    public String getID() {
        return "geiger_counter";
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if(!player.world.isClient) {
            sendRadiationUpdatePackage((ServerPlayerEntity) player);
            sendRadiationPerSecondUpdatePackage((ServerPlayerEntity) player);
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }
}
