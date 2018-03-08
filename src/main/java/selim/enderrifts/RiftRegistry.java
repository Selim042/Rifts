package selim.enderrifts;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import selim.enderrifts.api.RiftGenerator;
import selim.enderrifts.api.docs.DocCategory;
import selim.enderrifts.api.docs.DocEntry;
import selim.enderrifts.blocks.BlockAmethystBlock;
import selim.enderrifts.blocks.BlockAmethystOre;
import selim.enderrifts.blocks.BlockAmethystTorch;
import selim.enderrifts.blocks.BlockBarite;
import selim.enderrifts.blocks.BlockCustomFlowerPot;
import selim.enderrifts.blocks.BlockOpalBlock;
import selim.enderrifts.blocks.BlockOpalOre;
import selim.enderrifts.blocks.BlockOpaqueAir;
import selim.enderrifts.blocks.BlockRift;
import selim.enderrifts.blocks.BlockRiftFlower;
import selim.enderrifts.blocks.BlockRiftSand;
import selim.enderrifts.blocks.BlockTeleporter;
import selim.enderrifts.crafting.CrushRecipe;
import selim.enderrifts.events.handlers.AmethystTooltip;
import selim.enderrifts.events.handlers.CrushRecipeHandler;
import selim.enderrifts.events.handlers.EnderTeleport;
import selim.enderrifts.events.handlers.EyeTooltipHandler;
import selim.enderrifts.events.handlers.FogDensity;
import selim.enderrifts.events.handlers.PlayerRenderEvent;
import selim.enderrifts.events.handlers.PurpleTint;
import selim.enderrifts.events.handlers.ShaderHandler;
import selim.enderrifts.gui.GuiHandler;
import selim.enderrifts.items.ItemAmethyst;
import selim.enderrifts.items.ItemDebugItem;
import selim.enderrifts.items.ItemEnderLink;
import selim.enderrifts.items.ItemFracturedPearl;
import selim.enderrifts.items.ItemOpal;
import selim.enderrifts.items.ItemPhantomPearl;
import selim.enderrifts.items.ItemRiftAccess;
import selim.enderrifts.items.ItemRiftEye;
import selim.enderrifts.items.ItemRiftTransportNode;
import selim.enderrifts.items.ItemUniversalDye;
import selim.enderrifts.world.BiomeRift;
import selim.enderrifts.world.DimensionRift;

public class RiftRegistry {

	public static class Registries {

		public static IForgeRegistry<RiftGenerator> RIFT_GENERATORS;
		public static IForgeRegistry<CrushRecipe> CRUSH_RECIPES;

	}

	@GameRegistry.ObjectHolder("enderrifts")
	public static class RiftProviders {

		public static final RiftGenerator OVERWORLD = null;
		public static final RiftGenerator NETHER = null;
		public static final RiftGenerator END = null;

	}

	@GameRegistry.ObjectHolder("enderrifts")
	public static class Blocks {

		public static final BlockRift RIFT_TEST = null;
		public static final BlockRiftFlower RIFT_FLOWER = null;
		public static final BlockRiftSand RIFT_SAND = null;
		public static final BlockOpaqueAir OPAQUE_AIR = null;
		public static final BlockAmethystBlock AMETHYST_BLOCK = null;
		public static final BlockAmethystTorch AMETHYST_TORCH = null;
		public static final BlockAmethystOre AMETHYST_ORE = null;
		public static final BlockCustomFlowerPot FLOWER_POT = null;
		public static final BlockOpalBlock OPAL_BLOCK = null;
		public static final BlockOpalOre OPAL_ORE = null;
		public static final BlockBarite BARITE = null;
		public static final BlockTeleporter TELEPORTER = null;

		// public static final BlockFluidMatter MATTER = null;

	}

	@GameRegistry.ObjectHolder("enderrifts")
	public static class Items {

		public static final ItemBlock RIFT_FLOWER = null;
		public static final ItemBlock RIFT_SAND = null;
		public static final ItemBlock AMETHYST_BLOCK = null;
		public static final ItemBlock AMETHYST_TORCH = null;
		public static final ItemBlock AMETHYST_ORE = null;
		public static final ItemBlock OPAL_BLOCK = null;
		public static final ItemBlock OPAL_ORE = null;
		public static final ItemBlock BARITE = null;
		public static final ItemBlock TELEPORTER = null;

		public static final ItemUniversalDye UNIVERSAL_DYE = null;
		public static final ItemAmethyst AMETHYST = null;
		public static final ItemOpal OPAL = null;
		public static final ItemPhantomPearl PHANTOM_PEARL = null;
		public static final ItemRiftTransportNode RIFT_TRANSPORT_NODE = null;
		// See note in CommonProxy#registerItems as why this is disabled.
		// public static final ItemRiftAccess INTER_RIFT_ACCESS = null;
		public static final ItemEnderLink ENDER_RIFT = null;
		public static final ItemRiftAccess RIFT_ACCESS = null;
		public static final ItemRiftEye RIFT_EYE = null;
		public static final ItemFracturedPearl FRACTURED_PEARL = null;

		public static final ItemDebugItem DEBUG_ITEM = null;

	}

	public static class Fluids {

		// public static final FluidMatter MATTER = new FluidMatter();

	}

	public static BiomeRift riftBiome;

	public static void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new EnderTeleport());
		MinecraftForge.EVENT_BUS.register(new FogDensity());
		MinecraftForge.EVENT_BUS.register(new PurpleTint());
		MinecraftForge.EVENT_BUS.register(new ShaderHandler());
		MinecraftForge.EVENT_BUS.register(new AmethystTooltip());
		MinecraftForge.EVENT_BUS.register(new PlayerRenderEvent());
		if (RiftRegistry.Items.RIFT_TRANSPORT_NODE != null)
			MinecraftForge.EVENT_BUS.register(RiftRegistry.Items.RIFT_TRANSPORT_NODE);
		MinecraftForge.EVENT_BUS.register(new CrushRecipeHandler());
		// Laggy
		// MinecraftForge.EVENT_BUS.register(new WaterEffectHandler());
		MinecraftForge.EVENT_BUS.register(new EyeTooltipHandler());
	}

	public static void registerDispenserBehavior() {}

	public static void registerWorldGenerators() {}

	public static void registerGuiHandlers() {
		NetworkRegistry.INSTANCE.registerGuiHandler(EnderRifts.instance, new GuiHandler());
	}

	public static void registerDimensions() {
		DimensionRift.mainRegistry();
	}

}
