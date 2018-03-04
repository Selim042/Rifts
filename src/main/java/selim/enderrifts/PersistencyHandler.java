package selim.enderrifts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.DimensionType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class PersistencyHandler {

	private static final List<DimensionType> VISITED_TYPES = new LinkedList<DimensionType>();
	private static final HashMap<String, DimensionType> TYPE_CACHE = new HashMap<String, DimensionType>();
	private static boolean initalized = false;
	private static File file;
	// public static int persistentLevel;
	// public static boolean ignore = false;

	public static void init() {
		if (initalized)
			return;
		initalized = true;
		// if (!ConfigHandler.usePersistentData)
		// return;
		String home = System.getProperty("user.home");
		String os = System.getProperty("os.name");
		if (os.startsWith("Windows"))
			home += "\\AppData\\Roaming\\.minecraft\\ender_rifts";
		else if (os.startsWith("Mac"))
			home += "/Library/Application Support/minecraft/ender_rifts";
		else
			home += "/.minecraft/ender_rifts";
		File dir = new File(home);
		if (!dir.exists())
			dir.mkdirs();

		File readMe = new File(home, "README.txt");
		if (!readMe.exists()) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(readMe))) {
				readMe.createNewFile();
				writer.write(
						"This is a folder to store information to persist for the Ender Rifts mod.");
				writer.newLine();
				writer.write(
						"The information stored here is accessible to players using the Ender Rifts mod");
				writer.write(
						"to allow them to skip part of the progression if they have already completed it.");
				writer.newLine();
				writer.write(
						"This information should be accessible no matter what modpack you use or where ");
				writer.write("Minecraft is being run from.");
				writer.newLine();
				writer.newLine();
				// TODO: Add the config option mentioned below
				writer.write("You can disable this functionality in the Ender Rifts config.");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Minecraft mc = Minecraft.getMinecraft();
		String uuid = EntityPlayer.getUUID(mc.player.getGameProfile()).toString();
		file = new File(home, uuid);
		for (DimensionType type : DimensionType.values())
			TYPE_CACHE.put(type.getName(), type);
		if (file.exists() && !file.isDirectory()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String l = reader.readLine();
				if (l != null) {
					if (TYPE_CACHE.containsKey(l)) {
						VISITED_TYPES.add(TYPE_CACHE.get(l));
					}
					l = reader.readLine();
				}
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void addVisited(DimensionType type) {
		if (!initalized || VISITED_TYPES.contains(type))
			return;
		VISITED_TYPES.add(type);
		save();
	}

	public static boolean hasVisited(DimensionType type) {
		if (!initalized)
			return false;
		return VISITED_TYPES.contains(type);
	}

	public static void save() {
		System.out.println("saving");
		Minecraft mc = Minecraft.getMinecraft();
		if (/*
			 * !ConfigHandler.usePersistentData || level <= persistentLevel ||
			 */
		mc.player == null/* || mc.player.capabilities.isCreativeMode */)
			return;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("created new file");
			}
			for (DimensionType type : VISITED_TYPES) {
				writer.write(type.getName());
				System.out.println(type.getName());
				writer.newLine();
			}
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}