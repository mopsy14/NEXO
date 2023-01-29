package mopsy.productions.nucleartech.ModBlocks.entities.machines;

import mopsy.productions.nucleartech.interfaces.ImplementedInventory;
import mopsy.productions.nucleartech.recipes.CrusherRecipe;
import mopsy.productions.nucleartech.registry.ModdedBlockEntities;
import mopsy.productions.nucleartech.screen.crusher.CrusherScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.Optional;

public class CrusherEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    private int progress;
    private int maxProgress = 200;
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(1000, 10, 0) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };
    public long previousPower = 0;


    public CrusherEntity(BlockPos pos, BlockState state) {
        super(ModdedBlockEntities.CRUSHER, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index){
                    case 0: return CrusherEntity.this.progress;
                    case 1: return CrusherEntity.this.maxProgress;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0: CrusherEntity.this.progress = value; break;
                    case 1: CrusherEntity.this.maxProgress = value; break;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Crusher");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CrusherScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public void writeNbt(NbtCompound nbt){
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("crusher.progress", progress);
        nbt.putLong("crusher.power", energyStorage.amount);
    }

    @Override
    public void readNbt(NbtCompound nbt){
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("crusher.progress");
        energyStorage.amount = nbt.getLong("crusher.power");
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, CrusherEntity crusherEntity) {
        if(world.isClient)return;
        System.out.println("current power: "+crusherEntity.energyStorage.amount);

        if(hasRecipe(crusherEntity)&& crusherEntity.energyStorage.amount >= 5){
            crusherEntity.progress++;
            crusherEntity.energyStorage.amount -= 5;
            if(crusherEntity.progress >= crusherEntity.maxProgress){
                craft(crusherEntity);
            }
        }else{
            crusherEntity.progress = 0;
        }
        markDirty(world,blockPos,blockState);
        if(crusherEntity.energyStorage.amount!=crusherEntity.previousPower){

        }
    }

    private static void craft(CrusherEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for(int i = 0; i< entity.size(); i++){
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<CrusherRecipe> recipe = entity.getWorld().getRecipeManager().getFirstMatch(
                CrusherRecipe.Type.INSTANCE, inventory, entity.world);

        if(hasRecipe(entity)){
            entity.removeStack(0, 1);
            ItemStack output = recipe.get().getOutput();
            output.setCount(output.getCount() + entity.getStack(1).getCount());
            entity.setStack(1, output);
            entity.progress = 0;
        }
    }

    private static boolean hasRecipe(CrusherEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for(int i = 0; i< entity.size(); i++){
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<CrusherRecipe> match = entity.getWorld().getRecipeManager().getFirstMatch(
                CrusherRecipe.Type.INSTANCE, inventory, entity.world);

        return match.isPresent()&&canOutput(inventory, match.get().getOutput().getItem(), match.get().getOutput().getCount());
    }

    private static boolean canOutput(SimpleInventory inventory, Item outputType, int count){
        return (inventory.getStack(1).getItem()==outputType || inventory.getStack(1).isEmpty())
                &&inventory.getStack(1).getMaxCount() > inventory.getStack(1).getCount() + count;
    }


}
