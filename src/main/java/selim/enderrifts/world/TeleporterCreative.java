package selim.enderrifts.world;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * Created by drazisil on 2/11/2017.
 * 
 * Distributed under the Apache license
 * TODO: TAKE CARE OF THIS ^
 */
public class TeleporterCreative extends Teleporter {

	private final WorldServer worldServerInstance;
	/** A private Random() function in Teleporter */
	private final Random random;
	// private final Long2ObjectMap<Teleporter.PortalPosition>
	// destinationCoordinateCache = new Long2ObjectOpenHashMap(4096);

	public TeleporterCreative(WorldServer worldIn) {
		super(worldIn);
		this.worldServerInstance = worldIn;
		this.random = new Random(worldIn.getSeed());
	}

	@Nullable
	public static Entity changeDimension(Entity entityIn, int dimensionIn) {
		if (!entityIn.world.isRemote && !entityIn.isDead && entityIn instanceof EntityPlayerMP) {
			if (!net.minecraftforge.common.ForgeHooks.onTravelToDimension(entityIn, dimensionIn))
				return entityIn;

			if (entityIn.dimension != dimensionIn) {
				entityIn.dimension = dimensionIn;
			}

			TeleporterCreative.transferPlayerToDimension((EntityPlayerMP) entityIn, dimensionIn,
					new TeleporterCreative(((EntityPlayerMP) entityIn).getServerWorld()));
			((EntityPlayerMP) entityIn).connection
					.sendPacket(new SPacketEffect(1032, BlockPos.ORIGIN, 0, false));
			return entityIn;
		} else {
			return null;
		}
	}

	public static void transferPlayerToDimension(EntityPlayerMP player, int dimensionIn,
			TeleporterCreative teleporter) {
		int i = player.dimension;
		WorldServer worldserver = player.getServer().getPlayerList().getServerInstance()
				.getWorld(player.dimension);
		player.dimension = dimensionIn;
		WorldServer worldserver1 = player.getServer().getPlayerList().getServerInstance()
				.getWorld(player.dimension);
		player.connection.sendPacket(new SPacketRespawn(player.dimension, worldserver1.getDifficulty(),
				worldserver1.getWorldInfo().getTerrainType(), player.interactionManager.getGameType()));
		player.getServer().getPlayerList().updatePermissionLevel(player);
		worldserver.removeEntityDangerously(player);
		player.isDead = false;
		TeleporterCreative.transferEntityToWorld(player, i, worldserver, worldserver1, teleporter);
		player.getServer().getPlayerList().preparePlayer(player, worldserver);

		BlockPos safePos = player.getServer().getPlayerList().getServerInstance().getWorld(dimensionIn)
				.getTopSolidOrLiquidBlock(new BlockPos(player.posX, player.posY, player.posZ));

		player.connection.setPlayerLocation(player.posX, safePos.getY(), player.posZ, player.rotationYaw,
				player.rotationPitch);
		player.interactionManager.setWorld(worldserver1);
		player.connection.sendPacket(new SPacketPlayerAbilities(player.capabilities));
		player.getServer().getPlayerList().updateTimeAndWeatherForPlayer(player, worldserver1);
		player.getServer().getPlayerList().syncPlayerInventory(player);

		for (PotionEffect potioneffect : player.getActivePotionEffects()) {
			player.connection.sendPacket(new SPacketEntityEffect(player.getEntityId(), potioneffect));
		}
		net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player,
				i, dimensionIn);
	}

	public static void transferEntityToWorld(Entity entityIn, int lastDimension, WorldServer oldWorldIn,
			WorldServer toWorldIn, TeleporterCreative teleporter) {
		net.minecraft.world.WorldProvider pOld = oldWorldIn.provider;
		net.minecraft.world.WorldProvider pNew = toWorldIn.provider;
		double moveFactor = pOld.getMovementFactor() / pNew.getMovementFactor();
		double d0 = entityIn.posX * moveFactor;
		double d1 = entityIn.posZ * moveFactor;
		double d2 = 8.0D;
		float f = entityIn.rotationYaw;
		oldWorldIn.profiler.startSection("moving");

		if (entityIn.dimension == 1) {
			BlockPos blockpos;

			if (lastDimension == 1) {
				blockpos = toWorldIn.getSpawnPoint();
			} else {
				blockpos = toWorldIn.getSpawnCoordinate();
			}

			d0 = (double) blockpos.getX();
			entityIn.posY = (double) blockpos.getY();
			d1 = (double) blockpos.getZ();
			entityIn.setLocationAndAngles(d0, entityIn.posY, d1, 90.0F, 0.0F);

			if (entityIn.isEntityAlive()) {
				oldWorldIn.updateEntityWithOptionalForce(entityIn, false);
			}
		}

		oldWorldIn.profiler.endSection();

		if (lastDimension != 1) {
			oldWorldIn.profiler.startSection("placing");
			d0 = (double) MathHelper.clamp((int) d0, -29999872, 29999872);
			d1 = (double) MathHelper.clamp((int) d1, -29999872, 29999872);

			if (entityIn.isEntityAlive()) {
				entityIn.setLocationAndAngles(d0, entityIn.posY, d1, entityIn.rotationYaw,
						entityIn.rotationPitch);

				toWorldIn.spawnEntity(entityIn);
				toWorldIn.updateEntityWithOptionalForce(entityIn, false);
			}

			oldWorldIn.profiler.endSection();
		}

		entityIn.setWorld(toWorldIn);
	}

}
