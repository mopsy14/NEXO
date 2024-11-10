package mopsy.productions.nexo.screen.crusher;

import mopsy.productions.nexo.screen.DefaultSHPayload;
import mopsy.productions.nexo.screen.ScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class CrusherScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate delegate;
    private final BlockPos blockPos;

    public CrusherScreenHandler(int syncId, PlayerInventory playerInventory, DefaultSHPayload payload){
        this(syncId, playerInventory, new SimpleInventory(2), new ArrayPropertyDelegate(2), payload.blockPos());
    }
    public CrusherScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate, BlockPos blockPos) {
        super(ScreenHandlers.CRUSHER, syncId);
        checkSize(inventory, 2);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.delegate = delegate;
        this.blockPos = blockPos;

        this.addSlot(new Slot(inventory, 0,48,35));
        this.addSlot(new Slot(inventory, 1,116,35){
            @Override
            public boolean canInsert(ItemStack itemStack){
                return false;
            }
        });

        addPlayerInventory(playerInventory);
        addHotbar(playerInventory);

        addProperties(delegate);
    }

    public boolean isCrafting(){
        return delegate.get(0)>0;
    }

    public int getScaledProgress(){
        int progress = this.delegate.get(0);
        int max = this.delegate.get(1);
        int barSize = 26;

        return max!=0 && progress!=0 ? progress*barSize/max : 0;
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
