package selim.enderrifts.blocks;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;

public class BlockWillowLog extends BlockRotatedPillar {

	public static final PropertyEnum<BlockLog.EnumAxis> LOG_AXIS = PropertyEnum.<BlockLog.EnumAxis>create(
			"axis", BlockLog.EnumAxis.class);

	public BlockWillowLog() {
		super(Material.WOOD);
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "willow_log"));
		this.setUnlocalizedName(ModInfo.ID + ":willow_log");
		this.setCreativeTab(EnderRifts.mainTab);
		this.setHardness(8.0F);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (worldIn.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5))) {
			for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
				IBlockState iblockstate = worldIn.getBlockState(blockpos);
				if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos))
					iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
			}
		}
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX,
			float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getStateFromMeta(meta).withProperty(LOG_AXIS,
				BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		switch (meta) {
		case 0:
			return this.getDefaultState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
		case 1:
			return this.getDefaultState().withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
		case 2:
			return this.getDefaultState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
		default:
			return this.getDefaultState().withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		switch (state.getValue(LOG_AXIS)) {
		case Y:
			return 0;
		case X:
			return 1;
		case Z:
			return 2;
		default:
			return 3;
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { LOG_AXIS });
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		switch (rot) {
		case COUNTERCLOCKWISE_90:
		case CLOCKWISE_90:
			switch ((BlockLog.EnumAxis) state.getValue(LOG_AXIS)) {
			case X:
				return state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
			case Z:
				return state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
			default:
				return state;
			}
		default:
			return state;
		}
	}

	@Override
	public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world,
			BlockPos pos) {
		return true;
	}

	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos) {
		return true;
	}

}
