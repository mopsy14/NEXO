package mopsy.productions.nexo.screen;

import mopsy.productions.nexo.registry.ModdedItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;

public class PortableCraftingTableHandler extends CraftingScreenHandler {

    public PortableCraftingTableHandler(int i, PlayerInventory playerInventory, ScreenHandlerContext screenHandlerContext) {
        super(i, playerInventory, screenHandlerContext);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return player.getInventory().contains(new ItemStack(ModdedItems.Items.get("portable_crafting_table")));
    }

    public static void openTable(PlayerEntity player) {
        player.openHandledScreen(
                new SimpleNamedScreenHandlerFactory((syncId, inventory, playerEntity) ->
                        new PortableCraftingTableHandler(
                                syncId,
                                inventory,
                                ScreenHandlerContext.create(playerEntity.getWorld(), playerEntity.getBlockPos())),
                        Text.translatable("container.crafting")));
    }
}
