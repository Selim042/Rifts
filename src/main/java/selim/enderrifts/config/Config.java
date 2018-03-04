package selim.enderrifts.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public final class Config {

	// public static boolean blockShifter;
	// public static boolean mobShifter;
	// public static boolean scythe;
	// public static boolean moreStoneTools;
	// public static boolean netherrackTools;
	//
	// public static List<String> blockShifterBlacklist = new
	// ArrayList<String>();
	// public static List<String> mobShifterBlacklist = new ArrayList<String>();
	//
	// public static boolean glowstoneDirt;
	// public static boolean blastproofGlass;
	// public static boolean ropeLadder;
	// public static boolean weatherSensor;
	// public static boolean soulSandstone;
	// public static boolean bud = false;
	// public static boolean advancedCauldron = false;
	// public static boolean accumulator = false;
	// public static boolean coloredBeds;
	// public static boolean magmaBrick;
	// public static boolean moreWalls;
	// public static boolean invertedLamp = false;
	// public static boolean cheerlight;
	// public static int cheerlightInterval;
	// public static boolean moreChests = false;
	//
	// public static boolean vorpal;
	// public static boolean wither;
	// public static boolean amplifiy;
	// public static boolean feller;
	// public static boolean magmaWalker;
	// public static boolean warping;
	// public static boolean banishing;
	// public static boolean uncivilized;
	//
	// public static boolean penguin;
	// public static boolean explorer;
	//
	// public static boolean trample = false;
	// public static boolean chainArmor;
	// public static boolean saddle;
	// public static boolean horseArmor;
	// public static boolean elytra;
	// public static boolean rituals;

	public static void init(File file) {
		Configuration config = new Configuration(file);

		// Items
		// blockShifter = config.getBoolean("enableBlockShifter", "items", true,
		// "Enable Block Shifter?");
		// mobShifter = config.getBoolean("enableMobShifter", "items", true,
		// "Enable Mob Shifter?");
		// scythe = config.getBoolean("enableScythe", "items", true, "Enable
		// Scythe?");
		// moreStoneTools = config.getBoolean("enableAddionalStoneTools",
		// "items", true,
		// "Enable Granite, Diorite, and Andesite Tools?");
		// netherrackTools = config.getBoolean("enableNetherrackTools", "items",
		// true, "Enable Netherrack Tools?");

		// Item Configs
		// Block[] blockBlacklistB = { Blocks.BEDROCK, Blocks.END_PORTAL,
		// Blocks.END_PORTAL_FRAME, Blocks.END_GATEWAY,
		// Blocks.PORTAL };
		// for (int i = 0; i < blockBlacklistB.length; i++) {
		// if (blockBlacklistB[i] != null &&
		// blockBlacklistB[i].getRegistryName() != null) {
		// blockShifterBlacklist.add(blockBlacklistB[i].getRegistryName().toString());
		// }
		// }
		// String[] configBlacklist =
		// config.getStringList("blockShifterBlacklist", "itemConfigs",
		// blockShifterBlacklist.toArray(new
		// String[blockShifterBlacklist.size()]),
		// "Block blacklist for Block Shifter. Format: <modid>:<blockID>");
		// blockShifterBlacklist.clear();
		// for (int i = 0; i < configBlacklist.length; i++) {
		// blockShifterBlacklist.add(configBlacklist[i]);
		// }
		// // String[] mobBlacklist = {};
		// // mobShifterBlacklist = config.getStringList("mobShifterBlacklist",
		// // "itemConfigs", mobBlacklist, "Mob blacklist for Mob Shifter.
		// Format:
		// // <modid>:<mobID>");

		// Blocks
		// glowstoneDirt = config.getBoolean("enableGlowstoneDirt", "blocks",
		// true,
		// "Enable Glowstone Dirt and Glowstone Grass?");
		// blastproofGlass = config.getBoolean("enableBlastproofGlass",
		// "blocks", true, "Enable Blastproof Glass?");
		// ropeLadder = config.getBoolean("enableRopeLadder", "blocks", true,
		// "Enable Rope Ladders?");
		// weatherSensor = config.getBoolean("enableWeatherSensor", "blocks",
		// true, "Enable Weather Sensor?");
		// soulSandstone = config.getBoolean("enableSoulSandstone", "blocks",
		// true, "Enable Soul Sandstone?");
		// // bud = config.getBoolean("enableBUD", "blocks", true, "Enable
		// BUD?");
		// // advancedCauldron = config.getBoolean("enableAdvancedCauldron",
		// // "blocks", true, "Enable Advanced Cauldron?");
		// // accumulator = config.getBoolean("enableAccumulator", "blocks",
		// true,
		// // "Enable Redstone Accumulator?");
		// coloredBeds = config.getBoolean("enableColoredBeds", "blocks", true,
		// "Enable Colored Beds?");
		// magmaBrick = config.getBoolean("enableMagmaBrick", "blocks", true,
		// "Enable Magma Bricks?");
		// moreWalls = config.getBoolean("enableMoreWalls", "blocks", true,
		// "Enable More Walls?");
		// // invertedLamp = config.getBoolean("enableInvertedLamp", "blocks",
		// // true, "Enable Inverted Lamp?");
		// cheerlight = config.getBoolean("enableCheerlight", "blocks", true,
		// "Enable the Cheerlight?");
		// cheerlightInterval = config.getInt("cheerlightInterval", "blocks",
		// 30, 30, 600,
		// "How often should the Cheerlight update, in seconds?");
		// // moreChests = config.getBoolean("enableMoreChests", "blocks", true,
		// // "Enable More Chests?");

		// Enchants
		// vorpal = config.getBoolean("enableVorpal", "enchantments", true,
		// "Enable Vorpal enchantment?");
		// wither = config.getBoolean("enableWither", "enchantments", true,
		// "Enable Withering enchantment?");
		// amplifiy = config.getBoolean("enableAmplfiy", "enchantments", true,
		// "Enable Amplify enchantment?");
		// // feller = config.getBoolean("enableFeller", "enchantments", true,
		// // "Enable Feller enchantment?");
		// magmaWalker = config.getBoolean("enableMagmaWalker", "enchantments",
		// true,
		// "Enable Magma Walker enchantment?");
		// warping = config.getBoolean("enableWarping", "enchantments", true,
		// "Enable Warping enchantment?");
		// banishing = config.getBoolean("enableBanishing", "enchantments",
		// true,
		// "Enable Banishing enchantment?");
		// uncivilized = config.getBoolean("enableUncivilized", "enchantments",
		// true,
		// "Enable Uncivilized enchantment?");

		// Entities
		// penguin = config.getBoolean("enablePenguin", "entities", true,
		// "Enable Penguins?");
		// explorer = config.getBoolean("enableExplorer", "entities", true,
		// "Enable Explorer villagers?");

		// Misc
		// // trample = config.getBoolean("enablePathTrampling", "misc", true,
		// // "Enable path trampling?");
		// chainArmor = config.getBoolean("enableChainArmorRecipe", "misc",
		// true,
		// "Enable crafting recipe for Chain Armor?");
		// saddle = config.getBoolean("enableSaddleRecipe", "misc", true,
		// "Enable crafting recipe for Saddles?");
		// horseArmor = config.getBoolean("enableHorseArmorRecipe", "misc",
		// true,
		// "Enable crafting recipe for Horse Armor?");
		// elytra = config.getBoolean("enableElytraRecipe", "misc", true,
		// "Enable crafting recipe for Elytras?");
		// rituals = config.getBoolean("enableRituals", "misc", true,
		// "Enable rituals using the Ritual Stone?");

		if (config.hasChanged()) {
			config.save();
		}
	}

}