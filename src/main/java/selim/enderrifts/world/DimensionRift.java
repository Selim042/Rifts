package selim.enderrifts.world;

import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import selim.enderrifts.ModInfo;

public class DimensionRift {

	public static final int DIMENSION_ID = DimensionManager.getNextFreeDimId();
	public static final String DIMENSION_NAME = "rift";
	public static DimensionType DIMENSION_TYPE;

	public static void mainRegistry() {
		DIMENSION_TYPE = DimensionType.register(ModInfo.ID + ":" + DIMENSION_NAME.toLowerCase(),
				"_" + DIMENSION_NAME.toLowerCase(), DIMENSION_ID, WorldProviderRift.class, false);
		DimensionManager.registerDimension(DIMENSION_ID, DimensionRift.DIMENSION_TYPE);
	}

	public static boolean isTestDimension(World world) {
		return isTestDimension(world.provider.getDimension());
	}

	public static boolean isTestDimension(int id) {
		return id == DimensionRift.DIMENSION_ID;
	}
}
