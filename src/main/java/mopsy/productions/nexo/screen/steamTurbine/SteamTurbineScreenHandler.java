package mopsy.productions.nexo.screen.steamTurbine;

import mopsy.productions.nexo.screen.ScreenHandlers;
import mopsy.productions.nexo.util.slots.ReturnSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class SteamTurbineScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final BlockPos blockPos;

    public SteamTurbineScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf){
        this(syncId, playerInventory, new SimpleInventory(4), buf.readBlockPos());
    }
    public SteamTurbineScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, BlockPos blockPos) {
        super(ScreenHandlers.STEAM_TURBINE, syncId);
        checkSize(inventory, 4);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.blockPos = blockPos;

        //FluidInput
        this.addSlot(new Slot(inventory, 0,33,19));
        this.addSlot(new ReturnSlot(inventory, 1,33,50));
        //FluidOutput1
        this.addSlot(new Slot(inventory, 2,92,19));
        this.addSlot(new ReturnSlot(inventory, 3,92,50));

        addPlayerInventory(playerInventory);
        addHotbar(playerInventory);
    }
    public BlockPos getBlockPos(){
        return blockPos;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack res = ItemStack.EMPTY;

        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            res = originalStack.copy();
            if (index < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return res;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory){
        for (int i = 0; i<3; i++){
            for(int i2 = 0; i2<9; i2++){
                this.addSlot(new Slot(playerInventory, i2+i*9+9, 8+i2*18,84+i*18));
            }
        }
    }
    private void addHotbar(PlayerInventory playerInventory){
        for(int i = 0; i<9; i++){
            this.addSlot(new Slot(playerInventory, i, 8+i*18, 142));
        }
    }
}
