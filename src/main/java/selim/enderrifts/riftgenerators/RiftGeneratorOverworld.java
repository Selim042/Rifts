package selim.enderrifts.riftgenerators;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import selim.enderrifts.ModInfo;
import selim.enderrifts.RiftsRegistry;

public class RiftGeneratorOverworld extends DefaultRiftGenerator {

	public RiftGeneratorOverworld() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "overworld"));
		this.setDimensionType(DimensionType.OVERWORLD);
	}

	@Override
	public boolean setBlock(World oldWorld, World newWorld, BlockPos pos) {
		IBlockState baseState = oldWorld.getBlockState(pos.add(0, -1, 0));
		IBlockState state = oldWorld.getBlockState(pos);
		Block block = state.getBlock();
		if (baseState.getBlock().canSustainPlant(baseState, newWorld, pos, EnumFacing.UP,
				RiftsRegistry.Blocks.RIFT_FLOWER)
				&& (state.getMaterial().equals(Material.PLANTS) || state.getBlock() instanceof BlockBush)
				&& newWorld.rand.nextInt(16) == 1) {
			newWorld.setBlockState(pos, RiftsRegistry.Blocks.RIFT_FLOWER.getDefaultState());
			return true;
		}
		if (newWorld.rand.nextInt(8) == 1 && (block instanceof BlockOre
				|| block.getRegistryName().getResourcePath().contains("ore"))) {
			newWorld.setBlockState(pos,
					newWorld.rand.nextBoolean() ? RiftsRegistry.Blocks.AMETHYST_ORE.getDefaultState()
							: RiftsRegistry.Blocks.OPAL_ORE.getDefaultState());
			return true;
		}
		if (block.equals(Blocks.STONE) && !state.getValue(BlockStone.VARIANT).equals(BlockStone.EnumType.STONE)) {
			newWorld.setBlockState(pos, RiftsRegistry.Blocks.BARITE.getDefaultState());
			return true;
		}
		return false;
	}

}
