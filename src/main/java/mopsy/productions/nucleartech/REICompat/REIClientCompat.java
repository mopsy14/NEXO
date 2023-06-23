package mopsy.productions.nucleartech.REICompat;

import dev.architectury.event.CompoundEventResult;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.common.fluid.FluidSupportProvider;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mopsy.productions.nucleartech.interfaces.IBucketFluidStorage;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.stream.Stream;

public class REIClientCompat implements REIClientPlugin {
    @Override
    public void registerFluidSupport(FluidSupportProvider support) {
        System.out.println("Registering fluid support");
        support.register(stack -> {
            ItemStack itemStack = stack.getValue();
            Item item = itemStack.getItem();
            System.out.println("Checking item: "+item);
            if(item instanceof IBucketFluidStorage bucketFluidStorage) {
                Fluid fluid = bucketFluidStorage.getFluid();
                System.out.println("Registering: "+fluid);
                if (fluid != null)
                    return CompoundEventResult.interruptTrue(Stream.of(EntryStacks.of(fluid)));
                System.out.println("fluid==null");
            }
            return CompoundEventResult.pass();
        });
    }
}
