package selim.rifts.blocks;

import net.minecraft.block.BlockAir;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockStateContainer.Builder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import selim.rifts.ModInfo;

public class BlockOpaqueAir extends BlockAir {

	public static final PropertyInteger OPACITY = PropertyInteger.create("opacity", 0, 15);
//	public static final PropertyInteger OPACITY = PropertyInteger.create("opacity", 1, 4);
//	public static final PropertyEnum<EnumOpaqueAirRange> RANGE = PropertyEnum.create("range",
//			BlockOpaqueAir.EnumOpaqueAirRange.class);

	public BlockOpaqueAir() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "opaque_air"));
		this.setDefaultState(this.blockState.getBaseState().withProperty(OPACITY, 1)/*.withProperty(RANGE,
				EnumOpaqueAirRange.NEAR)*/);
	}

	@Override
	public boolean isAir(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(OPACITY).intValue();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { OPACITY/*, RANGE */});
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(OPACITY, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(OPACITY).intValue();
	}

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
		return true;
	}

	public static enum EnumOpaqueAirRange implements IStringSerializable {
		ADJACENT(1),
		NEAR(2),
		MID(4),
		FAR(6);

		private final int range;

		EnumOpaqueAirRange(int range) {
			this.range = range;
		}

		public int getRange() {
			return this.range;
		}

		public static EnumOpaqueAirRange getByRange(int range) {
			for (EnumOpaqueAirRange r : EnumOpaqueAirRange.values())
				if (r.range == range)
					return r;
			return null;
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}
	}

}
