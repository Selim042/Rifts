package selim.enderrifts.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;
import selim.enderrifts.utils.MiscUtils;
import selim.enderrifts.utils.MiscUtils.EnumHoliday;

public class BlockBarite extends Block {

	public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

	public BlockBarite() {
		super(Material.ROCK);
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "barite"));
		if (MiscUtils.getHoliday().equals(EnumHoliday.APRIL_FOOLS))
			this.setUnlocalizedName(ModInfo.ID + ":barite_april_fools");
		else
			this.setUnlocalizedName(ModInfo.ID + ":barite");
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.ROUGH));
		this.setCreativeTab(EnderRifts.mainTab);
		this.setHardness(1.5f);
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (!tab.equals(this.getCreativeTabToDisplayOn()))
			return;
		for (final EnumType enumType : EnumType.values()) {
			list.add(new ItemStack(this, 1, enumType.getId()));
		}
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this, 1, state.getValue(TYPE).id);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		return getItem(world, pos, state);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).getId();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, EnumType.valueOf(meta));
	}

	public static enum EnumType implements IStringSerializable {
		ROUGH(0),
		SMOOTH(1),
		BRICK(2);

		private final int id;

		EnumType(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}

		public static EnumType valueOf(int id) {
			for (EnumType v : EnumType.values())
				if (v.id == id)
					return v;
			return null;
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

	}

}
