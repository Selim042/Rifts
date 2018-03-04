package selim.enderrifts.events.handlers;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import selim.enderrifts.PersistencyHandler;

public class ClientTicks {

	@SubscribeEvent
	public void clientTickEnd(ClientTickEvent event) {
		if (event.phase == Phase.END) {
			Minecraft mc = Minecraft.getMinecraft();
			if (mc.player != null)
				PersistencyHandler.init();
		}
	}
}
