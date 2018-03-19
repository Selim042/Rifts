package selim.enderrifts;

import java.awt.Color;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import selim.enderrifts.api.BoundFuelEntry;
import selim.enderrifts.api.RiftGenerator;
import selim.enderrifts.blocks.BlockAdamaniteOre;
import selim.enderrifts.blocks.BlockAmethystBlock;
import selim.enderrifts.blocks.BlockAmethystOre;
import selim.enderrifts.blocks.BlockAmethystTorch;
import selim.enderrifts.blocks.BlockBarite;
import selim.enderrifts.blocks.BlockCustomFlowerPot;
import selim.enderrifts.blocks.BlockNewSlab.BlockNewDoubleSlab;
import selim.enderrifts.blocks.BlockNewSlab.BlockNewHalfSlab;
import selim.enderrifts.blocks.BlockOpalBlock;
import selim.enderrifts.blocks.BlockOpalOre;
import selim.enderrifts.blocks.BlockOpaqueAir;
import selim.enderrifts.blocks.BlockRift;
import selim.enderrifts.blocks.BlockRiftConnector;
import selim.enderrifts.blocks.BlockRiftFlower;
import selim.enderrifts.blocks.BlockRiftRail;
import selim.enderrifts.blocks.BlockRiftSand;
import selim.enderrifts.blocks.BlockTeleporter;
import selim.enderrifts.blocks.BlockWillowButton;
import selim.enderrifts.blocks.BlockWillowLeaves;
import selim.enderrifts.blocks.BlockWillowLog;
import selim.enderrifts.blocks.BlockWillowPlanks;
import selim.enderrifts.blocks.BlockWillowPressurePlate;
import selim.enderrifts.blocks.BlockWillowSapling;
import selim.enderrifts.blocks.BlockWillowStairs;
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
import selim.enderrifts.items.ItemBlockFlower;
import selim.enderrifts.items.ItemDebugItem;
import selim.enderrifts.items.ItemEnderLink;
import selim.enderrifts.items.ItemFracturedPearl;
import selim.enderrifts.items.ItemNewSlab;
import selim.enderrifts.items.ItemOpal;
import selim.enderrifts.items.ItemPhantomPearl;
import selim.enderrifts.items.ItemRiftAccess;
import selim.enderrifts.items.ItemRiftEye;
import selim.enderrifts.items.ItemRiftLink;
import selim.enderrifts.items.ItemRiftTransportNode;
import selim.enderrifts.items.ItemUniversalDye;
import selim.enderrifts.utils.ArmorUtils.ItemGenBoots;
import selim.enderrifts.utils.ArmorUtils.ItemGenChestplate;
import selim.enderrifts.utils.ArmorUtils.ItemGenHelmet;
import selim.enderrifts.utils.ArmorUtils.ItemGenLeggings;
import selim.enderrifts.utils.ToolUtils.ItemGenAxe;
import selim.enderrifts.utils.ToolUtils.ItemGenHoe;
import selim.enderrifts.utils.ToolUtils.ItemGenPickaxe;
import selim.enderrifts.utils.ToolUtils.ItemGenShovel;
import selim.enderrifts.utils.ToolUtils.ItemGenSword;
import selim.enderrifts.world.BiomeRift;
import selim.enderrifts.world.DimensionRift;

public class RiftRegistry {

	public static class Registries {

		public static IForgeRegistry<RiftGenerator> RIFT_GENERATORS;
		public static IForgeRegistry<CrushRecipe> CRUSH_RECIPES;
		public static IForgeRegistry<BoundFuelEntry> BOUND_FUEL;

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
		public static final BlockRiftConnector RIFT_CONNECTOR = null;
		public static final BlockRiftRail RIFT_RAIL = null;
		public static final BlockAdamaniteOre ADAMANITE_ORE = null;
		public static final BlockWillowLog WILLOW_LOG = null;
		public static final BlockWillowLeaves WILLOW_LEAVES = null;
		public static final BlockWillowSapling WILLOW_SAPLING = null;
		public static final BlockWillowPlanks WILLOW_PLANKS = null;
		public static final BlockWillowStairs WILLOW_STAIRS = null;
		public static final BlockNewHalfSlab WILLOW_PLANKS_SLAB = null;
		public static final BlockNewDoubleSlab WILLOW_PLANKS_DOUBLE_SLAB = null;
		public static final BlockWillowPressurePlate WILLOW_PRESSURE_PLATE = null;
		public static final BlockWillowButton WILLOW_BUTTON = null;

