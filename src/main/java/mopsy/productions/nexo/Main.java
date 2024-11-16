package mopsy.productions.nexo;

import mopsy.productions.nexo.mechanics.radiation.RadiationEvents;
import mopsy.productions.nexo.registry.*;
import mopsy.productions.nexo.screen.ScreenHandlers;
import mopsy.productions.nexo.world.gen.ModOreGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static mopsy.productions.nexo.networking.PacketManager.registerC2SPackets;

public class Main implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("NEXO");
	public static final String modid = "nexo";
	public static final ItemGroup CREATIVE_TAB = Registry.register(Registries.ITEM_GROUP, Identifier.of(modid,"nexo_items"),
			FabricItemGroup.builder()
					.displayName(Text.translatable("itemGroup.nexo.nexo_items"))
					.icon(() -> new ItemStack(ModdedItems.Items.get("uranium_ingot")))
					.build()
	);
	public static final ItemGroup CREATIVE_TOOLS_TAB = Registry.register(Registries.ITEM_GROUP, Identifier.of(modid,"nexo_tools"),
			FabricItemGroup.builder()
					.displayName(Text.translatable("itemGroup.nexo.nexo_tools"))
					.icon(() -> new ItemStack(ModdedItems.Items.get("titanium_pickaxe")))
					.build()
	);
	public static final ItemGroup CREATIVE_BLOCK_TAB = Registry.register(Registries.ITEM_GROUP, Identifier.of(modid,"nexo_blocks"),
			FabricItemGroup.builder()
					.displayName(Text.translatable("itemGroup.nexo.nexo_blocks"))
					.icon(() -> new ItemStack(ModdedBlocks.BlockItems.get("uranium_block")))
					.build()
	);
	public static final ItemGroup CREATIVE_FLUIDS_TAB = Registry.register(Registries.ITEM_GROUP, Identifier.of(modid,"nexo_fluids"),
			FabricItemGroup.builder()
					.displayName(Text.translatable("itemGroup.nexo.nexo_fluids"))
					.icon(() -> new ItemStack(ModdedBlocks.BlockItems.get("tank_mk1")))
					.build()
	);

	public static final RegistryKey<ItemGroup> CREATIVE_TAB_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(modid,"nexo_items"));
	public static final RegistryKey<ItemGroup> CREATIVE_TOOLS_TAB_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(modid,"nexo_tools"));
	public static final RegistryKey<ItemGroup> CREATIVE_BLOCK_TAB_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(modid,"nexo_blocks"));
	public static final RegistryKey<ItemGroup> CREATIVE_FLUIDS_TAB_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(modid,"nexo_fluids"));

	public static MinecraftServer server = null;

	@Override
	public void onInitialize() {
		LOGGER.info("NEXO starting");
		ServerLifecycleEvents.SERVER_STARTING.register(Identifier.of(modid,"set_server") ,server -> {
			Main.server = server;
		});

		ModdedDataComponentTypes.init();

		ModdedItems.regItems();
		ModdedBlocks.regBlocks();
		ModOreGeneration.generateOres();


		RadiationEvents.addEvents();

		registerC2SPackets();

		ScreenHandlers.regScreenHandlers();
		ModdedRecipes.regRecipes();

		ModdedFluids.regFluids();

		ModdedBlockEntities.regBlockEntities();
		LOGGER.info("NEXO successfully started");
	}
}
