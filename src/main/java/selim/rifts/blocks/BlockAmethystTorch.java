package selim.rifts.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.api.docs.IDocEntryLink;

public class BlockAmethystTorch extends BlockNegativeLight implements IDocEntryLink {

	private static final int RANGE = 2;
	private static final int OPACITY = 4;

	public BlockAmethystTorch() {
		super(Material.CIRCUITS, OPACITY, RANGE);
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "amethyst_torch"));
		this.setUnlocalizedName(ModInfo.ID + ":amethyst_torch");
		this.setCreativeTab(EnderRifts.mainTab);
		this.setTickRandomly(true);
		// this.lightValue = 1;
		// Coppied from BlockTorch
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
		this.setTickRandomly(true);
	}

	@Override
	public ResourceLocation getLinkedEntry() {
		return new ResourceLocation(ModInfo.ID, "amethyst");
	}

	// Coppied from BlockTorch
	public static final PropertyDirection FACING = PropertyDirection.create("facing",
			new Predicate<EnumFacing>() {

				public boolean apply(@Nullable EnumFacing p_apply_1_) {
					return p_apply_1_ != EnumFacing.DOWN;
				}
			});
	protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.4000000059604645D, 0.0D,
			0.4000000059604645D, 0.6000000238418579D, 0.6000000238418579D, 0.6000000238418579D);
	protected static final AxisAlignedBB TORCH_NORTH_AABB = new AxisAlignedBB(0.3499999940395355D,
			0.20000000298023224D, 0.699999988079071D, 0.6499999761581421D, 0.800000011920929D, 1.0D);
	protected static final AxisAlignedBB TORCH_SOUTH_AABB = new AxisAlignedBB(0.3499999940395355D,
			0.20000000298023224D, 0.0D, 0.6499999761581421D, 0.800000011920929D, 0.30000001192092896D);
	protected static final AxisAlignedBB TORCH_WEST_AABB = new AxisAlignedBB(0.699999988079071D,
			0.20000000298023224D, 0.3499999940395355D, 1.0D, 0.800000011920929D, 0.6499999761581421D);
	protected static final AxisAlignedBB TORCH_EAST_AABB = new AxisAlignedBB(0.0D, 0.20000000298023224D,
			0.3499999940395355D, 0.30000001192092896D, 0.800000011920929D, 0.6499999761581421D);

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch ((EnumFacing) state.getValue(FACING)) {
		case EAST:
			return TORCH_EAST_AABB;
		case WEST:
			return TORCH_WEST_AABB;
		case SOUTH:
			return TORCH_SOUTH_AABB;
		case NORTH:
			return TORCH_NORTH_AABB;
		default:
			return STANDING_AABB;
		}
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn,
			BlockPos pos) {
		return NULL_AABB;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks
	 * for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	private boolean canPlaceOn(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		return state.getBlock().canPlaceTorchOnTop(state, worldIn, pos);
	}

	/**
	 * Checks if this block can be placed exactly at the given position.
	 */
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		for (EnumFacing enumfacing : FACING.getAllowedValues()) {
			if (this.canPlaceAt(worldIn, pos, enumfacing)) {
				return true;
			}
		}

		return false;
	}

	private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
		BlockPos blockpos = pos.offset(facing.getOpposite());
		IBlockState iblockstate = worldIn.getBlockState(blockpos);
		Block block = iblockstate.getBlock();
		BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

		if (facing.equals(EnumFacing.UP) && this.canPlaceOn(worldIn, blockpos)) {
			return true;
		} else if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
			return !isExceptBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID;
		} else {
			return false;
		}
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to
	 * allow for adjustments to the IBlockstate
	 */
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX,
			float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if (this.canPlaceAt(worldIn, pos, facing)) {
			return this.getDefaultState().withProperty(FACING, facing);
		} else {
			for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
				if (this.canPlaceAt(worldIn, pos, enumfacing)) {
					return this.getDefaultState().withProperty(FACING, enumfacing);
				}
			}

			return this.getDefaultState();
		}
	}

	/**
	 * Called after the block is set in the Chunk data, but before the Tile
	 * Entity is set
	 */
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.checkForDrop(worldIn, pos, state);
	}

	/**
	 * Called when a neighboring block was changed and marks that this state
	 * should perform any checks during a neighbor change. Cases may include
	 * when redstone power is updated, cactus blocks popping off due to a
	 * neighboring solid block, etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn,
			BlockPos fromPos) {
		this.onNeighborChangeInternal(worldIn, pos, state);
	}

	protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.checkForDrop(worldIn, pos, state)) {
			return true;
		} else {
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
			EnumFacing.Axis enumfacing$axis = enumfacing.getAxis();
			EnumFacing enumfacing1 = enumfacing.getOpposite();
			BlockPos blockpos = pos.offset(enumfacing1);
			boolean flag = false;

			if (enumfacing$axis.isHorizontal() && worldIn.getBlockState(blockpos)
					.getBlockFaceShape(worldIn, blockpos, enumfacing) != BlockFaceShape.SOLID) {
				flag = true;
			} else if (enumfacing$axis.isVertical() && !this.canPlaceOn(worldIn, blockpos)) {
				flag = true;
			}

			if (flag) {
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
				return true;
			} else {
				return false;
			}
		}
	}

	protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
		if (state.getBlock() == this
				&& this.canPlaceAt(worldIn, pos, (EnumFacing) state.getValue(FACING))) {
			return true;
		} else {
			if (worldIn.getBlockState(pos).getBlock() == this) {
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
			}

			return false;
		}
	}

	/**
	 * Called periodically clientside on blocks near the player to show effects
	 * (like furnace fire particles). Note that this method is unrelated to
	 * {@link randomTick} and {@link #needsRandomTick}, and will always be
	 * called regardless of whether the block can receive random update ticks
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		EnumFacing enumfacing = (EnumFacing) stateIn.getValue(FACING);
		double d0 = (double) pos.getX() + 0.5D;
		double d1 = (double) pos.getY() + 0.7D;
		double d2 = (double) pos.getZ() + 0.5D;
		// double d3 = 0.22D;
		// double d4 = 0.27D;

		// if (enumfacing.getAxis().isHorizontal()) {
		// EnumFacing enumfacing1 = enumfacing.getOpposite();
		// worldIn.spawnParticle(EnumParticleTypes.PORTAL,
		// d0 + 0.27D * (double) enumfacing1.getFrontOffsetX(), d1 + 0.22D,
		// d2 + 0.27D * (double) enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D,
		// 0.0D);
		//// worldIn.spawnParticle(EnumParticleTypes.FLAME,
		//// d0 + 0.27D * (double) enumfacing1.getFrontOffsetX(), d1 + 0.22D,
		//// d2 + 0.27D * (double) enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D,
		// 0.0D);
		// } else {
		// worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, 0.25D,
		// -0.5D, 0.25D);
		//// worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D,
		// 0.0D, 0.0D);
		// }

		// for (int j = 0; j < 4; ++j) {
		// double d0 = (double)((float)pos.getX() + rand.nextFloat());
		// double d1 = (double)((float)pos.getY() + rand.nextFloat());
		// double d2 = (double)((float)pos.getZ() + rand.nextFloat());
		double d3 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
		double d4 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
		double d5 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
		int k = rand.nextInt(2) * 2 - 1;

		if (rand.nextBoolean()) {
			d2 = (double) pos.getZ() + 0.25D + 0.25D * (double) k;
			d5 = (double) (rand.nextFloat() * 1.0F * (float) k);
		} else {
			d0 = (double) pos.getX() + 0.5D + 0.25D * (double) k;
			d3 = (double) (rand.nextFloat() * 1.0F * (float) k);
		}

		worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
		// }
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState();

		switch (meta) {
		case 1:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST);
			break;
		case 2:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST);
			break;
		case 3:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH);
			break;
		case 4:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH);
			break;
		case 5:
		default:
			iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP);
		}

		return iblockstate;
	}

	/**
	 * Gets the render layer this block will render on. SOLID for solid blocks,
	 * CUTOUT or CUTOUT_MIPPED for on-off transparency (glass, reeds),
	 * TRANSLUCENT for fully blended transparency (stained glass)
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		switch ((EnumFacing) state.getValue(FACING)) {
		case EAST:
			i = i | 1;
			break;
		case WEST:
			i = i | 2;
			break;
		case SOUTH:
			i = i | 3;
			break;
		case NORTH:
			i = i | 4;
			break;
		case DOWN:
		case UP:
		default:
			i = i | 5;
		}

		return i;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed
	 * blockstate. If inapplicable, returns the passed blockstate.
	 */
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 */
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	/**
	 * Get the geometry of the queried face at the given position and state.
	 * This is used to decide whether things like buttons are allowed to be
	 * placed on the face, or how glass panes connect to the face, among other
	 * things.
	 * <p>
	 * Common values are {@code SOLID}, which is the default, and
	 * {@code UNDEFINED}, which represents something that does not fit the other
	 * descriptions and will generally cause other things not to connect to the
	 * face.
	 * 
	 * @return an approximation of the form of the given face
	 */
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos,
			EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	// End copy from BlockTorch

}
