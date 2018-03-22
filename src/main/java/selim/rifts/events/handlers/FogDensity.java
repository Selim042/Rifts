package selim.rifts.events.handlers;

import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.rifts.world.DimensionRift;

public class FogDensity {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onFogDensityRender(EntityViewRenderEvent.FogDensity event) {
		// Minecraft.getMinecraft().player
		// .sendMessage(new TextComponentString("dim: " +
		// event.getEntity().world + ", isRift: "
		// + DimensionRift.isTestDimension(event.getEntity().world)));
		if (DimensionRift.isTestDimension(event.getEntity().world)) {
			// Minecraft.getMinecraft().player
			// .sendMessage(new TextComponentString("dim: " +
			// event.getEntity().world + ", isRift: "
			// + DimensionRift.isTestDimension(event.getEntity().world)));
			event.setDensity(0.25f);
			event.setCanceled(true);
		}
	}

}
