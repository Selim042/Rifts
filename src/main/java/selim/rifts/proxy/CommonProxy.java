package selim.rifts.proxy;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.RiftRegistry;
import selim.rifts.api.BoundFuelEntry;
import selim.rifts.api.RiftGenerator;
import selim.rifts.api.docs.DocCategory;
import selim.rifts.api.docs.DocEntry;
import selim.rifts.api.docs.IDocEntryResource;
import selim.rifts.api.docs.pages.DocPageText;
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
import selim.rifts.entities.EntityPhantomCart;
import selim.rifts.entities.EntityPhantomPearl;
import selim.rifts.entities.EntityReverseFallingBlock;
import selim.rifts.events.handlers.CrushRecipeHandler;
import selim.rifts.events.handlers.EnderTeleport;
import selim.rifts.events.handlers.FogDensity;
import selim.rifts.events.handlers.PlayerRenderEvent;
import selim.rifts.events.handlers.PurpleTint;
import selim.rifts.events.handlers.ShaderHandler;
import selim.rifts.items.ItemAdamaniteIngot;
import selim.rifts.items.ItemAmethyst;
import selim.rifts.items.ItemBlockFlower;
import selim.rifts.items.ItemBlockMeta;
import selim.rifts.items.ItemDebugItem;
import selim.rifts.items.ItemEnderLink;
import selim.rifts.items.ItemFracturedPearl;
import selim.rifts.items.ItemNewSlab;
import selim.rifts.items.ItemOpal;
import selim.rifts.items.ItemPhantomPearl;
import selim.rifts.items.ItemRiftEye;
import selim.rifts.items.ItemRiftLink;
import selim.rifts.items.ItemRiftTransportNode;
import selim.rifts.items.ItemUniversalDye;
import selim.rifts.misc.IJsonParser;
import selim.rifts.riftgenerators.RiftGeneratorNether;
import selim.rifts.riftgenerators.RiftGeneratorOverworld;
import selim.rifts.tiles.TileRiftConnector;
import selim.rifts.tiles.TileRiftPortal;
import selim.rifts.tiles.TileRiftRail;
import selim.rifts.tiles.TileTeleporter;
import selim.rifts.utils.ArmorUtils;
import selim.rifts.utils.MiscUtils;
import selim.rifts.utils.ToolUtils;
import selim.rifts.world.BiomeRift;

@Mod.EventBusSubscriber
public class CommonProxy {

	// @SubscribeEvent
	// public static void fixBlockMappings(MissingMappings<Block> event) {
	// List<Mapping<Block>> mappings = event.getMappings();
	// for (Mapping<Block> mapping : mappings) {
	// if (mapping.key.getResourceDomain().equals("enderrifts")) {
	// Block block = mapping.registry.getValue(mapping.key);
	// block.setRegistryName(new ResourceLocation(ModInfo.ID,
	// mapping.key.getResourcePath()));
	// mapping.remap(block);
	// }
	// }
	// }
	//
	// @SubscribeEvent
	// public static void fixItemMappings(MissingMappings<Item> event) {
	// List<Mapping<Item>> mappings = event.getMappings();
	// for (Mapping<Item> mapping : mappings) {
	// if (mapping.key.getResourceDomain().equals("enderrifts")) {
	// Item item = mapping.registry.getValue(mapping.key);
	// item.setRegistryName(new ResourceLocation(ModInfo.ID,
	// mapping.key.getResourcePath()));
	// mapping.remap(item);
	// }
	// }
	// }

	@SubscribeEvent
	public static void registerCommonRegistries(RegistryEvent.NewRegistry event) {
		RiftRegistry.Registries.RIFT_GENERATORS = new RegistryBuilder<RiftGenerator>()
				.setType(RiftGenerator.class)
				.setName(new ResourceLocation(ModInfo.ID, "rift_generators")).create();
		RiftRegistry.Registries.CRUSH_RECIPES = new RegistryBuilder<CrushRecipe>()
				.setType(CrushRecipe.class).setName(new ResourceLocation(ModInfo.ID, "crush_recipe"))
				.create();
		RiftRegistry.Registries.BOUND_FUEL = new RegistryBuilder<BoundFuelEntry>()
				.setType(BoundFuelEntry.class).setName(new ResourceLocation(ModInfo.ID, "bound_fuel"))
				.create();

		RiftRegistry.Registries.DOC_CATEGORIES = new RegistryBuilder<DocCategory>()
				.setType(DocCategory.class).setName(new ResourceLocation(ModInfo.ID, "doc_categories"))
				.create();
		RiftRegistry.Registries.DOC_ENTRIES = new RegistryBuilder<DocEntry>().setType(DocEntry.class)
				.setName(new ResourceLocation(ModInfo.ID, "doc_entries")).create();
	}

