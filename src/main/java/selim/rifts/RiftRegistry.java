package selim.rifts;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import selim.rifts.api.BoundFuelEntry;
import selim.rifts.api.RiftGenerator;
import selim.rifts.api.docs.DocCategory;
import selim.rifts.api.docs.DocEntry;
import selim.rifts.blocks.BlockAdamaniteBlock;
import selim.rifts.blocks.BlockAdamaniteOre;
import selim.rifts.blocks.BlockAmethystBlock;
import selim.rifts.blocks.BlockAmethystOre;
import selim.rifts.blocks.BlockAmethystTorch;
import selim.rifts.blocks.BlockBarite;
import selim.rifts.blocks.BlockCustomFlowerPot;
import selim.rifts.blocks.BlockNewSlab.BlockNewDoubleSlab;
import selim.rifts.blocks.BlockNewSlab.BlockNewHalfSlab;
import selim.rifts.blocks.BlockOpalBlock;
import selim.rifts.blocks.BlockOpalOre;
import selim.rifts.blocks.BlockOpaqueAir;
import selim.rifts.blocks.BlockRift;
import selim.rifts.blocks.BlockRiftConnector;
import selim.rifts.blocks.BlockRiftFlower;
import selim.rifts.blocks.BlockRiftRail;
import selim.rifts.blocks.BlockRiftSand;
import selim.rifts.blocks.BlockTeleporter;
import selim.rifts.blocks.BlockWillowButton;
import selim.rifts.blocks.BlockWillowDoor;
import selim.rifts.blocks.BlockWillowLeaves;
import selim.rifts.blocks.BlockWillowLog;
import selim.rifts.blocks.BlockWillowPlanks;
import selim.rifts.blocks.BlockWillowPressurePlate;
import selim.rifts.blocks.BlockWillowSapling;
import selim.rifts.blocks.BlockWillowStairs;
import selim.rifts.crafting.CrushRecipe;
import selim.rifts.gui.GuiHandler;
import selim.rifts.items.ItemAdamaniteIngot;
import selim.rifts.items.ItemAmethyst;
import selim.rifts.items.ItemBlockFlower;
import selim.rifts.items.ItemDebugItem;
import selim.rifts.items.ItemEnderLink;
import selim.rifts.items.ItemFracturedPearl;
import selim.rifts.items.ItemNewSlab;
import selim.rifts.items.ItemOpal;
import selim.rifts.items.ItemPhantomPearl;
import selim.rifts.items.ItemRiftAccess;
import selim.rifts.items.ItemRiftEye;
import selim.rifts.items.ItemRiftLink;
import selim.rifts.items.ItemRiftTransportNode;
import selim.rifts.items.ItemUniversalDye;
import selim.rifts.utils.ArmorUtils.ItemGenBoots;
import selim.rifts.utils.ArmorUtils.ItemGenChestplate;
import selim.rifts.utils.ArmorUtils.ItemGenHelmet;
import selim.rifts.utils.ArmorUtils.ItemGenLeggings;
import selim.rifts.utils.ToolUtils.ItemGenAxe;
import selim.rifts.utils.ToolUtils.ItemGenHoe;
import selim.rifts.utils.ToolUtils.ItemGenPickaxe;
import selim.rifts.utils.ToolUtils.ItemGenShovel;
import selim.rifts.utils.ToolUtils.ItemGenSword;
import selim.rifts.world.BiomeRift;
import selim.rifts.world.DimensionRift;

public class RiftRegistry {

	public static class Registries {

		public static IForgeRegistry<RiftGenerator> RIFT_GENERATORS;
		public static IForgeRegistry<CrushRecipe> CRUSH_RECIPES;
		public static IForgeRegistry<BoundFuelEntry> BOUND_FUEL;
		public static IForgeRegistry<DocCategory> DOC_CATEGORIES;
		public static IForgeRegistry<DocEntry> DOC_ENTRIES;

	}

	@GameRegistry.ObjectHolder(ModInfo.ID)
	public static class RiftProviders {

		public static final RiftGenerator OVERWORLD = null;
		public static final RiftGenerator NETHER = null;
		public static final RiftGenerator END = null;

	}

	@GameRegistry.ObjectHolder(ModInfo.ID)
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
		// TODO: See about possibly fixing this (only updates color on chunk
		// draw)
		// public static final BlockUnstableBlock WOOL_UNSTABLE = null;
		public static final BlockWillowDoor WILLOW_DOOR = null;
		public static final BlockAdamaniteBlock ADAMANITE_BLOCK = null;

