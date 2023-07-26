package mopsy.productions.nexo;

import mopsy.productions.nexo.CableNetworks.CNetworkManager;
import mopsy.productions.nexo.mechanics.radiation.RadiationEvents;
import mopsy.productions.nexo.registry.*;
import mopsy.productions.nexo.screen.ScreenHandlers;
import mopsy.productions.nexo.world.feature.ModConfiguredFeatures;
import mopsy.productions.nexo.world.gen.ModOreGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static mopsy.productions.nexo.networking.PacketManager.registerC2SPackets;

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
		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
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

		CNetworkManager.regEvents();
	}
}
