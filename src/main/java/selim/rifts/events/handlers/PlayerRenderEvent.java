package selim.rifts.events.handlers;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.rifts.layers.LayerGlint;
import selim.rifts.layers.LayerKoala;

public class PlayerRenderEvent {

	private boolean addedLayers = false;
	private boolean isRenderingAqua = false;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerRender(RenderPlayerEvent.Pre event) {
		if (!addedLayers) {
			RenderPlayer render = event.getRenderer();
			render.addLayer(new LayerGlint(render, true));
			render.addLayer(new LayerKoala(render, false));
			// render.addLayer(new EnderEyesLayer(render, true));
			// render.addLayer(new WingLayer(render, true));
			addedLayers = true;
		}
		// if (event.getEntityPlayer().getName().equals("AquaticAbyss")) {
		// RenderPlayer render = event.getRenderer();
		// render.getRenderManager().setRenderShadow(false);
		// GL11.glPushMatrix();
		// // GL11.glEnable(GL11.GL_BLEND);
		// // GL11.glEnable(GL11.GL_TEXTURE_2D);
		// // GL11.glEnable(GL11.GL_ALPHA_TEST);
		// // GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		// GL11.glPopMatrix();
		// if (!isRenderingAqua) {
		// List<LayerRenderer> list =
		// ReflectionHelper.getPrivateValue(RenderLivingBase.class,
		// render, new String[] { "i", "field_177097_h", "layerRenderers" });
		// LayerRenderer remove = null;
		// for (LayerRenderer layer : list)
		// if (layer instanceof LayerElytra) {
		// remove = layer;
		// break;
		// }
		// list.remove(remove);
		// isRenderingAqua = true;
		// }
		// }
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerPostRender(RenderPlayerEvent.Post event) {
		if (event.getEntityPlayer().getName().equals("Selim_042")) {

		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onHandRender(RenderHandEvent event) {

	}

}