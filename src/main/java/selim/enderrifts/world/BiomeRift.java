package selim.enderrifts.world;

import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import selim.enderrifts.ModInfo;

public class BiomeRift extends Biome {

	private static BiomeProperties properties;

	public BiomeRift() {
		super(getProperties());
		this.spawnableCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		this.modSpawnableLists.clear();
		this.topBlock = Blocks.AIR.getDefaultState();
		this.fillerBlock = Blocks.AIR.getDefaultState();
		this.decorator.treesPerChunk = -999;
		this.decorator.deadBushPerChunk = -999;
		this.decorator.reedsPerChunk = -999;
		this.decorator.cactiPerChunk = -999;
		this.flowers.clear();

		this.spawnableMonsterList.add(new SpawnListEntry(EntityEndermite.class, 1, 1, 4));
		this.setRegistryName(ModInfo.ID, "the_rift");
	}

	private static BiomeProperties getProperties() {
		if (properties != null)
			return properties;
		properties = new BiomeProperties("enderrifts:rift");
		properties.setTemperature(0.5f);
		properties.setBaseHeight(0.0f);
		properties.setHeightVariation(0.0f);
		properties.setRainDisabled();
		// properties.setWaterColor(0);
		return properties;
	}

	@Override
	public boolean canRain() {
		return false;
	}

}