	@SubscribeEvent
	public static void registerRiftGenerators(RegistryEvent.Register<RiftGenerator> event) {
		event.getRegistry().register(new RiftGeneratorOverworld());
		event.getRegistry().register(new RiftGeneratorNether());
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> reg = event.getRegistry();
		reg.register(new BlockRift());
		GameRegistry.registerTileEntity(TileRiftPortal.class, ModInfo.ID + ":rift_portal");
		reg.register(new BlockRiftFlower());
		reg.register(new BlockRiftSand());
		reg.register(new BlockOpaqueAir());
		reg.register(new BlockAmethystBlock());
		reg.register(new BlockAmethystTorch());
		BlockAmethystOre amethystOre = new BlockAmethystOre();
		reg.register(amethystOre);
		reg.register(new BlockCustomFlowerPot());
		reg.register(new BlockOpalBlock());
		BlockOpalOre opalOre = new BlockOpalOre();
		reg.register(opalOre);
		reg.register(new BlockBarite());
		reg.register(new BlockTeleporter());
		GameRegistry.registerTileEntity(TileTeleporter.class, ModInfo.ID + ":teleporter");
		reg.register(new BlockRiftConnector());
		GameRegistry.registerTileEntity(TileRiftConnector.class, ModInfo.ID + ":rift_connector");
		reg.register(new BlockRiftRail());
		GameRegistry.registerTileEntity(TileRiftRail.class, ModInfo.ID + ":rift_rail");
		reg.register(new BlockAdamaniteOre());
		reg.register(new BlockWillowLog());
		reg.register(new BlockWillowLeaves());
		reg.register(new BlockWillowSapling());
		BlockWillowPlanks planks = new BlockWillowPlanks();
		reg.register(planks);
		reg.register(new BlockWillowStairs(planks));
		reg.register(new BlockNewHalfSlab(planks));
		reg.register(new BlockNewDoubleSlab(planks));
		reg.register(new BlockWillowPressurePlate());
		reg.register(new BlockWillowButton());
		// TODO: See about possibly fixing this (only updates color on chunk
		// draw)
		// reg.register(new BlockUnstableBlock(Blocks.WOOL.getDefaultState()));
		reg.register(new BlockWillowDoor());
		reg.register(new BlockAdamaniteBlock());
		// reg.register(new BlockRedstoneConnector());
		// GameRegistry.registerTileEntity(TileRedstoneConnector.class, ModInfo.ID + ":redstone_connector");

		// STOP TRYING TO REGISTER OREDICT HERE
		// OreDictionary.registerOre("riftOre", amethystOre);
		// OreDictionary.registerOre("riftOre", opalOre);
		// OreDictionary.registerOre("oreRiftAdamantie", adamaniteOre);
		// OreDictionary.registerOre("blockRiftAdamantie", adamaniteBlock);

		// FluidRegistry.registerFluid(RiftsRegistry.Fluids.MATTER);
		// FluidRegistry.addBucketForFluid(RiftsRegistry.Fluids.MATTER);
		// reg.register(new BlockFluidMatter());
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		// registerItemBlock(reg, RiftRegistry.Blocks.RIFT_TEST);
		reg.register(new ItemBlockFlower(RiftRegistry.Blocks.RIFT_FLOWER)
				.setRegistryName(RiftRegistry.Blocks.RIFT_FLOWER.getRegistryName()));
		registerItemBlock(reg, RiftRegistry.Blocks.RIFT_SAND);
		registerItemBlock(reg, RiftRegistry.Blocks.AMETHYST_BLOCK);
		registerItemBlock(reg, RiftRegistry.Blocks.AMETHYST_TORCH);
		OreDictionary.registerOre("riftOre", registerItemBlock(reg, RiftRegistry.Blocks.AMETHYST_ORE));
		registerItemBlock(reg, RiftRegistry.Blocks.OPAL_BLOCK);
		OreDictionary.registerOre("riftOre", registerItemBlock(reg, RiftRegistry.Blocks.OPAL_ORE));
		registerItemBlockMeta(reg, RiftRegistry.Blocks.BARITE);
		registerItemBlock(reg, RiftRegistry.Blocks.TELEPORTER);
		registerItemBlock(reg, RiftRegistry.Blocks.RIFT_CONNECTOR);
		registerItemBlock(reg, RiftRegistry.Blocks.RIFT_RAIL);
		registerItemBlock(reg, RiftRegistry.Blocks.ADAMANITE_ORE, "oreRiftAdamanite");
		registerItemBlock(reg, RiftRegistry.Blocks.WILLOW_LOG, "logWood");
		registerItemBlock(reg, RiftRegistry.Blocks.WILLOW_LEAVES);
		reg.register(new ItemBlockFlower(RiftRegistry.Blocks.WILLOW_SAPLING)
				.setRegistryName(RiftRegistry.Blocks.WILLOW_SAPLING.getRegistryName()));
		registerItemBlock(reg, RiftRegistry.Blocks.WILLOW_PLANKS, "plankWood");
		registerItemBlock(reg, RiftRegistry.Blocks.WILLOW_STAIRS, "stairsWood");
		reg.register(new ItemNewSlab(RiftRegistry.Blocks.WILLOW_PLANKS_SLAB,
				RiftRegistry.Blocks.WILLOW_PLANKS_SLAB, RiftRegistry.Blocks.WILLOW_PLANKS_DOUBLE_SLAB));
		registerItemBlock(reg, RiftRegistry.Blocks.WILLOW_PRESSURE_PLATE);
		registerItemBlock(reg, RiftRegistry.Blocks.WILLOW_BUTTON);
		// TODO: See about possibly fixing this (only updates color on chunk
		// draw)
		// registerItemBlockMeta(reg, RiftRegistry.Blocks.WOOL_UNSTABLE);
		reg.register(new ItemDoor(RiftRegistry.Blocks.WILLOW_DOOR)
				.setRegistryName(RiftRegistry.Blocks.WILLOW_DOOR.getRegistryName())
				.setUnlocalizedName(RiftRegistry.Blocks.WILLOW_DOOR.getUnlocalizedName()));
		registerItemBlock(reg, RiftRegistry.Blocks.ADAMANITE_BLOCK, "oreRiftAdamanite");

		ItemUniversalDye universalDye = new ItemUniversalDye();
		reg.register(universalDye);
		ItemAmethyst amethyst = new ItemAmethyst();
		reg.register(amethyst);
		ItemOpal opal = new ItemOpal();
		reg.register(opal);
		reg.register(new ItemPhantomPearl());
		reg.register(new ItemRiftTransportNode());
		// Disabled as it doesn't seem possible to make it work reliably (& not
		// be super hacky)
		// reg.register(new ItemRiftAccess());
		reg.register(new ItemEnderLink());
		reg.register(new ItemRiftEye());
		reg.register(new ItemFracturedPearl());
		reg.register(new ItemRiftLink());
		ItemAdamaniteIngot adamaniteIngot = new ItemAdamaniteIngot();
		reg.register(adamaniteIngot);

		reg.registerAll(ToolUtils.genToolset(RiftRegistry.ToolMaterials.ADAMANITE, EnderRifts.mainTab,
				new ResourceLocation(ModInfo.ID, "adamanite"), ModInfo.ID + ":adamanite", -2.8f,
				new ResourceLocation(ModInfo.ID, "admanite")));
		reg.registerAll(ArmorUtils.genArmor(RiftRegistry.ArmorMaterials.ADAMANITE, EnderRifts.mainTab,
				new ResourceLocation(ModInfo.ID, "adamanite"), ModInfo.ID + ":adamanite",
				new ResourceLocation(ModInfo.ID, "admanite")));

		for (EnumDyeColor color : EnumDyeColor.values()) {
			String name = color.getUnlocalizedName();
			OreDictionary.registerOre("dye", new ItemStack(universalDye));
			OreDictionary.registerOre("dye" + name.substring(0, 1).toUpperCase() + name.substring(1),
					new ItemStack(universalDye, 1, color.getDyeDamage()));
		}
		OreDictionary.registerOre("gemLapis", new ItemStack(amethyst));
		// OreDictionary.registerOre("riftOreGem", amethyst);
		// OreDictionary.registerOre("riftOreGem", opal);
		OreDictionary.registerOre("ingotRiftAdamanite", adamaniteIngot);

		if (MiscUtils.isDevEnvironment())
			event.getRegistry().register(new ItemDebugItem());
	}

