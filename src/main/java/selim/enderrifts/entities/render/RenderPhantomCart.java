package selim.enderrifts.entities.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.enderrifts.entities.EntityPhantomCart;

@SideOnly(Side.CLIENT)
public class RenderPhantomCart extends Render<EntityPhantomCart> {

	public RenderPhantomCart(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
	}

	@Override
	public void doRender(EntityPhantomCart entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		if (entity == null || entity.getMirrorCart() == null)
			return;
		// Minecraft.getMinecraft().getRenderManager().renderEntity(entity, x,
		// y, z, entityYaw,
		// partialTicks, false);
		EntityMinecart mirrorCart = entity.getMirrorCart();
		Render<Entity> mirrorRender = Minecraft.getMinecraft().getRenderManager()
				.getEntityRenderObject(mirrorCart);
		GlStateManager.pushMatrix();
		GlStateManager.color(0.0f, 0.0f, 0.0f, entity.getFade());
		mirrorRender.doRender(mirrorCart, x, y, z, entityYaw, partialTicks);
		GlStateManager.popMatrix();
	}

	protected ResourceLocation getEntityTexture(EntityPhantomCart entity) {
		return null;
	}

}
