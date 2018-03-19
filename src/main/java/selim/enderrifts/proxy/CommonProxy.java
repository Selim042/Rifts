package selim.enderrifts.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map.Entry;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;
import selim.enderrifts.RiftRegistry;
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
import selim.enderrifts.entities.EntityPhantomCart;
import selim.enderrifts.entities.EntityPhantomPearl;
import selim.enderrifts.entities.EntityReverseFallingBlock;
import selim.enderrifts.items.ItemAmethyst;
import selim.enderrifts.items.ItemBlockFlower;
import selim.enderrifts.items.ItemBlockMeta;
import selim.enderrifts.items.ItemDebugItem;
import selim.enderrifts.items.ItemEnderLink;
import selim.enderrifts.items.ItemFracturedPearl;
import selim.enderrifts.items.ItemNewSlab;
import selim.enderrifts.items.ItemOpal;
import selim.enderrifts.items.ItemPhantomPearl;
import selim.enderrifts.items.ItemRiftEye;
import selim.enderrifts.items.ItemRiftLink;
import selim.enderrifts.items.ItemRiftTransportNode;
import selim.enderrifts.items.ItemUniversalDye;
import selim.enderrifts.misc.IJsonParser;
import selim.enderrifts.riftgenerators.RiftGeneratorNether;
import selim.enderrifts.riftgenerators.RiftGeneratorOverworld;
import selim.enderrifts.tiles.TileRiftConnector;
import selim.enderrifts.tiles.TileRiftPortal;
import selim.enderrifts.tiles.TileRiftRail;
import selim.enderrifts.tiles.TileTeleporter;
import selim.enderrifts.utils.ArmorUtils;
import selim.enderrifts.utils.MiscUtils;
import selim.enderrifts.utils.ToolUtils;
import selim.enderrifts.world.BiomeRift;

@Mod.EventBusSubscriber
public class CommonProxy {

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

		OreDictionary.registerOre("riftOre", amethystOre);
		OreDictionary.registerOre("riftOre", opalOre);

		// FluidRegistry.registerFluid(RiftsRegistry.Fluids.MATTER);
		// FluidRegistry.addBucketForFluid(RiftsRegistry.Fluids.MATTER);
		// reg.register(new BlockFluidMatter());
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		registerItemBlock(reg, RiftRegistry.Blocks.RIFT_TEST);
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
		registerItemBlock(reg, RiftRegistry.Blocks.ADAMANITE_ORE);
		registerItemBlock(reg, RiftRegistry.Blocks.WILLOW_LOG);
		registerItemBlock(reg, RiftRegistry.Blocks.WILLOW_LEAVES);
		reg.register(new ItemBlockFlower(RiftRegistry.Blocks.WILLOW_SAPLING)
				.setRegistryName(RiftRegistry.Blocks.WILLOW_SAPLING.getRegistryName()));
		registerItemBlock(reg, RiftRegistry.Blocks.WILLOW_PLANKS);
		registerItemBlock(reg, RiftRegistry.Blocks.WILLOW_STAIRS);
		reg.register(new ItemNewSlab(RiftRegistry.Blocks.WILLOW_PLANKS_SLAB,
				RiftRegistry.Blocks.WILLOW_PLANKS_SLAB, RiftRegistry.Blocks.WILLOW_PLANKS_DOUBLE_SLAB));
		registerItemBlock(reg, RiftRegistry.Blocks.WILLOW_PRESSURE_PLATE);
		registerItemBlock(reg, RiftRegistry.Blocks.WILLOW_BUTTON);

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

		reg.registerAll(ToolUtils.genToolset(RiftRegistry.ToolMaterials.ADAMANITE, EnderRifts.mainTab,
				new ResourceLocation(ModInfo.ID, "adamanite"), ModInfo.ID + ":adamanite", -2.8f));
		reg.registerAll(ArmorUtils.genArmor(RiftRegistry.ArmorMaterials.ADAMANITE, EnderRifts.mainTab,
				new ResourceLocation(ModInfo.ID, "adamanite"), ModInfo.ID + ":adamanite"));

		for (EnumDyeColor color : EnumDyeColor.values()) {
			String name = color.getUnlocalizedName();
			OreDictionary.registerOre("dye", new ItemStack(universalDye));
			OreDictionary.registerOre("dye" + name.substring(0, 1).toUpperCase() + name.substring(1),
					new ItemStack(universalDye, 1, color.getDyeDamage()));
		}
		OreDictionary.registerOre("gemLapis", new ItemStack(amethyst));
		OreDictionary.registerOre("riftOreGem", amethyst);
		OreDictionary.registerOre("riftOreGem", opal);

		if (MiscUtils.isDevEnvironment())
			event.getRegistry().register(new ItemDebugItem());
	}

	private static ItemBlock registerItemBlock(IForgeRegistry<Item> reg, Block block) {
		ItemBlock item = (ItemBlock) new ItemBlock(block).setRegistryName(block.getRegistryName());
		reg.register(item);
		return item;
	}

	private static ItemBlockMeta registerItemBlockMeta(IForgeRegistry<Item> reg, Block block) {
		ItemBlockMeta item = (ItemBlockMeta) new ItemBlockMeta(block)
				.setRegistryName(block.getRegistryName());
		reg.register(item);
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