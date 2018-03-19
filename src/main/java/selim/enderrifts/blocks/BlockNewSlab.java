package selim.enderrifts.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import selim.enderrifts.EnderRifts;

public abstract class BlockNewSlab extends BlockSlab {

	public static final IProperty<EnumVariant> VARIANT = PropertyEnum.<EnumVariant>create("variant",
			EnumVariant.class);
	protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D,
			0.5D, 1.0D);
	protected static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D,
			1.0D);

	private final IBlockState modelState;

	public BlockNewSlab(final Block block) {
		this(block.getDefaultState());
	}

	public BlockNewSlab(final IBlockState modelState) {
		super(modelState.getMaterial());
		// this.setRegistryName(modelState.getBlock().getRegistryName() +
		// "_slab");
		this.modelState = modelState;
		this.useNeighborBrightness = !this.isDouble();
		IBlockState blockState = this.blockState.getBaseState().withProperty(VARIANT,
				EnumVariant.NORMAL);
		if (!this.isDouble())
			blockState = blockState.withProperty(HALF, EnumBlockHalf.BOTTOM);
		setDefaultState(blockState);
	}

	@Override
	public String getUnlocalizedName(int meta) {
		return modelState.getBlock().getUnlocalizedName().substring(5) + "_slab";
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return this.isDouble() ? FULL_BLOCK_AABB
				: (state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP ? AABB_TOP_HALF
						: AABB_BOTTOM_HALF);
	}

	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		if (!this.isDouble())
			return EnderRifts.mainTab;
		return null;
	}

	@Override
	protected final BlockStateContainer createBlockState() {
		if (this.isDouble()) {
			return new BlockStateContainer(this, new IProperty[] { VARIANT });
		} else {
			return new BlockStateContainer(this, new IProperty[] { HALF, VARIANT });
		}
	}

	@Override
	public final IBlockState getStateFromMeta(final int meta) {
		IBlockState blockState = this.getDefaultState();
		if (!this.isDouble()) {
			EnumBlockHalf value = EnumBlockHalf.BOTTOM;
			if (meta != 0)
				value = EnumBlockHalf.TOP;
			blockState = blockState.withProperty(HALF, value);
		}
		return blockState;
	}

	@Override
	public final int getMetaFromState(final IBlockState state) {
		if (this.isDouble() || ((EnumBlockHalf) state.getValue(HALF)).equals(EnumBlockHalf.BOTTOM))
			return 0;
		return 1;
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return VARIANT;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return EnumVariant.NORMAL;
	}

	public static class BlockNewHalfSlab extends BlockNewSlab {

		private final IBlockState modelState;

		public BlockNewHalfSlab(final Block block) {
			this(block.getDefaultState());
		}

		public BlockNewHalfSlab(final IBlockState modelState) {
			super(modelState);
			this.modelState = modelState;
			this.setRegistryName(modelState.getBlock().getRegistryName() + "_slab");
		}

		@Override
		public boolean isDouble() {
			return false;
		}

	}

	public static class BlockNewDoubleSlab extends BlockNewSlab {

		private final IBlockState modelState;

		public BlockNewDoubleSlab(final Block block) {
			this(block.getDefaultState());
		}

		public BlockNewDoubleSlab(final IBlockState modelState) {
			super(modelState);
			this.modelState = modelState;
			this.setRegistryName(modelState.getBlock().getRegistryName() + "_double_slab");
		}

		@Override
		public boolean isDouble() {
			return true;
		}

	}

	public static enum EnumVariant implements IStringSerializable {
		NORMAL;

		@Override
		public String getName() {
			return "normal";
		}
	}

}