	@SubscribeEvent
	public static void registerCategory(RegistryEvent.Register<DocCategory> event) {
		event.getRegistry().register(RiftRegistry.Categories.PRIMARY_CATEGORY);
		event.getRegistry().register(RiftRegistry.Categories.RIFT_OVERWORLD);
		event.getRegistry().register(RiftRegistry.Categories.RIFT_NETHER);
		event.getRegistry().register(RiftRegistry.Categories.RIFT_END);
	}

	@SubscribeEvent
	public static void registerEntries(RegistryEvent.Register<DocEntry> event) {
		event.getRegistry().register(RiftRegistry.Entries.THE_RIFT);
		RiftRegistry.Entries.THE_RIFT.addPages(
				new DocPageText(ModInfo.ID + ":rift_basics_0_0", ModInfo.ID + ":rift_basics_0_1",
						ModInfo.ID + ":rift_basics_0_2"),
				new DocPageText(ModInfo.ID + ":rift_basics_1_0", ModInfo.ID + ":rift_basics_1_1"));
		event.getRegistry().register(RiftRegistry.Entries.RIFT_DECAY);
		RiftRegistry.Entries.RIFT_DECAY.addPage(new DocPageText(ModInfo.ID + ":rift_decay_0_0",
				ModInfo.ID + ":rift_decay_0_1", ModInfo.ID + ":rift_decay_0_2"));
		// RiftRegistry.Entries.NETHER_RIFT.addPage(new DocPageText(ModInfo.ID +
		// ":nether_rift_0_0"));

		for (Entry<ResourceLocation, RiftGenerator> entry : RiftRegistry.Registries.RIFT_GENERATORS
				.getEntries())
			if (entry.getValue() instanceof IDocEntryResource)
				event.getRegistry().register(new DocEntry((IDocEntryResource) entry.getValue()));
		for (Entry<ResourceLocation, Block> entry : ForgeRegistries.BLOCKS.getEntries())
			if (entry.getValue() instanceof IDocEntryResource)
				event.getRegistry().register(new DocEntry((IDocEntryResource) entry.getValue()));
		for (Entry<ResourceLocation, Item> entry : ForgeRegistries.ITEMS.getEntries())
			if (entry.getValue() instanceof IDocEntryResource)
				event.getRegistry().register(new DocEntry((IDocEntryResource) entry.getValue()));

		// try {
		// Class<RiftRegistry.RiftProviders> riftsClazz =
		// RiftRegistry.RiftProviders.class;
		// for (Field f : riftsClazz.getFields()) {
		// Object obj = f.get(null);
		// if (IDocEntryResource.class.isInstance(obj))
		// event.getRegistry().register(new DocEntry((IDocEntryResource) obj));
		// }
		// Class<RiftRegistry.Blocks> blocksClazz = RiftRegistry.Blocks.class;
		// for (Field f : blocksClazz.getFields()) {
		// Object obj = f.get(null);
		// if (IDocEntryResource.class.isInstance(obj))
		// event.getRegistry().register(new DocEntry((IDocEntryResource) obj));
		// }
		// Class<RiftRegistry.Items> itemsClazz = RiftRegistry.Items.class;
		// for (Field f : itemsClazz.getFields()) {
		// Object obj = f.get(null);
		// if (IDocEntryResource.class.isInstance(obj))
		// event.getRegistry().register(new DocEntry((IDocEntryResource) obj));
		// }
		// } catch (IllegalAccessException e) {}
	}

