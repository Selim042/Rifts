package selim.enderrifts.world;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.Level;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import selim.enderrifts.EnderRifts;

public class CustomTeleporter extends Teleporter {

	@Override
	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
		double spawnX = (double) this.x + 0.5D;
		double spawnZ = (double) this.y + 0.5D;
		double spawnY = (double) this.z;

		if (entityIn instanceof EntityPlayerMP) {
			((EntityPlayerMP) entityIn).connection.setPlayerLocation(spawnX, spawnY, spawnZ,
					entityIn.rotationYaw, entityIn.rotationPitch);
		} else {
			entityIn.setLocationAndAngles(spawnX, spawnY, spawnZ, entityIn.rotationYaw,
					entityIn.rotationPitch);
		}
		((EntityPlayerMP) entityIn).connection
				.sendPacket(new SPacketEffect(1032, BlockPos.ORIGIN, 0, false));

		return true;
	}

	public CustomTeleporter(WorldServer world, BlockPos pos) {
		super(world);
		this.worldServer = world;
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}

	public CustomTeleporter(WorldServer world, double x, double y, double z) {
		super(world);
		this.worldServer = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	private final WorldServer worldServer;
	private double x;
	private double y;
	private double z;

	@Override
	public void placeInPortal(@Nonnull Entity entity, float rotationYaw) {
		// The main purpose of this function is to *not* create a nether portal
		this.worldServer.getBlockState(new BlockPos((int) this.x, (int) this.y, (int) this.z));

		entity.setPosition(this.x, this.y, this.z);
		entity.motionX = 0.0f;
		entity.motionY = 0.0f;
		entity.motionZ = 0.0f;
	}

	public static void teleportToDimension(EntityPlayer player, int dimension, BlockPos pos) {
		teleportToDimension(player, dimension, pos.getX(), pos.getY(), pos.getZ());
	}

	public static void teleportToDimension(EntityPlayer player, int dimension, double x, double y,
			double z) {
		EnderRifts.LOGGER.log(Level.INFO, "a");
		int oldDimension = player.getEntityWorld().provider.getDimension();
		EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
		MinecraftServer server = player.getEntityWorld().getMinecraftServer();
		WorldServer worldServer = server.getWorld(dimension);
		player.addExperienceLevel(0);

		if (worldServer == null || worldServer.getMinecraftServer() == null) { // Dimension
																				// doesn't
																				// exist
			throw new IllegalArgumentException("Dimension: " + dimension + " doesn't exist!");
		}

		World oldWorld = player.getEntityWorld();
		oldWorld.removeEntity(entityPlayerMP);
		entityPlayerMP.isDead = false;
		CustomTeleporter teleporter = new CustomTeleporter(worldServer, x, y, z);
		teleporter.placeInExistingPortal(entityPlayerMP, 0);
		worldServer.updateEntityWithOptionalForce(entityPlayerMP, false);

		if (oldDimension == 1) {
			// For some reason teleporting out of the end does weird things.
			// Compensate for that
			player.setPositionAndUpdate(x, y, z);
			worldServer.spawnEntity(player);
			worldServer.updateEntityWithOptionalForce(player, false);
		}
	}

}
