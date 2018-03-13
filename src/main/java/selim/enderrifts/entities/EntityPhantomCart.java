package selim.enderrifts.entities;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class EntityPhantomCart extends Entity {

	private final EntityMinecart mirrorCart;
	private float fade = 1.0f;

	public EntityPhantomCart(World world) {
		this(world, null);
	}

	public EntityPhantomCart(World world, EntityMinecart mirrorCart) {
		super(world);
		this.mirrorCart = mirrorCart;
		if (mirrorCart != null) {
			this.posX = mirrorCart.posX;
			this.posY = mirrorCart.posY;
			this.posZ = mirrorCart.posZ;
			this.rotationYaw = mirrorCart.rotationYaw;
			this.rotationPitch = mirrorCart.rotationPitch;
		}
	}

	@Override
	protected void entityInit() {}

	@Override
	public void onEntityUpdate() {
		if (this.fade <= 0.0f || this.mirrorCart == null) {
			System.out.println("killed, fade: " + this.fade + ", mirror: " + this.mirrorCart);
			this.setDead();
		}
		super.onEntityUpdate();
		this.fade -= 0.01f;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		if (!nbt.hasKey("mirrorDim") || nbt.hasKey("mirrorUuidLeast") || nbt.hasKey("mirrorUuidMost"))
			return;
		WorldServer world = DimensionManager.getWorld(nbt.getInteger("mirrorDim"));
		UUID mirrorUuid = new UUID(nbt.getLong("mirrorUuidMost"), nbt.getLong("mirrorUuidLeast"));
		world.getEntityFromUuid(mirrorUuid);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		if (this.mirrorCart == null)
			return;
		nbt.setInteger("mirrorDim", this.mirrorCart.world.provider.getDimension());
		UUID mirrorUuid = this.mirrorCart.getUniqueID();
		nbt.setLong("mirrorUuidLeast", mirrorUuid.getLeastSignificantBits());
		nbt.setLong("mirrorUuidMost", mirrorUuid.getMostSignificantBits());
	}

	public EntityMinecart getMirrorCart() {
		return this.mirrorCart;
	}

	public float getFade() {
		return this.fade;
	}

}
