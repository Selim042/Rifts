package selim.enderrifts.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import selim.enderrifts.RiftsRegistry;

public abstract class RiftGenerator extends IForgeRegistryEntry.Impl<RiftGenerator> {

	private DimensionType type;

	public abstract boolean setBlock(World oldWorld, World newWorld, BlockPos pos);

	public RiftGenerator setDimensionType(DimensionType type) {
		this.type = type;
		return this;
	}

	public DimensionType getDimensionType() {
		return this.type;
	}

	private static final ConcurrentHashMap<DimensionType, RiftGenerator[]> CACHE = new ConcurrentHashMap<DimensionType, RiftGenerator[]>();

	public static RiftGenerator[] getGenerators(World world) {
		return getGenerators(world.provider);
	}

	public static RiftGenerator[] getGenerators(int id) {
		return getGenerators(DimensionManager.getProvider(id));
	}

	public static RiftGenerator[] getGenerators(WorldProvider provider) {
		return getGenerators(provider.getDimensionType());
	}

	public static RiftGenerator[] getGenerators(DimensionType type) {
		if (CACHE.containsKey(type))
			return CACHE.get(type);
		IForgeRegistry<RiftGenerator> registry = GameRegistry.findRegistry(RiftGenerator.class);
		List<RiftGenerator> gens = new ArrayList<RiftGenerator>();
		for (Entry<ResourceLocation, RiftGenerator> e : registry.getEntries()) {
			RiftGenerator gen = e.getValue();
			if (gen != null && gen.type != null && gen.type.equals(type))
				gens.add(gen);
		}
		RiftGenerator[] gensArr;
		if (!gens.isEmpty())
			gensArr = gens.toArray(new RiftGenerator[gens.size() - 1]);
		gensArr = new RiftGenerator[] { RiftsRegistry.RiftProviders.OVERWORLD };
		CACHE.put(type, gensArr);
		return gensArr;
	}

}
