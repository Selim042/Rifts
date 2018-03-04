package selim.enderrifts.proxy;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.RegistryBuilder;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;
import selim.enderrifts.PersistencyHandler;
import selim.enderrifts.RiftsRegistry;
import selim.enderrifts.api.docs.DocCategory;
import selim.enderrifts.api.docs.DocEntry;
import selim.enderrifts.api.docs.pages.DocPageCraftingRecipe;
import selim.enderrifts.api.docs.pages.DocPageText;
import selim.enderrifts.entities.EntityPhantomPearl;
import selim.enderrifts.entities.EntityReverseFallingBlock;
import selim.enderrifts.entities.render.RenderReverseFallingBlock;
import selim.enderrifts.events.handlers.ClientTicks;
import selim.enderrifts.utils.MiscUtils;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@GameRegistry.ObjectHolder("enderrifts")
	public static class Categories {

		public static final DocCategory PRIMARY_CATEGORY = new DocCategory(
				ModInfo.ID + ":primary_category")
						.setRegistryName(new ResourceLocation(ModInfo.ID, "primary_category"))
						.setPriority(9).setIcon(new ItemStack(RiftsRegistry.Items.RIFT_EYE));
		public static final DocCategory RIFT_OVERWORLD = new DocCategory(ModInfo.ID + ":rift_overworld")
				.setPriority(5).setRegistryName(new ResourceLocation(ModInfo.ID, "rift_overworld"))
				.setIcon(new ItemStack(RiftsRegistry.Items.BARITE))
				.setRequiredDim(DimensionType.OVERWORLD);
		public static final DocCategory RIFT_NETHER = new DocCategory(ModInfo.ID + ":rift_nether")
				.setPriority(5).setRegistryName(new ResourceLocation(ModInfo.ID, "rift_nether"))
				.setIcon(new ItemStack(Blocks.NETHERRACK)).setRequiredDim(DimensionType.NETHER);
		public static final DocCategory RIFT_END = new DocCategory(ModInfo.ID + ":rift_end")
				.setPriority(5).setRegistryName(new ResourceLocation(ModInfo.ID, "rift_end"))
				.setIcon(new ItemStack(Blocks.END_STONE)).setRequiredDim(DimensionType.THE_END);

	}

	@GameRegistry.ObjectHolder("enderrifts")
	public static class Entries {

		public static final DocEntry THE_RIFT = new DocEntry(Categories.PRIMARY_CATEGORY,
				ModInfo.ID + ":the_rift").setPriority(9)
						.setRegistryName(new ResourceLocation(ModInfo.ID, "the_rift"))
						.setIcon(new ItemStack(RiftsRegistry.Items.RIFT_EYE));
		public static final DocEntry RIFT_DECAY = new DocEntry(Categories.PRIMARY_CATEGORY,
				ModInfo.ID + ":rift_decay").setPriority(7)
						.setRegistryName(new ResourceLocation(ModInfo.ID, "rift_decay"))
						.setIcon(new ItemStack(Blocks.GRASS));
		public static final DocEntry BARITE = new DocEntry(Categories.RIFT_OVERWORLD,
				ModInfo.ID + ":barite").setPriority(5)
						.setRegistryName(new ResourceLocation(ModInfo.ID, "barite"))
						.setIcon(new ItemStack(RiftsRegistry.Items.BARITE));

	}

	@SubscribeEvent
	public static void registerClientRegistries(RegistryEvent.NewRegistry event) {
		new RegistryBuilder<DocCategory>().setType(DocCategory.class)
				.setName(new ResourceLocation(ModInfo.ID, "doc_categories")).create();
		new RegistryBuilder<DocEntry>().setType(DocEntry.class)
				.setName(new ResourceLocation(ModInfo.ID, "doc_entries")).create();
	}

	@SubscribeEvent
	public static void registerCategory(RegistryEvent.Register<DocCategory> event) {
		event.getRegistry().register(Categories.PRIMARY_CATEGORY);
		event.getRegistry().register(Categories.RIFT_OVERWORLD);
		event.getRegistry().register(Categories.RIFT_NETHER);
		event.getRegistry().register(Categories.RIFT_END);
	}

	@SubscribeEvent
	public static void registerEntries(RegistryEvent.Register<DocEntry> event) {
		event.getRegistry().register(Entries.THE_RIFT);
		Entries.THE_RIFT.addPages(
				new DocPageText(ModInfo.ID + ":rift_basics_0_0", ModInfo.ID + ":rift_basics_0_1",
						ModInfo.ID + ":rift_basics_0_2"),
				new DocPageText(ModInfo.ID + ":rift_basics_1_0", ModInfo.ID + ":rift_basics_1_1"));
		event.getRegistry().register(Entries.RIFT_DECAY);
		Entries.RIFT_DECAY.addPage(new DocPageText(ModInfo.ID + ":rift_decay_0_0",
				ModInfo.ID + ":rift_decay_0_1", ModInfo.ID + ":rift_decay_0_2"));
		Entries.BARITE.addPage(new DocPageCraftingRecipe(new ResourceLocation("minecraft", "furnace")));
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		Class<RiftsRegistry.Items> clazz = RiftsRegistry.Items.class;
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field f : fields) {
				Object obj = f.get(null);
				if (obj == null)
					continue;
				if (obj instanceof Item && ((Item) obj).getHasSubtypes())
					continue;
				if (f.getType().equals(ItemBlock.class))
					registerModel((ItemBlock) obj);
				else if (!f.getName().equals("DEBUG_ITEM"))
					registerModel((Item) obj);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			EnderRifts.LOGGER.error("An " + e.getClass().getName()
					+ " was thrown when attempting to load ItemBlock models.");
		}
		// for (EnumDyeColor color : EnumDyeColor.values())
		// ModelLoader.setCustomModelResourceLocation(RiftsRegistry.universalDye,
		// color.getDyeDamage(),
		// new ModelResourceLocation(
		// RiftsRegistry.universalDye.getRegistryName() + "_" +
		// color.getDyeColorName(),
		// "inventory"));
		registerModel(RiftsRegistry.Items.UNIVERSAL_DYE);
		registerModel(RiftsRegistry.Items.BARITE, 0);
		registerModel(RiftsRegistry.Items.BARITE, 1, "_smooth");
		registerModel(RiftsRegistry.Items.BARITE, 2, "_brick");
		// registerModel(RiftsRegistry.Items.AMETHYST);

		if (MiscUtils.isDevEnvironment())
			registerModel(RiftsRegistry.Items.DEBUG_ITEM);
	}

	private static void registerModel(Item item) {
		registerModel(item, 0);
	}

	private static void registerModel(Item item, int meta) {
		registerModel(item, meta, "");
	}

	private static void registerModel(Item item, int meta, String postfix) {
		if (item == null)
			return;
		ModelLoader.setCustomModelResourceLocation(item, meta,
				new ModelResourceLocation(item.getRegistryName() + postfix, "inventory"));
	}

	@Override
	public void preInit() {
		super.preInit();
		MinecraftForge.EVENT_BUS.register(new ClientTicks());
		RenderingRegistry.registerEntityRenderingHandler(EntityReverseFallingBlock.class,
				new IRenderFactory<EntityReverseFallingBlock>() {

					@Override
					public Render<EntityReverseFallingBlock> createRenderFor(RenderManager manager) {
						return new RenderReverseFallingBlock(manager);
					}
				});
		RenderingRegistry.registerEntityRenderingHandler(EntityPhantomPearl.class,
				new IRenderFactory<EntityPhantomPearl>() {

					@Override
					public Render<? super EntityPhantomPearl> createRenderFor(RenderManager manager) {
						return new RenderSnowball<EntityPhantomPearl>(manager,
								RiftsRegistry.Items.PHANTOM_PEARL,
								Minecraft.getMinecraft().getRenderItem());
					}
				});
	}

	@Override
	public void init() {
		super.init();
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
				RiftsRegistry.Items.UNIVERSAL_DYE.new UniversalDyeItemColor(),
				RiftsRegistry.Items.UNIVERSAL_DYE);
	}

	@Override
	public void postInit() {
		super.postInit();
		// Minecraft mc = Minecraft.getMinecraft();
		// Minecraft.getMinecraft().fontRenderer = new
		// CustomFontRenderer(mc.gameSettings,
		// new ResourceLocation("textures/font/ascii.png"), mc.renderEngine,
		// false);
	}

	private Minecraft mc = Minecraft.getMinecraft();
	private ShaderGroup oldGroup;
	private ResourceLocation rift = new ResourceLocation(ModInfo.ID, "shaders/post/desaturate.json");

	@Override
	public void setRiftShader(boolean enabled) {
		EntityRenderer renderer = mc.entityRenderer;
		if (enabled) {
			this.oldGroup = renderer.getShaderGroup();
			renderer.loadShader(rift);
		} else if (this.oldGroup != null) {
			renderer.loadShader(new ResourceLocation(this.oldGroup.getShaderGroupName()));
			this.oldGroup = null;
		} else {
			renderer.stopUseShader();
			this.oldGroup = null;
		}
	}

	@Override
	public void addVisitedToPersistance(DimensionType type) {
		PersistencyHandler.addVisited(type);
	}

	@Override
	public boolean hasVisitedFromPersistance(DimensionType type) {
		return PersistencyHandler.hasVisited(type);
	}

}