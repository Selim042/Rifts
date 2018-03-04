package selim.enderrifts.layers;

import java.util.UUID;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.enderrifts.ModInfo;

@SideOnly(Side.CLIENT)
public class LayerKoala implements LayerRenderer<AbstractClientPlayer> {

	private static final ResourceLocation koalaTexture = new ResourceLocation(ModInfo.ID,
			"textures/entities/koala_backpack.png");

	private RenderPlayer render;
	private boolean koalaOnly;
	// private ModelRenderer koalaRenderer;
	private ModelKoalaBackpack koalaBackpack;

	public LayerKoala(RenderPlayer render, boolean koalaOnly) {
		this.render = render;
		this.koalaOnly = koalaOnly;
		// this.koalaRenderer = new ModelRendererKoala(render.getMainModel(),
		// 24, 0);
		// this.koalaRenderer.addBox(0, 0, 0, 16, 16, 16, 0.0f);
		this.koalaBackpack = new ModelKoalaBackpack();

	}

	@Override
	public void doRenderLayer(AbstractClientPlayer entity, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (/*
			 * !entity.isInvisible() &&
			 */(entity.getUniqueID().equals(UUID.fromString("4a1dfd47-e7ba-48ff-893e-a741e9077fcf"))
				|| !koalaOnly)) {
			render.bindTexture(koalaTexture);
			GlStateManager.pushMatrix();
			GlStateManager.translate(-0.5, 0, 0);
			// this.koalaRenderer.render(0.733f);
			this.koalaBackpack.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw,
					headPitch, (float) (scale * 1.5));
			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}