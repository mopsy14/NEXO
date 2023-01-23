package mopsy.productions.nucleartech;

import mopsy.productions.nucleartech.mechanics.Radiation;
import mopsy.productions.nucleartech.registry.Blocks;
import mopsy.productions.nucleartech.registry.Items;
import mopsy.productions.nucleartech.world.feature.ModConfiguredFeatures;
import mopsy.productions.nucleartech.world.gen.ModOreGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("nucleartech");
	public static final String modid = "nucleartech";
	public static final ItemGroup CREATIVE_TAB = FabricItemGroupBuilder.build(
			new Identifier(modid, "nucleartech_items"), () -> new ItemStack(Items.Items.get("uranium_ingot")));
	public static final ItemGroup CREATIVE_BLOCK_TAB = FabricItemGroupBuilder.build(
			new Identifier(modid, "nucleartech_blocks"), () -> new ItemStack(Blocks.BlockItems.get("uranium_block")));

	@Override
	public void onInitialize() {
		LOGGER.info("Nuclear Tech starting");
		Items.regItems();
		Blocks.regBlocks();
		ModConfiguredFeatures.regConfiguredFeatures();
		ModOreGeneration.generateOres();

		Radiation.addEvents();
	}
}
