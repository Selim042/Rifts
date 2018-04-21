package selim.rifts.api.capabilities;

import net.minecraft.tileentity.TileEntity;

public interface ITeleportHandler {

	public boolean canTeleport();

	public void preTeleport(TileEntity te);

	public void postTeleport(TileEntity te);

}
