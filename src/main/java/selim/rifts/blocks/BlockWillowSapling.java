package selim.rifts.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLog;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.RiftRegistry;

public class BlockWillowSapling extends BlockBush implements IGrowable {

	public static final PropertyEnum<EnumFacing> FACE = PropertyEnum.create("face", EnumFacing.class,
			EnumFacing.UP, EnumFacing.DOWN);
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 4);
	protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D,
			0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);
	protected static final AxisAlignedBB UPSIDEDOWN_SAPLING_AABB = new AxisAlignedBB(
			0.09999999403953552D, 0.800000011920929D, 0.09999999403953552D, 0.8999999761581421D, 0.0D,
			0.8999999761581421D);

	public BlockWillowSapling() {
		super(Material.PLANTS);
		this.setRegistryName(ModInfo.ID, "willow_sapling");
		this.setUnlocalizedName(ModInfo.ID + ":willow_sapling");
		this.setCreativeTab(EnderRifts.mainTab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACE, EnumFacing.UP)
				.withProperty(STAGE, Integer.valueOf(0)));
		this.setTickRandomly(true);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		// TODO Auto-generated method stub
		return super.canPlaceBlockAt(worldIn, pos);
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		// TODO Auto-generated method stub
		return super.canBlockStay(worldIn, pos, state);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX,
			float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		switch (facing) {
		case UP:
			return this.getDefaultState().withProperty(FACE, EnumFacing.UP);
		default:
			return this.getDefaultState().withProperty(FACE, EnumFacing.DOWN);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(FACE)) {
		case UP:
			return SAPLING_AABB;
		case DOWN:
			return UPSIDEDOWN_SAPLING_AABB;
		default:
			return Block.FULL_BLOCK_AABB;
		}
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return true;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
		return rand.nextBoolean();
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		int stage = state.getValue(STAGE);
		if (stage < 3)
			world.setBlockState(pos, state.withProperty(STAGE, stage + 1));
		else
			spawnTree(world, rand, pos, state.getValue(FACE));
	}

	private void spawnTree(World world, Random rand, BlockPos pos, EnumFacing facing) {
		IBlockState leaves = RiftRegistry.Blocks.WILLOW_LEAVES.getDefaultState();
		int height = rand.nextInt(2) + 3;
		BlockPos midLeaf = null;
		for (int y = 0; y < height + 2; y++) {
			BlockPos p = pos.add(0, y * (facing == EnumFacing.UP ? 1 : -1), 0);
			if (!p.equals(pos) && !world.isAirBlock(p))
				return;
		}
		for (int x = -1; x < 2; x++) {
			for (int z = -1; z < 2; z++) {
				for (int y = 0; y < height + 2; y++) {
					BlockPos p = pos.add(x, y * (facing == EnumFacing.UP ? 1 : -1), z);
					if (!p.equals(pos) && !world.getBlockState(p).getBlock().isReplaceable(world, p))
						return;
				}
			}
		}
		for (int i = 0; i < height; i++) {
			midLeaf = pos.add(0, i * (facing == EnumFacing.UP ? 1 : -1), 0);
			world.setBlockState(midLeaf, RiftRegistry.Blocks.WILLOW_LOG.getDefaultState()
					.withProperty(BlockWillowLog.LOG_AXIS, BlockLog.EnumAxis.Y));
		}
		midLeaf = midLeaf.offset(facing);
		IBlockState curState = world.getBlockState(midLeaf);
		if (curState.getBlock().canBeReplacedByLeaves(curState, world, midLeaf))
			world.setBlockState(midLeaf, leaves);
		for (int x = -2; x < 3; x++) {
			for (int y = -2; y < 3; y++) {
				if ((x == -2 && y == -2) || (x == -2 && y == 2) || (x == 2 && y == -2)
						|| (x == 2 && y == 2))
					continue;
				BlockPos p = midLeaf.add(x, 0, y);
				curState = world.getBlockState(p);
				if (curState.getBlock().canBeReplacedByLeaves(curState, world, p))
					world.setBlockState(p, leaves);
			}
		}
		BlockPos nextLayer = midLeaf.offset(facing);
		curState = world.getBlockState(nextLayer);
		if (curState.getBlock().canBeReplacedByLeaves(curState, world, nextLayer))
			world.setBlockState(nextLayer, leaves);
		for (EnumFacing f : EnumFacing.HORIZONTALS) {
			BlockPos p = nextLayer.offset(f);
			curState = world.getBlockState(p);
			if (curState.getBlock().canBeReplacedByLeaves(curState, world, p))
				world.setBlockState(p, leaves);
		}
		BlockPos topHang = midLeaf.offset(facing.getOpposite());
		for (EnumFacing s : EnumFacing.HORIZONTALS) {
			int hangHeight = 2 + rand.nextInt(2);
			BlockPos p = topHang.offset(s, 3).offset(s.rotateY());
			for (int i = 0; i < hangHeight; i++) {
				curState = world.getBlockState(p);
				if (curState.getBlock().canBeReplacedByLeaves(curState, world, p))
					world.setBlockState(p.offset(facing.getOpposite(), i), leaves);
			}
			hangHeight = 2 + rand.nextInt(2);
			p = topHang.offset(s, 3).offset(s.rotateYCCW());
			for (int i = 0; i < hangHeight; i++) {
				curState = world.getBlockState(p);
				if (curState.getBlock().canBeReplacedByLeaves(curState, world, p))
					world.setBlockState(p.offset(facing.getOpposite(), i), leaves);
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState().withProperty(FACE,
				meta >= 4 ? EnumFacing.DOWN : EnumFacing.UP);
		return state.withProperty(STAGE, meta >= 4 ? meta - 4 : meta);
		// return this.getDefaultState().withProperty(FACE,
		// EnumFacing.byMetadata(meta & 7))
		// .withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = state.getValue(STAGE);
		if (state.getValue(FACE) == EnumFacing.DOWN)
			i *= 2;
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACE, STAGE });
	}

}
