package selim.rifts.entities;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.rifts.events.EnhancedEnderTeleportEvent;

// Copied from EntityEnderPearl
public class EntityPhantomPearl extends EntityThrowable {

	private EntityLivingBase perlThrower;
	private boolean phased = false;

	public EntityPhantomPearl(World worldIn) {
		super(worldIn);
	}

	public EntityPhantomPearl(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
		this.perlThrower = throwerIn;
	}

	@SideOnly(Side.CLIENT)
	public EntityPhantomPearl(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		// This if statement is not copied
		if (!phased) {
			this.noClip = true;
			this.phased = true;
			System.out.println("impact: " + this.noClip);
			return;
		}
		EntityLivingBase entitylivingbase = this.getThrower();

		if (result.entityHit != null) {
			if (result.entityHit == this.perlThrower) {
				return;
			}

			result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, entitylivingbase),
					0.0F);
		}

		if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
			BlockPos blockpos = result.getBlockPos();
			TileEntity tileentity = this.world.getTileEntity(blockpos);

			if (tileentity instanceof TileEntityEndGateway) {
				TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway) tileentity;

				if (entitylivingbase != null) {
					if (entitylivingbase instanceof EntityPlayerMP) {
						CriteriaTriggers.ENTER_BLOCK.trigger((EntityPlayerMP) entitylivingbase,
								this.world.getBlockState(blockpos));
					}

					tileentityendgateway.teleportEntity(entitylivingbase);
					this.setDead();
					return;
				}

				tileentityendgateway.teleportEntity(this);
				return;
			}
		}

		for (int i = 0; i < 32; ++i) {
			this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX,
					this.posY + this.rand.nextDouble() * 2.0D, this.posZ, this.rand.nextGaussian(), 0.0D,
					this.rand.nextGaussian());
		}

		if (!this.world.isRemote) {
			if (entitylivingbase instanceof EntityPlayerMP) {
				EntityPlayerMP entityplayermp = (EntityPlayerMP) entitylivingbase;

				if (entityplayermp.connection.getNetworkManager().isChannelOpen()
						&& entityplayermp.world == this.world && !entityplayermp.isPlayerSleeping()) {
					EnderTeleportEvent event = new EnhancedEnderTeleportEvent(entityplayermp, this,
							this.posX, this.posY, this.posZ, 5.0F);
					if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) { // Don't
																							// indent
																							// to
																							// lower
																							// patch
																							// size

						// Removed the possible endermite spawn

						if (entitylivingbase.isRiding()) {
							entitylivingbase.dismountRidingEntity();
						}

						entitylivingbase.setPositionAndUpdate(event.getTargetX(), event.getTargetY(),
								event.getTargetZ());
						entitylivingbase.fallDistance = 0.0F;
						entitylivingbase.attackEntityFrom(DamageSource.FALL, event.getAttackDamage());
					}
				}
			} else if (entitylivingbase != null) {
				entitylivingbase.setPositionAndUpdate(this.posX, this.posY, this.posZ);
				entitylivingbase.fallDistance = 0.0F;
			}

			this.setDead();
		}
	}

	@Override
	public void onUpdate() {
		// This if statement is not copied
		if (phased) {
			IBlockState state = this.world.getBlockState(this.getPosition());
			if (state.getBlock().isAir(state, this.world, this.getPosition())
					|| state.getBlock() instanceof BlockAir) {
				this.noClip = false;
				System.out.println("update: " + this.noClip + ", pos: " + this.getPosition());
				return;
			}
		}
		EntityLivingBase entitylivingbase = this.getThrower();

		if (entitylivingbase != null && entitylivingbase instanceof EntityPlayer
				&& !entitylivingbase.isEntityAlive()) {
			this.setDead();
		} else {
			super.onUpdate();
		}
	}

	@Nullable
	@Override
	public Entity changeDimension(int dimensionIn) {
		if (this.thrower.dimension != dimensionIn) {
			this.thrower = null;
		}

		return super.changeDimension(dimensionIn);
	}
	// End copy

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("phased", this.phased);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.phased = compound.getBoolean("phased");
	}
}