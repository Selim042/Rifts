package selim.enderrifts.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * koala_backpack - Undefined Created using Tabula 7.0.0
 */
public class ModelKoalaBackpack extends ModelBase {

	public ModelRenderer body;
	public ModelRenderer left;
	public ModelRenderer right;
	public ModelRenderer main;
	public ModelRenderer nose;
	public ModelRenderer left_1;
	public ModelRenderer right_1;

	public ModelKoalaBackpack() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.main = new ModelRenderer(this, 20, 0);
		this.main.setRotationPoint(1.0F, -3.0F, 0.0F);
		this.main.addBox(0.0F, 0.0F, 0.0F, 4, 4, 4, 0.0F);
		this.right = new ModelRenderer(this, 16, 0);
		this.right.setRotationPoint(6.0F, 7.0F, -2.0F);
		this.right.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
		this.right_1 = new ModelRenderer(this, 16, 0);
		this.right_1.setRotationPoint(6.1F, 7.2F, -2.0F);
		this.right_1.addBox(0.0F, -7.0F, 0.0F, 1, 1, 3, 0.0F);
		this.left_1 = new ModelRenderer(this, 16, 0);
		this.left_1.setRotationPoint(-0.9F, 7.2F, -2.0F);
		this.left_1.addBox(0.0F, -7.0F, 0.0F, 1, 1, 3, 0.0F);
		this.body = new ModelRenderer(this, 0, 0);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addBox(0.0F, 0.0F, 0.0F, 6, 8, 2, 0.0F);
		this.left = new ModelRenderer(this, 16, 0);
		this.left.setRotationPoint(-1.0F, 7.0F, -2.0F);
		this.left.addBox(0.0F, 0.0F, 0.0F, 1, 1, 3, 0.0F);
		this.nose = new ModelRenderer(this, 14, 0);
		this.nose.setRotationPoint(2.5F, -4.0F, 0.0F);
		this.nose.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, float scale) {
		this.main.render(scale);
		this.right.render(scale);
		this.right_1.render(scale);
		this.left_1.render(scale);
		this.body.render(scale);
		this.left.render(scale);
		this.nose.render(scale);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