	private static ItemBlock registerItemBlock(IForgeRegistry<Item> reg, Block block,
			String... oreDict) {
		ItemBlock item = (ItemBlock) new ItemBlock(block).setRegistryName(block.getRegistryName());
		reg.register(item);
		return registerItem(reg, item, oreDict);
	}

	private static ItemBlockMeta registerItemBlockMeta(IForgeRegistry<Item> reg, Block block,
			String... oreDict) {
		ItemBlockMeta item = (ItemBlockMeta) new ItemBlockMeta(block)
				.setRegistryName(block.getRegistryName());
		return registerItem(reg, item, oreDict);
	}

	private static <T extends Item> T registerItem(IForgeRegistry<Item> reg, T item, String... oreDict) {
		reg.register(item);
		for (String ore : oreDict)
			OreDictionary.registerOre(ore, item);
		return item;
	}

	@SubscribeEvent
	public void registerBiome(RegistryEvent.Register<Biome> event) {
		event.getRegistry().register(new BiomeRift());
	}

	@SubscribeEvent
	public void registerEntity(RegistryEvent.Register<EntityEntry> event) {
		// event.getRegistry().register(
		// new EntityEntry(EntityReverseFallingBlock.class, ModInfo.ID +
		// ":reverse_falling_block"));
		// event.getRegistry()
		// .register(new EntityEntry(EntityPhantomPearl.class, ModInfo.ID +
		// ":phantom_pearl"));
		// event.getRegistry().register(EntityEntryBuilder.create().entity(EntityReverseFallingBlock.class)
		// .id(new ResourceLocation(ModInfo.ID, "reverse_falling_block"),
		// 0).build());
		// event.getRegistry().register(EntityEntryBuilder.create().entity(EntityPhantomPearl.class)
		// .id(new ResourceLocation(ModInfo.ID, "phantom_pearl"), 1).build());
		// event.getRegistry().register(EntityEntryBuilder.create().entity(EntityPhantomCart.class)
		// .id(new ResourceLocation(ModInfo.ID, "phantom_cart"), 2).build());
	}

