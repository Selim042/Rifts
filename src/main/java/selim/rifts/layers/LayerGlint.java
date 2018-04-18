package selim.rifts.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerGlint implements LayerRenderer<EntityLivingBase> {

	RenderLivingBase<?> render;
	protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES = new ResourceLocation(
			"textures/misc/enchanted_item_glint.png");
	private boolean selimOnly;

	public LayerGlint(RenderLivingBase<?> render2, boolean selimOnly) {
		this.render = render2;
		this.selimOnly = selimOnly;
	}

	// private final Random rand = new Random();
	// private float hue = 0.0f;

	@Override
	public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (!entity.isInvisible() && (entity.getName().equals("Selim_042") || !selimOnly)) {
			// NBTTagCompound nbt = new NBTTagCompound();
			// entity.writeToNBT(nbt);
			// float colorR = nbt.getFloat("colorR");
			// if (colorR == 1.0F) {
			// nbt.setFloat("colorR", 0.0F);
			// float colorG = nbt.getFloat("colorG");
			// if (colorG == 1.0F) {
			// nbt.setFloat("colorG", 0.0F);
			// float colorB = nbt.getFloat("colorB");
			// if (colorB == 1.0F) {
			// nbt.setFloat("colorB", 0.0F);
			// }
			// else {
			// nbt.setFloat("colorB", colorB + 0.001F);
			// }
			// }
			// else {
			// nbt.setFloat("colorG", colorG + 0.001F);
			// }
			// }
			// else {
			// nbt.setFloat("colorR", colorR + 0.001F);
			// }
			// entity.readFromNBT(nbt);
			float colorR = 0.4F;
			float colorG = 0.2F;
			float colorB = 0.4F;
			float alpha = 0.33F;
			// hue += 0.75f/200;
			// Color color = Color.getHSBColor(hue, 1.0f, 0.5f);
			// System.out.println(Color.HSBtoRGB(hue, 1.0f, 0.5f));
			for (int i = 0; i < 1; i++) {
				renderEnchantedGlint(render, entity, render.getMainModel(), limbSwing, limbSwingAmount,
						partialTicks, ageInTicks, netHeadYaw, headPitch, scale, colorR, colorB, colorG,
						alpha);
				// renderEnchantedGlint(render, entity, render.getMainModel(),
				// limbSwing, limbSwingAmount,
				// partialTicks, ageInTicks, netHeadYaw, headPitch, scale,
				// color.getRed(),
				// color.getBlue(), color.getGreen(), alpha);
			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

	public static void renderEnchantedGlint(RenderLivingBase<?> render, EntityLivingBase entity,
			ModelBase model, float limbSwing, float limbSwingAmount, float p_188364_5_,
			float p_188364_6_, float p_188364_7_, float p_188364_8_, float p_188364_9_, float r, float g,
			float b, float a) {
		float f = (float) entity.ticksExisted + p_188364_5_;
		render.bindTexture(ENCHANTED_ITEM_GLINT_RES);
		GlStateManager.enableBlend();
		GlStateManager.depthFunc(514);
		GlStateManager.depthMask(false);
		GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);

		for (int i = 0; i < 2; ++i) {
			GlStateManager.disableLighting();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR,
					GlStateManager.DestFactor.ONE);
			GlStateManager.enableAlpha();
			GlStateManager.color(r, g, b, a);
			GlStateManager.matrixMode(5890);
			GlStateManager.loadIdentity();
			GlStateManager.scale(0.33333334F, 0.33333334F, 0.33333334F);
			GlStateManager.rotate(30.0F - (float) i * 60.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(0.0F, f * (0.001F + (float) i * 0.003F) * 20.0F, 0.0F);
			GlStateManager.matrixMode(5888);
			model.render(entity, limbSwing, limbSwingAmount, p_188364_6_, p_188364_7_, p_188364_8_,
					p_188364_9_);
		}

		GlStateManager.matrixMode(5890);
		GlStateManager.loadIdentity();
		GlStateManager.matrixMode(5888);
		GlStateManager.enableLighting();
		GlStateManager.depthMask(true);
		GlStateManager.depthFunc(515);
		GlStateManager.disableBlend();
	}

	public static void renderEnchantedGlint(RenderLivingBase<?> p_188364_0_,
			EntityLivingBase p_188364_1_, ModelBase model, float p_188364_3_, float p_188364_4_,
			float p_188364_5_, float p_188364_6_, float p_188364_7_, float p_188364_8_,
			float p_188364_9_) {
		renderEnchantedGlint(p_188364_0_, p_188364_1_, model, p_188364_3_, p_188364_4_, p_188364_5_,
				p_188364_6_, p_188364_7_, p_188364_8_, p_188364_9_, 0.38F, 0.19F, 0.608F, 1.0F);
	}
}
