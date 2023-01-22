package mopsy.productions.nucleartech;

import mopsy.productions.nucleartech.registry.Blocks;
import mopsy.productions.nucleartech.registry.Items;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("nucleartech");
	public static final String modid = "nucleartech";
	public static final ItemGroup CREATIVE_TAB = FabricItemGroupBuilder.build(

	);

	@Override
	public void onInitialize() {
		LOGGER.info("Nuclear Tech starting");
		Items.regItems();
		Blocks.regBlocks();

	}
}
