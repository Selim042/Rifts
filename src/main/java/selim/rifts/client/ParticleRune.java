package selim.rifts.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ParticleRune extends Particle {

	protected ParticleRune(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
	}

	@Override
	public void setRBGColorF(float particleRedIn, float particleGreenIn, float particleBlueIn) {
		super.setRBGColorF(particleRedIn, particleGreenIn, particleBlueIn);
	}

	@Override
	public void setAlphaF(float alpha) {
		super.setAlphaF(alpha);
	}

	@Override
	public boolean shouldDisableDepth() {
		return super.shouldDisableDepth();
	}

	@Override
	public float getRedColorF() {
		return super.getRedColorF();
	}

	@Override
	public float getGreenColorF() {
		return super.getGreenColorF();
	}

	@Override
	public float getBlueColorF() {
		return super.getBlueColorF();
	}

	@Override
	public void setMaxAge(int p_187114_1_) {
		super.setMaxAge(p_187114_1_);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks,
			float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ,
				rotationXY, rotationXZ);
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	@Override
	public void setParticleTexture(TextureAtlasSprite texture) {
		super.setParticleTexture(texture);
	}

	@Override
	public void setParticleTextureIndex(int particleTextureIndex) {
		super.setParticleTextureIndex(particleTextureIndex);
	}

	@Override
	public void nextTextureIndexX() {
		super.nextTextureIndexX();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public void setExpired() {
		super.setExpired();
	}

	@Override
	protected void setSize(float p_187115_1_, float p_187115_2_) {
		super.setSize(p_187115_1_, p_187115_2_);
	}

	@Override
	public void setPosition(double p_187109_1_, double p_187109_3_, double p_187109_5_) {
		super.setPosition(p_187109_1_, p_187109_3_, p_187109_5_);
	}

	@Override
	public void move(double x, double y, double z) {
		super.move(x, y, z);
	}

	@Override
	protected void resetPositionToBB() {
		super.resetPositionToBB();
	}

	@Override
	public int getBrightnessForRender(float p_189214_1_) {
		return super.getBrightnessForRender(p_189214_1_);
	}

	@Override
	public boolean isAlive() {
		return super.isAlive();
	}

	@Override
	public AxisAlignedBB getBoundingBox() {
		return super.getBoundingBox();
	}

	@Override
	public void setBoundingBox(AxisAlignedBB bb) {
		super.setBoundingBox(bb);
	}

}
