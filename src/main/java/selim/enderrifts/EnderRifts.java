package selim.enderrifts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.DimensionType;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import selim.enderrifts.api.RiftGenerator;
import selim.enderrifts.commands.TestCommand;
import selim.enderrifts.config.Config;
import selim.enderrifts.creativetabs.RiftMainTab;
import selim.enderrifts.proxy.CommonProxy;
import selim.enderrifts.riftgenerators.DefaultRiftGenerator;

@Mod(useMetadata = false, modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION,
		dependencies = ModInfo.DEPENDENCIES)
public class EnderRifts {

	@Instance(value = ModInfo.ID)
	public static EnderRifts instance;
	@SidedProxy(clientSide = ModInfo.PROXY_CLIENT, serverSide = ModInfo.PROXY_SERVER)
	public static CommonProxy proxy;
	public static final Logger LOGGER = LogManager.getLogger(ModInfo.ID);

	public static CreativeTabs mainTab = new RiftMainTab();

	private static int modGuiIndex = 0;
	public static final int ItemInventoryGuiIndex = modGuiIndex++;

	static {
		if (!FluidRegistry.isUniversalBucketEnabled()) {
			System.out.println("Forge Universal Bucket is not enabled.");
			System.out.println("Now enabling the Forge Universal Bucket.");
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
		RiftsRegistry.registerDimensions();

		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// Register event handlers
		RiftsRegistry.registerEventHandlers();

		// Register custom dispenser behavior
		RiftsRegistry.registerDispenserBehavior();

		// Register recipes
		// MyRecipies.addRecipies();

		// Register world generators
		RiftsRegistry.registerWorldGenerators();

		// Register gui handlers
		RiftsRegistry.registerGuiHandlers();

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
		event.registerServerCommand(new TestCommand());
	}

}