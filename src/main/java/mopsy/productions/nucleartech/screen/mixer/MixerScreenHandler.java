package mopsy.productions.nucleartech.screen.mixer;

import mopsy.productions.nucleartech.screen.ScreenHandlers;
import mopsy.productions.nucleartech.util.slots.MixerSlot;
import mopsy.productions.nucleartech.util.slots.ReturnSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class MixerScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final BlockPos blockPos;
    public final PropertyDelegate delegate;

    public MixerScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf){
        this(syncId, playerInventory, new SimpleInventory(12), new ArrayPropertyDelegate(3), buf.readBlockPos());
    }
    public MixerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate, BlockPos blockPos) {
        super(ScreenHandlers.MIXER, syncId);
        checkSize(inventory, 12);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.delegate = delegate;
        this.blockPos = blockPos;

        //FluidSlot1
        this.addSlot(new Slot(inventory, 0,24,19));
        this.addSlot(new ReturnSlot(inventory, 1,24,50));
        //FluidSlot2
        this.addSlot(new Slot(inventory, 2,62,19));
        this.addSlot(new ReturnSlot(inventory, 3,62,50));
        //FluidSlot3
        this.addSlot(new Slot(inventory, 4,100,19));
        this.addSlot(new ReturnSlot(inventory, 5,100,50));
        //rods
        this.addSlot(new MixerSlot(inventory, 8,119,19, b->delegate.get(0)==0));
        this.addSlot(new MixerSlot(inventory, 9,138,19, b->delegate.get(0)==0));
        this.addSlot(new MixerSlot(inventory, 10,119,38, b->delegate.get(0)==0));
        this.addSlot(new MixerSlot(inventory, 11,138,38, b->delegate.get(0)==0));

        addPlayerInventory(playerInventory);
        addHotbar(playerInventory);
        addProperties(delegate);
    }
    public BlockPos getBlockPos(){
        return blockPos;
    }
    public boolean isActive(){
        return delegate.get(0)>0;
    }

    public int getScaledProgress(){
        int progress = this.delegate.get(0);
        int max = this.delegate.get(1);
        int barSize = 37;

        return max!=0 && progress!=0 ? progress*barSize/max : 0;
    }
    public int getSliderPos(){
        int progress = getHeat()-1000;
        int max = 1000;
        int barSize = 100;

        return progress!=0 ? progress*barSize/max : 0;
    }
    public int getHeatFromSliderPos(int sliderPos){
        int max = 1000;
        int barSize = 100;

        return sliderPos > 0 ? ((sliderPos*max)/barSize)-1000: -1000;
    }
    public int getHeat(){
        return this.delegate.get(2);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
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
