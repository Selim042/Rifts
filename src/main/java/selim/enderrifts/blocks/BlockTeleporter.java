package selim.enderrifts.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
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
import selim.enderrifts.tiles.TileTeleporter;

public class BlockTeleporter extends BlockContainer {

	public static final PropertyEnum<EnumFacingAxis> FACING = PropertyEnum.create("facing",
			BlockTeleporter.EnumFacingAxis.class);

	public BlockTeleporter() {
		super(Material.ROCK);
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "teleporter"));
		this.setUnlocalizedName(ModInfo.ID + ":teleporter");
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacingAxis.UP_X));
		this.setCreativeTab(EnderRifts.mainTab);
		this.setHardness(2.0f);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX,
			float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING,
				EnumFacingAxis.getForPlacement(pos, placer).getOppositeAxis());
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacingAxis) state.getValue(FACING)).getId();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacingAxis.valueOfId(meta));
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
		// System.out.println("onNeighborChanged");
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn,
			BlockPos fromPos) {
		// System.out.println("neighborChanged");
		// Copied from BlockDispenser
		TileEntity te = worldIn.getTileEntity(pos);
		if (!(te instanceof TileTeleporter))
			return;
		TileTeleporter tp = (TileTeleporter) te;
		boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
		boolean flag1 = tp.isTriggered();
		if (flag && !flag1) {
			worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
			tp.setTriggered(true);
//			worldIn.setBlockState(pos, state.withProperty(TRIGGERED, Boolean.valueOf(true)), 4);
		} else if (!flag && flag1) {
			tp.setTriggered(false);
//			worldIn.setBlockState(pos, state.withProperty(TRIGGERED, Boolean.valueOf(false)), 4);
		}
		// End copy from BlockDispenser
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote)
			this.teleport(worldIn, pos);
	}

	protected void teleport(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof TileTeleporter))
			return;
		TileTeleporter tp = (TileTeleporter) te;
		if (tp != null && tp.isTriggered()) {
			System.out.println("teleported");
		}
	}

	@Override
	public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos,
			EnumFacing side) {
		return true;
	}

	public static enum EnumFacingAxis implements IStringSerializable {
		DOWN_X(0, 1, 2, EnumFacing.DOWN),
		DOWN_Z(1, 0, 3, EnumFacing.DOWN),
		UP_X(2, 3, 0, EnumFacing.UP),
		UP_Z(3, 2, 3, EnumFacing.UP),
		NORTH_Y(4, 5, 6, EnumFacing.NORTH),
		NORTH_X(5, 4, 7, EnumFacing.NORTH),
		SOUTH_Y(6, 7, 4, EnumFacing.SOUTH),
		SOUTH_X(7, 6, 5, EnumFacing.SOUTH),
		EAST_Y(8, 9, 10, EnumFacing.EAST),
		EAST_Z(9, 8, 11, EnumFacing.EAST),
		WEST_Y(10, 11, 8, EnumFacing.WEST),
		WEST_Z(11, 10, 9, EnumFacing.WEST);

		private final static EnumFacingAxis[] VALUES = new EnumFacingAxis[12];

		private final int id;
		private final int oppositeAxis;
		private final int oppositeDir;
		private final EnumFacing vanillaFacing;

		EnumFacingAxis(int id, int oppositeAxis, int oppositeDir, EnumFacing vanillaFacing) {
			this.id = id;
			this.oppositeAxis = oppositeAxis;
			this.oppositeDir = oppositeDir;
			this.vanillaFacing = vanillaFacing;
		}

		public int getId() {
			return this.id;
		}

		public EnumFacingAxis getOppositeAxis() {
			return VALUES[this.oppositeAxis];
		}

		public EnumFacingAxis getOppositeDir() {
			return VALUES[this.oppositeDir];
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

		public static EnumFacingAxis getForPlacement(BlockPos pos, EntityLivingBase placer) {
			if (Math.abs(placer.posX - (double) ((float) pos.getX() + 0.5F)) < 2.0D
					&& Math.abs(placer.posZ - (double) ((float) pos.getZ() + 0.5F)) < 2.0D) {
				double d0 = placer.posY + (double) placer.getEyeHeight();
				if (d0 - (double) pos.getY() > 2.0D)
					if (placer.isSneaking())
						return UP_X;
					else
						return UP_Z;
				if ((double) pos.getY() - d0 > 0.0D)
					if (placer.isSneaking())
						return UP_X;
					else
						return UP_Z;
			}
			return getHorizontalFacing(placer.getHorizontalFacing(), placer).getOppositeDir();
		}

		public static EnumFacingAxis getHorizontalFacing(EnumFacing facing, EntityLivingBase placer) {
			for (EnumFacingAxis fa : values())
				if (fa.vanillaFacing.equals(facing))
					if (placer.isSneaking())
						return VALUES[fa.oppositeAxis];
					else
						return fa;
			return null;
		}

		public static EnumFacingAxis valueOfId(int id) {
			if (id < 0 || id >= VALUES.length)
				return null;
			return VALUES[id];
		}

		static {
			for (EnumFacingAxis fa : values())
				VALUES[fa.id] = fa;
		}
	}

}
