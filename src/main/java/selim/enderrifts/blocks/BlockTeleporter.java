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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;
import selim.enderrifts.gui.GuiHandler;
import selim.enderrifts.tiles.TileTeleporter;

public class BlockTeleporter extends BlockContainer implements IBindable {

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
		return state.getValue(FACING).getId();
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
			// worldIn.setBlockState(pos, state.withProperty(TRIGGERED,
			// Boolean.valueOf(true)), 4);
		} else if (!flag && flag1) {
			tp.setTriggered(false);
			// worldIn.setBlockState(pos, state.withProperty(TRIGGERED,
			// Boolean.valueOf(false)), 4);
		}
		// End copy from BlockDispenser
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote && worldIn.getTileEntity(pos) instanceof TileTeleporter) {
			TileTeleporter teleporter = (TileTeleporter) worldIn.getTileEntity(pos);
			teleporter.teleport(worldIn,pos);
		}
	}

	@Override
	public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos,
			EnumFacing side) {
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof TileTeleporter))
			return;
		((TileTeleporter) te).setPlacer(placer);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player != null)
			player.openGui(EnderRifts.instance, GuiHandler.BOUND_BLOCK, world, pos.getX(), pos.getY(),
					pos.getZ());
		return true;
	}

	public static enum EnumFacingAxis implements IStringSerializable {
		DOWN_X(0, 1, 2, EnumFacing.DOWN, EnumFacing.Axis.X),
		DOWN_Z(1, 0, 3, EnumFacing.DOWN, EnumFacing.Axis.Z),
		UP_X(2, 3, 0, EnumFacing.UP, EnumFacing.Axis.X),
		UP_Z(3, 2, 3, EnumFacing.UP, EnumFacing.Axis.Z),
		NORTH_Y(4, 5, 6, EnumFacing.NORTH, EnumFacing.Axis.Y),
		NORTH_X(5, 4, 7, EnumFacing.NORTH, EnumFacing.Axis.X),
		SOUTH_Y(6, 7, 4, EnumFacing.SOUTH, EnumFacing.Axis.Y),
		SOUTH_X(7, 6, 5, EnumFacing.SOUTH, EnumFacing.Axis.X),
		EAST_Y(8, 9, 10, EnumFacing.EAST, EnumFacing.Axis.Y),
		EAST_Z(9, 8, 11, EnumFacing.EAST, EnumFacing.Axis.Z),
		WEST_Y(10, 11, 8, EnumFacing.WEST, EnumFacing.Axis.Y),
		WEST_Z(11, 10, 9, EnumFacing.WEST, EnumFacing.Axis.Z);

		private final static EnumFacingAxis[] VALUES = new EnumFacingAxis[12];

		private final int id;
		private final int oppositeAxis;
		private final int oppositeDir;
		private final EnumFacing vanillaFacing;
		private final EnumFacing.Axis axis;

		EnumFacingAxis(int id, int oppositeAxis, int oppositeDir, EnumFacing vanillaFacing,
				EnumFacing.Axis axis) {
			this.id = id;
			this.oppositeAxis = oppositeAxis;
			this.oppositeDir = oppositeDir;
			this.vanillaFacing = vanillaFacing;
			this.axis = axis;
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

		public EnumFacing getVanillaFacing() {
			return this.vanillaFacing;
		}

		public EnumFacing.Axis getAxis() {
			return this.axis;
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

		public static EnumFacingAxis getForPlacement(BlockPos pos, EntityLivingBase placer) {
			// Math logic copied from EnumFacing
			if (Math.abs(placer.posX - (double) ((float) pos.getX() + 0.5F)) < 2.0D
					&& Math.abs(placer.posZ - (double) ((float) pos.getZ() + 0.5F)) < 2.0D) {
				double d0 = placer.posY + (double) placer.getEyeHeight();
				if (d0 - (double) pos.getY() > 2.0D)
					switch (placer.getHorizontalFacing().getAxis()) {
					case X:
						return UP_X;
					case Z:
						return UP_Z;
					default:
						break;
					}
				if ((double) pos.getY() - d0 > 0.0D) {
					switch (placer.getHorizontalFacing().getAxis()) {
					case X:
						return DOWN_X;
					case Z:
						return DOWN_Z;
					default:
						break;
					}
				}
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
