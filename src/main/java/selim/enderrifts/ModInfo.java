package selim.enderrifts;

import net.minecraft.util.math.Vec3d;
import selim.enderrifts.compat.BaublesHelper;
import selim.enderrifts.compat.EnderStorageHelper;

// TODO: Pre-1.13 release, give to Aqua early for spotlight
// TODO: Contact Soaryn about testing
public class ModInfo {

	public static final String ID = "enderrifts";
	public static final String NAME = "Ender Rifts";
	public static final String VERSION = "0.0.1";
	public static final String PROXY_SERVER = "selim.enderrifts.proxy.CommonProxy";
	public static final String PROXY_CLIENT = "selim.enderrifts.proxy.ClientProxy";
	public static final String DEPENDENCIES = "after:" + BaublesHelper.ID + ";after:"
			+ EnderStorageHelper.ID;
	public static final String[] DESCRIPTION = {
			"This mod expands on a concept originally described by Dinnerbone.",
			"The idea was first expanded upon here by oroJefe: https://mine.guide/minecraft-ideas/new-minecraft-dimension." };
	public static final String[] CREDITS = { "Selim_042", "code and textures", "koalaclumsy", "textures",
			"Minecraft Suggestions Subreddit", "inspiration" };
	public static final Vec3d PURPLE_COLOR = new Vec3d(27.0 / 255, 0.0 / 255, 49.0 / 255);

}