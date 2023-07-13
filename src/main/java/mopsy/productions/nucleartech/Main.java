package mopsy.productions.nucleartech;

import mopsy.productions.nucleartech.mechanics.radiation.RadiationEvents;
import mopsy.productions.nucleartech.registry.*;
import mopsy.productions.nucleartech.screen.ScreenHandlers;
import mopsy.productions.nucleartech.world.feature.ModConfiguredFeatures;
import mopsy.productions.nucleartech.world.gen.ModOreGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static mopsy.productions.nucleartech.networking.PacketManager.registerC2SPackets;

public class Main implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("NEXO");
	public static final String modid = "nexo";
	public static final ItemGroup CREATIVE_TAB = FabricItemGroupBuilder.build(
			new Identifier(modid, "nexo_items"), () -> new ItemStack(ModdedItems.Items.get("uranium_ingot")));
	public static final ItemGroup CREATIVE_BLOCK_TAB = FabricItemGroupBuilder.build(
			new Identifier(modid, "nexo_blocks"), () -> new ItemStack(ModdedBlocks.BlockItems.get("uranium_block")));

	public static MinecraftServer server = null;

	@Override
	public void onInitialize() {
		LOGGER.info("NEXO starting");
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			Main.server = server;
		});

		ModdedItems.regItems();
		ModdedBlocks.regBlocks();
		ModConfiguredFeatures.regConfiguredFeatures();
		ModOreGeneration.generateOres();

		RadiationEvents.addEvents();

		registerC2SPackets();

		ScreenHandlers.regScreenHandlers();
		ModdedRecipes.regRecipes();

		ModdedFluids.regFluids();

		ModdedBlockEntities.regBlockEntities();
	}
}
