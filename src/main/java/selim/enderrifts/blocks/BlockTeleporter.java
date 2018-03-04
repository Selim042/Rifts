package selim.enderrifts.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;

public class BlockTeleporter extends BlockContainer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");

	public BlockTeleporter() {
		super(Material.ROCK);
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "teleporter"));
		this.setUnlocalizedName(ModInfo.ID + ":teleporter");
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP)
				.withProperty(TRIGGERED, false));
		this.setCreativeTab(EnderRifts.mainTab);
		this.setHardness(2.0f);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, TRIGGERED });
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX,
			float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState()
				.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer).getOpposite())
				.withProperty(TRIGGERED, Boolean.valueOf(false));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0 | ((EnumFacing) state.getValue(FACING)).getIndex();
		if (((Boolean) state.getValue(TRIGGERED)).booleanValue())
			i |= 8;
		return i;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7))
				.withProperty(TRIGGERED, Boolean.valueOf((meta & 8) > 0));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileTeleporter();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos,
			EnumFacing side) {
		return true;
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
//		System.out.println("onNeighborChanged");
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn,
			BlockPos fromPos) {
//		System.out.println("neighborChanged");
		// Copied from BlockDispenser
		boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
		boolean flag1 = state.getValue(TRIGGERED).booleanValue();
		if (flag && !flag1) {
			worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
			worldIn.setBlockState(pos, state.withProperty(TRIGGERED, Boolean.valueOf(true)), 4);
		} else if (!flag && flag1) {
			worldIn.setBlockState(pos, state.withProperty(TRIGGERED, Boolean.valueOf(false)), 4);
		}
		// End copy from BlockDispenser
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote)
			this.teleport(worldIn, pos);
	}

	protected void teleport(World world, BlockPos pos) {
		System.out.println("teleported");
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof TileTeleporter))
			return;
		TileTeleporter teleporter = (TileTeleporter) te;
		if (teleporter != null) {

		}
	}

	@Override
	public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos,
			EnumFacing side) {
		return true;
	}

	public static enum EnumFacingAxis implements IStringSerializable {
		UP_X,
		UP_Z,
		WEST_Y,
		WEST_Z,
		NORTH_Y,
		NORTH_X,
		DOWN_X,
		DOWN_Z,
		SOUTH_Y,
		SOUTH_X,
		EAST_Y,
		EAST_Z;

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}
	}

}
