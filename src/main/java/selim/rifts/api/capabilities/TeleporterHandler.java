package selim.rifts.api.capabilities;

import net.minecraft.tileentity.TileEntity;

public class TeleporterHandler implements ITeleportHandler {

	@Override
	public boolean canTeleport() {
		return true;
	}

	@Override
	public void preTeleport(TileEntity te) {}

	@Override
	public void postTeleport(TileEntity te) {}

}