		// public static final BlockFluidMatter MATTER = null;

	}

	@GameRegistry.ObjectHolder(ModInfo.ID)
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
		// TODO: See about possibly fixing this (only updates color on chunk
		// draw)
		// public static final ItemBlock WOOL_UNSTABLE = null;
		public static final ItemDoor WILLOW_DOOR = null;
		public static final ItemBlock ADAMANITE_BLOCK = null;

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
		public static final ItemAdamaniteIngot ADAMANITE_INGOT = null;

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

	@GameRegistry.ObjectHolder(ModInfo.ID)
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

	@GameRegistry.ObjectHolder(ModInfo.ID)
	public static class Categories {

		public static final DocCategory PRIMARY_CATEGORY = new DocCategory(
				ModInfo.ID + ":primary_category")
						.setRegistryName(new ResourceLocation(ModInfo.ID, "primary_category"))
						.setPriority(9).setIcon(new ItemStack(RiftRegistry.Items.RIFT_EYE));
		public static final DocCategory RIFT_OVERWORLD = new DocCategory(ModInfo.ID + ":rift_overworld")
				.setPriority(5).setRegistryName(new ResourceLocation(ModInfo.ID, "rift_overworld"))
				.setIcon(new ItemStack(RiftRegistry.Items.BARITE))
				.setRequiredDim(DimensionType.OVERWORLD);
		public static final DocCategory RIFT_NETHER = new DocCategory(ModInfo.ID + ":rift_nether")
				.setPriority(5).setRegistryName(new ResourceLocation(ModInfo.ID, "rift_nether"))
				.setIcon(new ItemStack(net.minecraft.init.Blocks.NETHERRACK))
				.setRequiredDim(DimensionType.NETHER);
		public static final DocCategory RIFT_END = new DocCategory(ModInfo.ID + ":rift_end")
				.setPriority(5).setRegistryName(new ResourceLocation(ModInfo.ID, "rift_end"))
				.setIcon(new ItemStack(net.minecraft.init.Blocks.END_STONE))
				.setRequiredDim(DimensionType.THE_END);

	}

	@GameRegistry.ObjectHolder(ModInfo.ID)
	public static class Entries {

		public static final DocEntry THE_RIFT = new DocEntry(Categories.PRIMARY_CATEGORY,
				ModInfo.ID + ":the_rift").setPriority(9)
						.setRegistryName(new ResourceLocation(ModInfo.ID, "the_rift"))
						.setIcon(new ItemStack(RiftRegistry.Items.RIFT_EYE));
		public static final DocEntry RIFT_DECAY = new DocEntry(Categories.PRIMARY_CATEGORY,
				ModInfo.ID + ":rift_decay").setPriority(7)
						.setRegistryName(new ResourceLocation(ModInfo.ID, "rift_decay"))
						.setIcon(new ItemStack(net.minecraft.init.Blocks.GRASS));
		// public static final DocEntry NETHER_RIFT = new
		// DocEntry(Categories.RIFT_NETHER,
		// ModInfo.ID + ":nether_rift")
		// .setRegistryName(new ResourceLocation(ModInfo.ID, "nether_rift"))
		// .setIcon(new ItemStack(Blocks.NETHERRACK));

	}

	public static void registerDispenserBehavior() {}

	public static void registerWorldGenerators() {}

	public static void registerGuiHandlers() {
		NetworkRegistry.INSTANCE.registerGuiHandler(EnderRifts.instance, new GuiHandler());
	}

	public static void registerDimensions() {
		DimensionRift.mainRegistry();
	}

	public static void registerFurnaceRecipes() {
		FurnaceRecipes furnaceRecipes = FurnaceRecipes.instance();
		furnaceRecipes.addSmeltingRecipeForBlock(Blocks.ADAMANITE_ORE,
				new ItemStack(Items.ADAMANITE_INGOT), 0.7F);
		furnaceRecipes.addSmeltingRecipeForBlock(Blocks.AMETHYST_ORE, new ItemStack(Items.AMETHYST), 1.0F);
		furnaceRecipes.addSmeltingRecipeForBlock(Blocks.OPAL_ORE, new ItemStack(Items.OPAL), 1.0F);
	}

}
