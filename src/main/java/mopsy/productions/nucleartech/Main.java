package mopsy.productions.nucleartech;

import mopsy.productions.nucleartech.registry.Blocks;
import mopsy.productions.nucleartech.registry.Items;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static mopsy.productions.nucleartech.registry.Items.URANIUM_INGOT;

public class Main implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("nucleartech");
	public static final String modid = "nucleartech";
	public static final ItemGroup CREATIVE_TAB = FabricItemGroupBuilder.build(
		new Identifier(modid, "nucleartech"), () -> new ItemStack(URANIUM_INGOT));

	@Override
	public void onInitialize() {
		LOGGER.info("Nuclear Tech starting");
		Items.regItems();
		Blocks.regBlocks();

	}
}
