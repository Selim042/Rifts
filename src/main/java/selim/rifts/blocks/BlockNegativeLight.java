package selim.rifts.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import selim.rifts.RiftRegistry;

public class BlockNegativeLight extends Block {

	private int opacity;
	private int range;

	public BlockNegativeLight(Material materialIn, int opacity, int range) {
		super(materialIn);
		this.opacity = opacity;
		this.range = range;
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		if (!worldIn.isRemote)
			placeOpaqueAir(worldIn, pos, range);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		placeOpaqueAir(worldIn, pos, range);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		removeOpaqueAir(worldIn, pos, range);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
			EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY,
			float hitZ) {
		placeOpaqueAir(worldIn, pos, range);
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn,
			BlockPos fromPos) {
		placeOpaqueAir(worldIn, pos, range);
	}

	private void placeOpaqueAir(World world, BlockPos pos, int range, EnumFacing... exclude) {
		for (EnumFacing dir : EnumFacing.values()) {
			if (arrayContains(exclude, dir))
				continue;
			BlockPos innerPos = pos.offset(dir);
			IBlockState state = world.getBlockState(innerPos);
			Block block = state.getBlock();
			if (block instanceof BlockAir || (block instanceof BlockOpaqueAir
					&& state.getValue(BlockOpaqueAir.OPACITY) <= opacity)) {
				world.setBlockState(innerPos, RiftRegistry.Blocks.OPAQUE_AIR.getDefaultState()
						.withProperty(BlockOpaqueAir.OPACITY, opacity));
				if (range > 0)
					placeOpaqueAir(world, innerPos, range - 1, dir.getOpposite());
			}
		}
	}

	private void removeOpaqueAir(World world, BlockPos pos, int range, EnumFacing... exclude) {
		// System.out.println(range);
		for (EnumFacing dir : EnumFacing.values()) {
			// if (arrayContains(exclude, dir))
			// continue;
			BlockPos innerPos = pos.offset(dir);
			Block block = world.getBlockState(innerPos).getBlock();
			if (block instanceof BlockOpaqueAir) {
				world.setBlockToAir(innerPos);
				if (range > 0)
					removeOpaqueAir(world, innerPos, range - 1, dir.getOpposite());
			}
		}
	}

	private <T> boolean arrayContains(T[] arr, T val) {
		for (T v : arr)
			if (v != null && v.equals(val))
				return true;
		return false;
	}

}