	@SubscribeEvent
	public static void registerBoundFuelEntries(RegistryEvent.Register<BoundFuelEntry> event) {
		event.getRegistry()
				.register(new BoundFuelEntry(new ItemStack(RiftRegistry.Items.FRACTURED_PEARL), 4)
						.setRegistryName(new ResourceLocation(ModInfo.ID, "fratured_pearl")));
	}

	public void preInit() {
		int id = 1;
		EntityRegistry.registerModEntity(new ResourceLocation(ModInfo.ID, "reverse_falling_block"),
				EntityReverseFallingBlock.class, "reverse_falling_block", id++, EnderRifts.instance, 64,
				1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ModInfo.ID, "phantom_pearl"),
				EntityPhantomPearl.class, "phantom_pearl", id++, EnderRifts.instance, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ModInfo.ID, "phantom_cart"),
				EntityPhantomCart.class, "phantom_cart", id++, EnderRifts.instance, 64, 1, true);
	}

	private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	public void init() {}

	public void postInit() {
		loadRecipes("crush", CrushRecipe.getParser());
	}

	public void setRiftShader(boolean enabled) {}

	public void addVisitedToPersistance(DimensionType type) {}

	public boolean hasVisitedFromPersistance(DimensionType type) {
		return false;
	}

	public void registerEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new EnderTeleport());
		MinecraftForge.EVENT_BUS.register(new FogDensity());
		MinecraftForge.EVENT_BUS.register(new PurpleTint());
		MinecraftForge.EVENT_BUS.register(new ShaderHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerRenderEvent());
		if (RiftRegistry.Items.RIFT_TRANSPORT_NODE != null)
			MinecraftForge.EVENT_BUS.register(RiftRegistry.Items.RIFT_TRANSPORT_NODE);
		MinecraftForge.EVENT_BUS.register(new CrushRecipeHandler());
		// Laggy
		// MinecraftForge.EVENT_BUS.register(new WaterEffectHandler());
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

	public static <T extends IForgeRegistryEntry<T>> void loadRecipes(String path,
			IJsonParser<T> parser) {
		loadRecipes(path, parser, GameRegistry.findRegistry(parser.getType()));
	}

	public static <T extends IForgeRegistryEntry<T>> void loadRecipes(String path, IJsonParser<T> parser,
			IForgeRegistry<T> reg) {
		ModContainer current = Loader.instance().activeModContainer();
		for (Entry<String, ModContainer> entry : Loader.instance().getIndexedModList().entrySet()) {
			ModContainer mod = entry.getValue();
			JsonContext ctx = new JsonContext(mod.getModId());

			CraftingHelper.findFiles(mod, "assets/" + mod.getModId() + "/enderrifts_recipes/" + path,
					root -> {
						return true;
					}, (root, file) -> {
						Loader.instance().setActiveModContainer(mod);

						String relative = root.relativize(file).toString();
						if (!"json".equals(FilenameUtils.getExtension(file.toString()))
								|| relative.startsWith("_"))
							return true;

						String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
						ResourceLocation key = new ResourceLocation(ctx.getModId(), name);

						BufferedReader reader = null;
						try {
							reader = Files.newBufferedReader(file);
							JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
							if (json.has("conditions") && !CraftingHelper
									.processConditions(JsonUtils.getJsonArray(json, "conditions"), ctx))
								return true;
							T val = parser.parse(json, ctx);
							reg.register(val.setRegistryName(key));
						} catch (JsonParseException e) {
							FMLLog.log.error("Parsing error loading recipe {}", key, e);
							return false;
						} catch (IOException e) {
							FMLLog.log.error("Couldn't read recipe {} from {}", key, file, e);
							return false;
						} finally {
							IOUtils.closeQuietly(reader);
						}
						return true;
					}, true, true);
		}
		Loader.instance().setActiveModContainer(current);
	}

}