		// public static final BlockFluidMatter MATTER = null;

	}

	@GameRegistry.ObjectHolder("enderrifts")
	public static class Items {

		public static final ItemBlockFlower RIFT_FLOWER = null;
		public static final ItemBlock RIFT_SAND = null;
		public static final ItemBlock AMETHYST_BLOCK = null;
		public static final ItemBlock AMETHYST_TORCH = null;
		public static final ItemBlock AMETHYST_ORE = null;
		public static final ItemBlock OPAL_BLOCK = null;
		public static final ItemBlock OPAL_ORE = null;
		public static final ItemBlock BARITE = null;
		public static final ItemBlock TELEPORTER = null;
		public static final ItemBlock RIFT_CONNECTOR = null;
		public static final ItemBlock RIFT_RAIL = null;
		public static final ItemBlock ADAMANTINE_ORE = null;
		public static final ItemBlock WILLOW_LOG = null;
		public static final ItemBlock WILLOW_LEAVES = null;
		public static final ItemBlockFlower WILLOW_SAPLING = null;
		public static final ItemBlock WILLOW_PLANKS = null;
		public static final ItemBlock WILLOW_STAIRS = null;
		public static final ItemNewSlab WILLOW_PLANKS_SLAB = null;
		public static final ItemBlock WILLOW_PRESSURE_PLATE = null;
		public static final ItemBlock WILLOW_BUTTON = null;

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
		public static final ItemRiftLink RIFT_LINK = null;

		public static final ItemGenPickaxe ADAMANITE_PICKAXE = null;
		public static final ItemGenAxe ADAMANITE_AXE = null;
		public static final ItemGenShovel ADAMANITE_SHOVEL = null;
		public static final ItemGenHoe ADAMANITE_HOE = null;
		public static final ItemGenSword ADAMANITE_SWORD = null;
		public static final ItemGenHelmet ADAMANITE_HELMET = null;
		public static final ItemGenChestplate ADAMANITE_CHESTPLATE = null;
		public static final ItemGenLeggings ADAMANITE_LEGGINGS = null;
		public static final ItemGenBoots ADAMANITE_BOOTS = null;

		public static final ItemDebugItem DEBUG_ITEM = null;

	}

	@GameRegistry.ObjectHolder("enderrifts")
	public static class Biomes {

		public static final BiomeRift THE_RIFT = null;
	}

	public static class ToolMaterials {

		public static final ToolMaterial ADAMANITE = EnumHelper
				.addToolMaterial(ModInfo.ID + ":adamanite", 5, 2341, 12.0F, 5.0F, 18);

	}

	public static class ArmorMaterials {

		public static final ArmorMaterial ADAMANITE = EnumHelper.addArmorMaterial(
				ModInfo.ID + ":adamanite", ModInfo.ID + ":adamanite", 66, new int[] { 4, 7, 9, 4 }, 18,
				SoundEvents.ITEM_ARMOR_EQUIP_IRON, 4.0F);

	}

	public static class Fluids {

		// public static final FluidMatter MATTER = new FluidMatter();

	}

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
		// TODO: Replace this with something just on the client to save network
		MinecraftForge.EVENT_BUS.register(new Object() {

			private final Random rand = new Random();
			private float hue = 0.0f;

			@SubscribeEvent
			public void onTick(PlayerTickEvent event) {
				if (event.phase == TickEvent.Phase.START) {
					EntityPlayer player = event.player;
					for (ItemStack stack : player.getArmorInventoryList()) {
						Item item = stack.getItem();
						NBTTagCompound nbt = stack.getTagCompound();
						if (item instanceof ItemArmor
								&& ((ItemArmor) item).getArmorMaterial().equals(ArmorMaterial.LEATHER)
								&& nbt.getBoolean("enderrifts:universal_dye")) {
							ItemArmor armor = (ItemArmor) item;
							armor.setColor(stack, getColor());
						}
					}
				}
			}

			private int getColor() {
				if (hue >= Float.MAX_VALUE / 4)
					hue = 0.0f;
				// TODO: Possibly have saturation & brightness fade as well
				return Color.HSBtoRGB(hue += rand.nextFloat() / 4000, 1.0f, 1.0f);
			}
		});
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
