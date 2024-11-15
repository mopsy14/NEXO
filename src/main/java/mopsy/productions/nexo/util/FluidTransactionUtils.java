package mopsy.productions.nexo.util;

import mopsy.productions.nexo.interfaces.IItemFluidData;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;


public class FluidTransactionUtils {
    public static boolean tryImportFluid(Inventory inventory, int inputIndex, int outputIndex, SingleVariantStorage<FluidVariant> fluidStorage) {
        ItemStack inputStack = inventory.getStack(inputIndex);

        if (fluidStorage.amount < fluidStorage.getCapacity() && FluidDataUtils.getFluidAmount(inputStack) > 0 && inputStack.getItem() instanceof IItemFluidData) {
            if (fluidStorage.variant.isBlank()) {
                fluidStorage.variant = FluidDataUtils.getFluidType(inputStack);
                long insertAmount = Math.min(fluidStorage.getCapacity(), FluidDataUtils.getFluidAmount(inputStack));
                FluidDataUtils.setFluidAmount(inputStack, FluidDataUtils.getFluidAmount(inputStack) - insertAmount);
                fluidStorage.amount += insertAmount;
                moveIToO(inventory, inputIndex, outputIndex);
                return true;
            }
            if (fluidStorage.variant.equals(FluidDataUtils.getFluidType(inputStack))) {
                long insertAmount = Math.min(fluidStorage.getCapacity() - fluidStorage.amount, FluidDataUtils.getFluidAmount(inputStack));
                FluidDataUtils.setFluidAmount(inputStack, FluidDataUtils.getFluidAmount(inputStack) - insertAmount);
                fluidStorage.amount += insertAmount;
                moveIToO(inventory, inputIndex, outputIndex);
                return true;
            }
        }
        return false;
    }
    public static boolean tryExportFluid(Inventory inventory, int inputIndex, int outputIndex, SingleVariantStorage<FluidVariant> fluidStorage) {
        ItemStack inputStack = inventory.getStack(inputIndex);

        if (fluidStorage.amount > 0 && inputStack.getItem() instanceof IItemFluidData) {
            if (FluidDataUtils.getFluidType(inputStack).isBlank()) {
                FluidDataUtils.setFluidType(inputStack, fluidStorage.variant);
                long insertAmount = Math.min((((IItemFluidData) inputStack.getItem()).getMaxCapacity()), fluidStorage.amount);
                fluidStorage.amount -= insertAmount;
                if (fluidStorage.amount == 0)
                    fluidStorage.variant = FluidVariant.blank();
                FluidDataUtils.setFluidAmount(inputStack, insertAmount);
                moveIToO(inventory, inputIndex, outputIndex);
                return true;
            }
            if (FluidDataUtils.getFluidType(inputStack).equals(fluidStorage.variant)) {
                long insertAmount = Math.min((((IItemFluidData) inputStack.getItem()).getMaxCapacity() - FluidDataUtils.getFluidAmount(inputStack)), fluidStorage.amount);
                fluidStorage.amount -= insertAmount;
                if (fluidStorage.amount == 0)
                    fluidStorage.variant = FluidVariant.blank();
                FluidDataUtils.setFluidAmount(inputStack, FluidDataUtils.getFluidAmount(inputStack) + insertAmount);
                moveIToO(inventory, inputIndex, outputIndex);
                return true;
            }
        }
        return false;
    }
    private static void moveIToO(Inventory inv, int inputSlot, int outputSlot){
        inv.setStack(outputSlot, inv.getStack(inputSlot).copy());
        inv.getStack(inputSlot).setCount(0);
    }
    public static boolean doFabricImportTransaction(Inventory inventory, int inputIndex, int outputIndex, SingleVariantStorage<FluidVariant> fluidStorage){
        try(Transaction transaction = Transaction.openOuter()) {
            if (canTransfer(inventory, inputIndex, outputIndex)) {
                long moved = StorageUtil.move(
                        FluidUtils.getItemFluidStorage(inventory, inputIndex, outputIndex),
                        fluidStorage,
                        predicate -> true,
                        Long.MAX_VALUE,
                        transaction
                );
                if (moved > 0) {
                    transaction.commit();
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean doFabricExportTransaction(Inventory inventory, int inputIndex, int outputIndex, SingleVariantStorage<FluidVariant> fluidStorage){
        try(Transaction transaction = Transaction.openOuter()){
            if( canTransfer(inventory, inputIndex, outputIndex)) {
                long moved = StorageUtil.move(
                        fluidStorage,
                        FluidUtils.getItemFluidStorage(inventory, inputIndex, outputIndex),
                        fv -> true,
                        Long.MAX_VALUE,
                        transaction
                );
                if (moved > 0) {
                    transaction.commit();
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean canTransfer(Inventory inv, int inputIndex, int outputIndex){
        if(inv.getStack(inputIndex).isEmpty())
            return false;
        return inv.getStack(outputIndex).getCount() < inv.getStack(outputIndex).getMaxCount();
    }
    public static long move(StorageView<FluidVariant> from, Storage<FluidVariant> to, long maxAmount, Transaction transaction){
        long totalMoved = 0;
        try(Transaction methodTrans = transaction.openNested()) {
            if (from.isResourceBlank()) return 0;
            FluidVariant resource = from.getResource();
            long maxExtracted;

            // check how much can be extracted
            try (Transaction extractionTestTransaction = methodTrans.openNested()) {
                maxExtracted = from.extract(resource, StorageUtil.simulateInsert(to,resource,maxAmount,extractionTestTransaction), extractionTestTransaction);
                extractionTestTransaction.abort();
            }

            try (Transaction transferTransaction = methodTrans.openNested()) {
                // check how much can be inserted
                long accepted = to.insert(resource, maxExtracted, transferTransaction);

                // extract it, or rollback if the amounts don't match
                if (from.extract(resource, accepted, transferTransaction) == accepted) {
                    totalMoved += accepted;
                    transferTransaction.commit();
                }
            }
            methodTrans.commit();
        }
        return totalMoved;
    }
}
