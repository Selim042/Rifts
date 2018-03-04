package selim.enderrifts.world;

import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

public class ChunkGeneratorRift implements IChunkGenerator {

	private final World world;
	private Random random;
	private Biome[] biomesForGeneration;

	public ChunkGeneratorRift(World world) {
		this.world = world;
		long seed = world.getSeed();
		this.random = new Random((seed + 516) * 314);
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		ChunkPrimer chunkprimer = new ChunkPrimer();

		// Setup biomes again for actual biome decoration
		this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration,
				x * 16, z * 16, 16, 16);

		Chunk chunk = new Chunk(this.world, chunkprimer, x, z);

		byte[] biomeArray = chunk.getBiomeArray();
		for (int i = 0; i < biomeArray.length; ++i) {
			biomeArray[i] = (byte) Biome.getIdForBiome(this.biomesForGeneration[i]);
		}

		chunk.generateSkylightMap();
		return chunk;
	}

	@Override
	public void populate(int x, int z) {}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return ImmutableList.of();
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position,
			boolean findUnexplored) {
		return null;
	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		return false;
	}

}
