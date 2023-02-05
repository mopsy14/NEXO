package mopsy.productions.nucleartech.REI;

import dev.architectury.event.CompoundEventResult;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.common.fluid.FluidSupportProvider;
import me.shedaniel.rei.api.common.util.EntryStacks;
import mopsy.productions.nucleartech.registry.ModdedFluids;

import java.util.stream.Stream;

public class REIPluginServer implements REIClientPlugin {
    @Override
    public void registerFluidSupport(FluidSupportProvider support) {
        support.register(stack ->{
            return CompoundEventResult.interruptTrue(Stream.of(EntryStacks.of(ModdedFluids.NITROGEN)));
        });
    }
}
