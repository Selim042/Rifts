package selim.rifts.events.handlers;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.rifts.EnderRifts;
import selim.rifts.world.DimensionRift;

public class ShaderHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		EnderRifts.LOGGER.info("a");
		if (event.getWorld().isRemote && DimensionRift.isTestDimension(event.getWorld())) {
			EnderRifts.LOGGER.info("enabled");
			EnderRifts.proxy.setRiftShader(true);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		EnderRifts.LOGGER.info("b");
		if (event.getWorld().isRemote && DimensionRift.isTestDimension(event.getWorld())) {
			EnderRifts.LOGGER.info("disabled");
			EnderRifts.proxy.setRiftShader(false);
		}
	}

}
