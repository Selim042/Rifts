package selim.enderrifts.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import selim.enderrifts.tiles.TileEntityBound;

public class GuiHandler implements IGuiHandler {

	public static final int RIFT_EYE = 0;
	public static final int BOUND_BLOCK = 1;

	protected static MainScreen lastScreen;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case 0: // Rift Eye
			return null;
		case 1: // bound block w/ fuel
			TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
			if (te == null || !(te instanceof TileEntityBound))
				return null;
			return new ContainerBoundInventory(player.inventory, (TileEntityBound) te);
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
		case 0: // Rift Eye
			if (lastScreen == null)
				lastScreen = new MainScreen();
			return lastScreen;
		case 1: // bound block w/ fuel
			TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
			if (te == null || !(te instanceof TileEntityBound))
				return null;
			return new BoundInventoryScreen(
					new ContainerBoundInventory(player.inventory, (TileEntityBound) te));
		default:
			return null;
		}
	}

	public static MainScreen getOpenScreen() {
		if (lastScreen == null)
			lastScreen = new MainScreen();
		else
			lastScreen.reloadCategories();
		return lastScreen;
	}

}
