package selim.rifts.world;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.RiftRegistry;
import selim.rifts.api.RiftGenerator;

public class WorldProviderRift extends WorldProvider {

	public WorldProviderRift() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	protected void init() {
		super.init();
		this.biomeProvider = new BiomeProviderSingle(RiftRegistry.Biomes.THE_RIFT);
	}

	@Override
	public DimensionType getDimensionType() {
		return DimensionRift.DIMENSION_TYPE;
	}

	@Override
	public String getSaveFolder() {
		return "world_rift";
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorRift(world);
	}

	@Override
	public boolean isSurfaceWorld() {
		return false;
	}

	@Override
	public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
		return ModInfo.PURPLE_COLOR;
	}

	@Override
	public boolean isSkyColored() {
		return true;
	}

	@Override
	public float[] getLightBrightnessTable() {
		return super.getLightBrightnessTable();
	}

	@Override
	public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight,
			float blockLight, float[] colors) {
		super.getLightmapColors(partialTicks, sunBrightness, skyLight, blockLight, colors);
	}

	@Override
	public boolean shouldMapSpin(String entity, double x, double z, double rotation) {
		return true;
	}

	@Override
	public int getRespawnDimension(EntityPlayerMP player) {
		// return super.getRespawnDimension(player);
		WorldProvider prov = DimensionManager.getProvider((WorldProviderRift.getEntryDim(player)));
		if (prov == this)
			return 0;
		return prov.getRespawnDimension(player);
	}

	@Override
	public boolean isDaytime() {
		return false;
	}

	@Override
	public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
		return ModInfo.PURPLE_COLOR;
	}

	@Override
	public float getSunBrightness(float par1) {
		return 1.0f;
	}

	@Override
	public float getStarBrightness(float par1) {
		return 0.0f;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public double getHorizon() {
		return 0.0;
	}

	@Override
	public boolean canDoLightning(Chunk chunk) {
		return false;
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return false;
	}

	@Override
	public boolean doesXZShowFog(int x, int z) {
		return true;
	}

	@Override
	protected void generateLightBrightnessTable() {
		// float f = 0.1F;
		for (int i = 0; i <= 15; ++i) {
			float f1 = 1.0F - (float) i / 15.0F;
			this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 0.9F + 0.1F;
		}
	}

	private static final HashMap<UUID, Integer> ENTRY_DIM = new HashMap<UUID, Integer>();
	private static final HashMap<UUID, List<BlockPos>> DECAY = new HashMap<UUID, List<BlockPos>>();
	private static final int RADIUS = 16;
	private static final Random rand = new Random();

	@Override
	public void onPlayerAdded(EntityPlayerMP player) {
		// ENTRY_DIM.put(player.getUniqueID(),
		// player.world.provider.getDimension());
		// BlockPos pos = player.getPosition();
		// World oldWorld = player.world;
		// World newWorld = this.world;
		// EnderRifts.proxy.addVisitedToPersistance(oldWorld.provider.getDimensionType());
		// List<BlockPos> decay = new LinkedList<BlockPos>();
		// RiftGenerator[] generators = RiftGenerator.getGenerators(oldWorld);
		// System.out.println("s");
		// for (int y = -RADIUS; y < RADIUS; y++) {
		// for (int r = 0; r < MathHelper.sqrt(Math.pow(RADIUS, 2) - Math.pow(y,
		// 2)); r++) {
		// for (int theta = 0; theta < 360; theta++) {
		// if (rand.nextInt(MathHelper.abs((RADIUS / 2) - RADIUS) + 2) == 1) {
		// float x = r * MathHelper.sin(theta);
		// float z = r * MathHelper.cos(theta);
		// BlockPos newPos = pos.add(x, y, z);
		// IBlockState state = oldWorld.getBlockState(newPos);
		// if (oldWorld.getTileEntity(newPos) != null
		// || state.getBlock() instanceof BlockAir
		// || state.getBlock().isAir(state, oldWorld, newPos)) {
		// if (rand.nextInt(32) == 0
		// && newWorld.getBlockState(pos.add(0, -1, 0)).isTopSolid()) {
		// newWorld.setBlockState(pos,
		// RiftRegistry.Blocks.RIFT_TEST.getDefaultState());
		// decay.add(pos);
		// }
		// continue;
		// }
		// if (state.getBlock().canPlaceBlockAt(newWorld, newPos))
		// setBlock(generators, oldWorld, newWorld, state, newPos);
		// decay.add(newPos);
		// }
		// }
		// }
		// }
		// newWorld.setBlockState(pos,
		// RiftRegistry.Blocks.RIFT_TEST.getDefaultState());
		// decay.add(pos);
		// DECAY.put(player.getUniqueID(), decay);
	}

	@SubscribeEvent
	public void onWorldChange(PlayerChangedDimensionEvent event) {
		EntityPlayer player = event.player;
		if (event.fromDim == DimensionRift.DIMENSION_ID) {
			ENTRY_DIM.remove(player.getUniqueID());
			List<BlockPos> decay = DECAY.get(player.getUniqueID());
			if (decay != null) {
				for (BlockPos pos : decay)
					this.world.setBlockToAir(pos);
				DECAY.remove(player.getUniqueID());
			}
			return;
		}
		if (event.toDim != DimensionRift.DIMENSION_ID)
			return;
		ENTRY_DIM.put(player.getUniqueID(), event.fromDim);
		BlockPos pos = player.getPosition();
		World oldWorld = DimensionManager.getWorld(event.fromDim);
		World newWorld = this.world;
		EnderRifts.proxy.addVisitedToPersistance(oldWorld.provider.getDimensionType());
		List<BlockPos> decay = new LinkedList<BlockPos>();
		RiftGenerator[] generators = RiftGenerator.getGenerators(oldWorld);
//		System.out.println("s");
		for (int y = -RADIUS; y < RADIUS; y++) {
			for (int r = 0; r < MathHelper.sqrt(Math.pow(RADIUS, 2) - Math.pow(y, 2)); r++) {
				for (int theta = 0; theta < 360; theta++) {
					if (rand.nextInt(MathHelper.abs((RADIUS / 2) - RADIUS) + 2) == 1) {
						float x = r * MathHelper.sin(theta);
						float z = r * MathHelper.cos(theta);
						BlockPos newPos = pos.add(x, y, z);
						IBlockState state = oldWorld.getBlockState(newPos);
						if (oldWorld.getTileEntity(newPos) != null
								|| state.getBlock() instanceof BlockAir
								|| state.getBlock().isAir(state, oldWorld, newPos)) {
//							if (rand.nextInt(32) == 0
//									&& newWorld.getBlockState(newPos.add(0, -1, 0)).isTopSolid()) {
//								newWorld.setBlockState(newPos,
//										RiftRegistry.Blocks.RIFT_TEST.getDefaultState());
//								decay.add(newPos);
//							}
							continue;
						}
						if (state.getBlock().canPlaceBlockAt(newWorld, newPos))
							setBlock(generators, oldWorld, newWorld, state, newPos);
						decay.add(newPos);
					}
				}
			}
		}
		newWorld.setBlockState(pos, RiftRegistry.Blocks.RIFT_TEST.getDefaultState());
		decay.add(pos);
		DECAY.put(player.getUniqueID(), decay);
	}

	private void setBlock(RiftGenerator[] gens, World oldWorld, World newWorld, IBlockState state,
			BlockPos pos) {
		boolean set = false;
		for (RiftGenerator gen : gens) {
			// System.out.println(gen.getClass().getName());
			if (gen.setBlock(oldWorld, newWorld, pos))
				set = true;
		}
		if (!set) {
			// System.out.println("def");
			newWorld.setBlockState(pos, state, 18);
		}
	}

	@Override
	public void onPlayerRemoved(EntityPlayerMP player) {
		ENTRY_DIM.remove(player.getUniqueID());
		List<BlockPos> decay = DECAY.get(player.getUniqueID());
		if (decay != null) {
			for (BlockPos pos : decay)
				this.world.setBlockToAir(pos);
			DECAY.remove(player.getUniqueID());
		}
	}

	public static int getEntryDim(EntityPlayer player) {
		return ENTRY_DIM.containsKey(player.getUniqueID())
				? ENTRY_DIM.get(player.getUniqueID()).intValue() : DimensionType.OVERWORLD.getId();
	}

	public void addToDecay(EntityPlayer player, BlockPos pos) {
		if (player == null || pos == null)
			return;
		List<BlockPos> decay = DECAY.get(player.getUniqueID());
		if (decay == null)
			return;
		decay.add(pos);
		DECAY.put(player.getUniqueID(), decay);
	}

	@SubscribeEvent
	public void placeEvent(PlaceEvent event) {
		if (DimensionRift.isTestDimension(event.getWorld()))
			addToDecay(event.getPlayer(), event.getPos());
	}

}
