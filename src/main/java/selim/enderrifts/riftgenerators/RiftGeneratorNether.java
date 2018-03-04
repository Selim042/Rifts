package selim.enderrifts.riftgenerators;

import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import selim.enderrifts.ModInfo;
import selim.enderrifts.RiftsRegistry;

public class RiftGeneratorNether extends DefaultRiftGenerator {

	public RiftGeneratorNether() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "nether"));
		this.setDimensionType(DimensionType.NETHER);
	}

	@Override
	public boolean setBlock(World oldWorld, World newWorld, BlockPos pos) {
		IBlockState baseState = oldWorld.getBlockState(pos.add(0, -1, 0));
		IBlockState state = oldWorld.getBlockState(pos);
		if (baseState.getBlock().canSustainPlant(baseState, newWorld, pos, EnumFacing.UP,
				RiftsRegistry.Blocks.RIFT_FLOWER)
				&& (state.getMaterial().equals(Material.PLANTS) || state.getBlock() instanceof BlockBush)
				&& newWorld.rand.nextInt(16) == 1) {
			newWorld.setBlockState(pos, RiftsRegistry.Blocks.RIFT_FLOWER.getDefaultState());
			return true;
		}
		return false;
	}

}
