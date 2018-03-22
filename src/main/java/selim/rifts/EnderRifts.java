package selim.rifts;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.DimensionType;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import selim.rifts.api.RiftGenerator;
import selim.rifts.commands.TestCommand;
import selim.rifts.config.Config;
import selim.rifts.creativetabs.RiftMainTab;
import selim.rifts.proxy.CommonProxy;
import selim.rifts.riftgenerators.DefaultRiftGenerator;

@Mod(useMetadata = false, modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION,
		dependencies = ModInfo.DEPENDENCIES)
public class EnderRifts {

	@Mod.Instance(value = ModInfo.ID)
	public static EnderRifts instance;
	@SidedProxy(clientSide = ModInfo.PROXY_CLIENT, serverSide = ModInfo.PROXY_SERVER)
	public static CommonProxy proxy;
	public static final Logger LOGGER = LogManager.getLogger(ModInfo.ID);

	public static CreativeTabs mainTab = new RiftMainTab();

	private static int modGuiIndex = 0;
	public static final int ItemInventoryGuiIndex = modGuiIndex++;

	static {
		if (!FluidRegistry.isUniversalBucketEnabled()) {
			LOGGER.log(Level.INFO, "Forge Universal Bucket is not enabled.");
			LOGGER.log(Level.INFO, "Now enabling the Forge Universal Bucket.");
			FluidRegistry.enableUniversalBucket();
		}
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Config.init(event.getSuggestedConfigurationFile());

		// Register stuff
		// RiftsRegistry.registerItems();
		// RiftsRegistry.registerBlocks();
		// RiftsRegistry.registerEnchantments();
		// RiftsRegistry.registerEntities();
		// RiftsRegistry.registerPotions();
		RiftRegistry.registerDimensions();

		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// Register event handlers
		RiftRegistry.registerEventHandlers();

		// Register custom dispenser behavior
		RiftRegistry.registerDispenserBehavior();

		// Register recipes
		// MyRecipies.addRecipies();

		// Register world generators
		RiftRegistry.registerWorldGenerators();

		// Register gui handlers
		RiftRegistry.registerGuiHandlers();

		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();

		notifyMissingGenerators();
	}

	private void notifyMissingGenerators() {
		boolean printedFirst = false;
		for (DimensionType type : DimensionType.values()) {
			RiftGenerator[] gens = RiftGenerator.getGenerators(type);
			if (gens.length == 0 || (gens.length == 1
					&& gens[0].getRegistryName().getResourceDomain().equalsIgnoreCase(ModInfo.ID)
					&& !(gens[0] instanceof DefaultRiftGenerator))) {
				if (!printedFirst) {
					LOGGER.info("There are some dimension types that do not have RiftGenerators:");
					printedFirst = true;
				}
				LOGGER.info(" - " + type.getName() + (gens.length == 0 ? " (built-in)" : ""));
			}
		}
		if (printedFirst) {
			LOGGER.info(
					"This is not necessary, but may improve the user experience with the EnderRifts mod.");
			LOGGER.info(
					"Any dimensions marked with \"built-in\" have generators in EnderRifts that can be moved to the dimension's host mod.");
		}
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
//		event.registerServerCommand(new TestCommand());
	}

}