package selim.enderrifts.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import selim.enderrifts.ModInfo;
import selim.enderrifts.RiftsRegistry;
import selim.enderrifts.api.RiftGenerator;
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
import selim.enderrifts.blocks.TileTeleporter;
import selim.enderrifts.crafting.CrushRecipe;
import selim.enderrifts.crafting.SpecificCrushRecipe;
import selim.enderrifts.entities.EntityPhantomPearl;
import selim.enderrifts.entities.EntityReverseFallingBlock;
import selim.enderrifts.items.ItemAmethyst;
import selim.enderrifts.items.ItemBlockFlower;
import selim.enderrifts.items.ItemBlockMeta;
import selim.enderrifts.items.ItemDebugItem;
import selim.enderrifts.items.ItemEnderLink;
import selim.enderrifts.items.ItemOpal;
import selim.enderrifts.items.ItemPhantomPearl;
import selim.enderrifts.items.ItemRiftEye;
import selim.enderrifts.items.ItemRiftTransportNode;
import selim.enderrifts.items.ItemUniversalDye;
import selim.enderrifts.riftgenerators.RiftGeneratorNether;
import selim.enderrifts.riftgenerators.RiftGeneratorOverworld;
import selim.enderrifts.utils.MiscUtils;

@Mod.EventBusSubscriber
public class CommonProxy {

	@SubscribeEvent
	public static void registerCommonRegistries(RegistryEvent.NewRegistry event) {
		new RegistryBuilder<RiftGenerator>().setType(RiftGenerator.class)
				.setName(new ResourceLocation(ModInfo.ID, "rift_generators")).create();
		new RegistryBuilder<CrushRecipe>().setType(CrushRecipe.class)
				.setName(new ResourceLocation(ModInfo.ID, "crush_recipe")).create();
	}

	@SubscribeEvent
	public static void registrRiftGenerators(RegistryEvent.Register<RiftGenerator> event) {
		event.getRegistry().register(new RiftGeneratorOverworld());
		event.getRegistry().register(new RiftGeneratorNether());
	}

	@SubscribeEvent
	public static void registerCrushRecipes(RegistryEvent.Register<CrushRecipe> event) {
		event.getRegistry()
				.register(new SpecificCrushRecipe(new ItemStack(RiftsRegistry.Items.AMETHYST, 9),
						new ItemStack(RiftsRegistry.Items.AMETHYST_BLOCK))
								.setRegistryName(new ResourceLocation(ModInfo.ID, "test")));
		event.getRegistry()
				.register(new SpecificCrushRecipe(new ItemStack(RiftsRegistry.Items.AMETHYST, 1),
						new ItemStack(RiftsRegistry.Items.OPAL, 2))
								.setRegistryName(new ResourceLocation(ModInfo.ID, "test2")));
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> reg = event.getRegistry();
		reg.register(new BlockRift());
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

		OreDictionary.registerOre("riftOre", amethystOre);
		OreDictionary.registerOre("riftOre", opalOre);

		// FluidRegistry.registerFluid(RiftsRegistry.Fluids.MATTER);
		// FluidRegistry.addBucketForFluid(RiftsRegistry.Fluids.MATTER);
		// reg.register(new BlockFluidMatter());
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		registerItemBlock(reg, RiftsRegistry.Blocks.RIFT_TEST);
		event.getRegistry().register(new ItemBlockFlower(RiftsRegistry.Blocks.RIFT_FLOWER)
				.setRegistryName(RiftsRegistry.Blocks.RIFT_FLOWER.getRegistryName()));
		registerItemBlock(reg, RiftsRegistry.Blocks.RIFT_SAND);
		registerItemBlock(reg, RiftsRegistry.Blocks.AMETHYST_BLOCK);
		registerItemBlock(reg, RiftsRegistry.Blocks.AMETHYST_TORCH);
		OreDictionary.registerOre("riftOre", registerItemBlock(reg, RiftsRegistry.Blocks.AMETHYST_ORE));
		registerItemBlock(reg, RiftsRegistry.Blocks.OPAL_BLOCK);
		OreDictionary.registerOre("riftOre", registerItemBlock(reg, RiftsRegistry.Blocks.OPAL_ORE));
		registerItemBlockMeta(reg, RiftsRegistry.Blocks.BARITE);
		registerItemBlock(reg, RiftsRegistry.Blocks.TELEPORTER);
		GameRegistry.registerTileEntity(TileTeleporter.class, ModInfo.ID + ":teleporter");

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

		// for (String ore : OreDictionary.getOreNames())
		// EnderRifts.LOGGER.info("ore: " + ore);

		for (EnumDyeColor color : EnumDyeColor.values()) {
			String name = color.getUnlocalizedName();
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
		event.getRegistry().register(RiftsRegistry.riftBiome);
	}

	@SubscribeEvent
	public void registerEntity(RegistryEvent.Register<EntityEntry> event) {
		// event.getRegistry().register(
		// new EntityEntry(EntityReverseFallingBlock.class, ModInfo.ID +
		// ":reverse_falling_block"));
		// event.getRegistry()
		// .register(new EntityEntry(EntityPhantomPearl.class, ModInfo.ID +
		// ":phantom_pearl"));
		event.getRegistry().register(EntityEntryBuilder.create().entity(EntityReverseFallingBlock.class)
				.id(new ResourceLocation(ModInfo.ID, "reverse_falling_block"), 0).build());
		event.getRegistry().register(EntityEntryBuilder.create().entity(EntityPhantomPearl.class)
				.id(new ResourceLocation(ModInfo.ID, "phantom_pearl"), 0).build());
	}

	public void preInit() {}

	public void init() {}

	public void postInit() {}

	public void setRiftShader(boolean enabled) {}

	public void addVisitedToPersistance(DimensionType type) {}

	public boolean hasVisitedFromPersistance(DimensionType type) {
		return false;
	}

}