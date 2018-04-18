package selim.rifts.proxy;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.PersistencyHandler;
import selim.rifts.RiftRegistry;
import selim.rifts.api.docs.DocCategory;
import selim.rifts.api.docs.DocEntry;
import selim.rifts.api.docs.IDocEntryResource;
import selim.rifts.api.docs.pages.DocPageText;
import selim.rifts.blocks.BlockNewSlab;
import selim.rifts.blocks.BlockWillowSapling;
import selim.rifts.entities.EntityPhantomCart;
import selim.rifts.entities.EntityPhantomPearl;
import selim.rifts.entities.EntityReverseFallingBlock;
import selim.rifts.entities.render.RenderPhantomCart;
import selim.rifts.entities.render.RenderReverseFallingBlock;
import selim.rifts.events.handlers.AmethystTooltip;
import selim.rifts.events.handlers.ClientTicks;
import selim.rifts.events.handlers.EyeTooltipHandler;
import selim.rifts.items.ItemUniversalDye.UniversalDyeItemColor;
import selim.rifts.render.TileRiftPortalRenderer;
import selim.rifts.tiles.TileRiftPortal;
import selim.rifts.utils.MiscUtils;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		Class<RiftRegistry.Items> clazz = RiftRegistry.Items.class;
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field f : fields) {
				Object obj = f.get(null);
				if (obj == null || obj == Items.AIR)
					continue;
				// System.out.print("BLERP:" + f.getName() + "," +
				// obj.getClass().getName() + ":" + obj);
				if (obj instanceof Item && ((Item) obj).getHasSubtypes()) {
					// System.out.println("Item, with subtypes. Not
					// registered.");
					continue;
				} else if (obj instanceof ItemBlock) {
					// System.out.println("ItemBlock. Registered.");
					registerModel((ItemBlock) obj);
				} else if (!f.getName().equals("DEBUG_ITEM")) {
					// System.out.println("Item, not DEBUG_ITEM. Registered.");
					registerModel((Item) obj);
				} else
					System.out.println("Failed to register: " + f.getName());
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			EnderRifts.LOGGER.error("An " + e.getClass().getName()
					+ " was thrown when attempting to load ItemBlock models.");
			e.printStackTrace();
		}
		for (int i = 0; i < 17; i++)
			registerModel(RiftRegistry.Items.UNIVERSAL_DYE, i);
		registerModel(RiftRegistry.Items.BARITE, 0);
		registerModel(RiftRegistry.Items.BARITE, 1, "_smooth");
		registerModel(RiftRegistry.Items.BARITE, 2, "_brick");

		ModelLoader.setCustomStateMapper(RiftRegistry.Blocks.WILLOW_LEAVES, NormalStateMapper.NORMAL);
		ModelLoader.setCustomStateMapper(RiftRegistry.Blocks.OPAQUE_AIR, NormalStateMapper.NORMAL);
		ModelLoader.setCustomStateMapper(RiftRegistry.Blocks.WILLOW_SAPLING,
				new IgnoreStateMapper(BlockWillowSapling.STAGE));
		IgnoreStateMapper slabIgnore = new IgnoreStateMapper(BlockNewSlab.VARIANT);
		IgnoreStateMapper doorIngore = new IgnoreStateMapper(BlockDoor.POWERED);
		for (Field f : RiftRegistry.Blocks.class.getFields()) {
			try {
				Object obj = f.get(null);
				if (obj instanceof BlockNewSlab)
					ModelLoader.setCustomStateMapper((BlockNewSlab) obj, slabIgnore);
				else if (obj instanceof BlockDoor)
					ModelLoader.setCustomStateMapper((BlockDoor) obj, doorIngore);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				EnderRifts.LOGGER.error("An " + e.getClass().getName()
						+ " was thrown when attempting to set " + f.getName() + " StateMapper.");
				e.printStackTrace();
			}
		}

		if (MiscUtils.isDevEnvironment())
			registerModel(RiftRegistry.Items.DEBUG_ITEM);
	}

	protected static class NormalStateMapper implements IStateMapper {

		public static final NormalStateMapper NORMAL = new NormalStateMapper();

		@Override
		public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block block) {
			ModelResourceLocation defLoc = new ModelResourceLocation(block.getRegistryName(), "normal");
			HashMap<IBlockState, ModelResourceLocation> locs = new HashMap<IBlockState, ModelResourceLocation>();
			for (IBlockState state : block.getBlockState().getValidStates())
				locs.put(state, defLoc);
			return locs;
		}
	}

	protected static class IgnoreStateMapper implements IStateMapper {

		private final IProperty<?>[] properties;

		public IgnoreStateMapper(IProperty<?>... properties) {
			this.properties = properties;
		}

		@Override
		public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block block) {
			HashMap<IBlockState, ModelResourceLocation> locs = new HashMap<IBlockState, ModelResourceLocation>();
			for (IBlockState state : block.getBlockState().getValidStates()) {
				String loc = "";
				for (IProperty<?> p : state.getPropertyKeys()) {
					if (arrContains(this.properties, p))
						continue;
					if (!loc.equals(""))
						loc += ",";
					loc += p.getName() + "=" + state.getValue(p);
				}
				locs.put(state, new ModelResourceLocation(block.getRegistryName(), loc));
			}
			return locs;
		}
	}

	private static <T> boolean arrContains(T[] arr, T val) {
		for (T t : arr)
			if (t != null && t.equals(val))
				return true;
		return false;
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
//		RenderingRegistry.registerEntityRenderingHandler(EntityPhantomPearl.class,
//				new IRenderFactory<EntityPhantomPearl>() {
//
//					@Override
//					public Render<? super EntityPhantomPearl> createRenderFor(RenderManager manager) {
//						return new RenderSnowball<EntityPhantomPearl>(manager,
//								RiftRegistry.Items.PHANTOM_PEARL,
//								Minecraft.getMinecraft().getRenderItem());
//					}
//				});
		RenderingRegistry.registerEntityRenderingHandler(EntityPhantomCart.class,
				new IRenderFactory<EntityPhantomCart>() {

					@Override
					public Render<? super EntityPhantomCart> createRenderFor(RenderManager manager) {
						return new RenderPhantomCart(manager);
					}
				});
		ClientRegistry.bindTileEntitySpecialRenderer(TileRiftPortal.class, new TileRiftPortalRenderer());
	}

	@Override
	public void init() {
		super.init();
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(UniversalDyeItemColor.INSTANCE,
				RiftRegistry.Items.UNIVERSAL_DYE);
		// TODO: See about possibly fixing this (only updates color on chunk
		// draw)
		// try {
		// for (Field f : RiftRegistry.Items.class.getFields()) {
		// Object obj = f.get(null);
		// if (obj instanceof ItemBlock
		// && ((ItemBlock) obj).getBlock() instanceof BlockUnstableBlock) {
		// ItemBlock item = (ItemBlock) obj;
		// System.out.println("blerp:" + f.getName());
		// Minecraft.getMinecraft().getItemColors()
		// .registerItemColorHandler(UniversalDyeItemColor.INSTANCE, item);
		// Minecraft.getMinecraft().getBlockColors()
		// .registerBlockColorHandler(UniversalDyeItemColor.INSTANCE,
		// item.getBlock());
		// }
		// }
		// } catch (IllegalArgumentException | IllegalAccessException e) {
		// EnderRifts.LOGGER.error("An " + e.getClass().getName()
		// + " was thrown when attempting to set BlockUnstableBlock color.");
		// e.printStackTrace();
		// }
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

	@Override
	public void registerEventHandlers() {
		super.registerEventHandlers();
		MinecraftForge.EVENT_BUS.register(new AmethystTooltip());
		MinecraftForge.EVENT_BUS.register(new EyeTooltipHandler());
	}

}