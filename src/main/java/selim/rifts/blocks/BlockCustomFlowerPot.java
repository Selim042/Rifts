package selim.rifts.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.rifts.ModInfo;

// TODO: Do I need a TE for this??
public class BlockCustomFlowerPot extends Block/* Container */ {

	public static final PropertyEnum<EnumFlowerType> CONTENTS = PropertyEnum.create("contents",
			BlockCustomFlowerPot.EnumFlowerType.class);
	// Borrowed AxisAlignedBB from BlockFlowerPot
	protected static final AxisAlignedBB FLOWER_POT_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D,
			0.6875D, 0.375D, 0.6875D);

	public BlockCustomFlowerPot() {
		super(Material.CIRCUITS);
		this.setRegistryName(ModInfo.ID, "flower_pot");
		// Sets to same unlocalized name as vanilla, just in case it is needed
		this.setUnlocalizedName("flower_pot");
		this.setDefaultState(this.blockState.getBaseState().withProperty(CONTENTS,
				BlockCustomFlowerPot.EnumFlowerType.EMPTY));
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FLOWER_POT_AABB;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
			EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY,
			float hitZ) {
		ItemStack heldStack = playerIn.getHeldItem(hand);
		EnumFlowerType heldType = EnumFlowerType.valueOfItem(heldStack);
		EnumFlowerType currentType = state.getValue(CONTENTS);
		if (currentType == EnumFlowerType.EMPTY) {
			if (!this.canBePotted(heldStack))
				return false;
			worldIn.setBlockState(pos, state.withProperty(CONTENTS, heldType));
			playerIn.addStat(StatList.FLOWER_POTTED);
			if (!playerIn.capabilities.isCreativeMode)
				heldStack.shrink(1);
		} else {
			ItemStack currentStack = currentType.getItemStack();
			if (heldStack.isEmpty())
				playerIn.setHeldItem(hand, currentStack);
			else if (!playerIn.addItemStackToInventory(currentStack))
				playerIn.dropItem(currentStack, false);
			worldIn.setBlockState(pos, Blocks.FLOWER_POT.getDefaultState());
			worldIn.notifyBlockUpdate(pos, state, state, 3);
			return true;
		}
		return false;
	}

	private boolean canBePotted(ItemStack stack) {
		BlockCustomFlowerPot.EnumFlowerType type = BlockCustomFlowerPot.EnumFlowerType
				.valueOfItem(stack.getItem());
		return type != null && type != BlockCustomFlowerPot.EnumFlowerType.EMPTY;
	}

	// Start borrow from BlockFlowerPot
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		BlockCustomFlowerPot.EnumFlowerType type = state.getValue(CONTENTS);
		if (type != null) {
			ItemStack stack = type.getItemStack();
			if (!stack.isEmpty())
				return stack;
		}
		return new ItemStack(Items.FLOWER_POT);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		return getItem(world, pos, state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.down()).isTopSolid();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn,
			BlockPos fromPos) {
		if (!worldIn.getBlockState(pos.down()).isTopSolid()) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.FLOWER_POT;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { CONTENTS });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(CONTENTS).id;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(CONTENTS, EnumFlowerType.valueOfId(meta));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos,
			EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	// End borrow from BlockFlowerPot

	public static enum EnumFlowerType implements IStringSerializable {
		EMPTY(0),
		RIFT_FLOWER(1, new ResourceLocation(ModInfo.ID, "rift_flower")),
		WILLOW_SAPLING(2, new ResourceLocation(ModInfo.ID, "willow_sapling"));

		private final int id;
		private Item item;
		private final ResourceLocation loc;

		EnumFlowerType(int id) {
			this.id = id;
			this.loc = null;
		}

		EnumFlowerType(int id, Item item) {
			this.id = id;
			this.item = item;
			this.loc = item == null ? null : item.getRegistryName();
		}

		EnumFlowerType(int id, ResourceLocation loc) {
			this.id = id;
			this.loc = loc;
		}

		public int getId() {
			return this.id;
		}

		public static EnumFlowerType valueOfId(int id) {
			for (EnumFlowerType type : EnumFlowerType.values())
				if (type.id == id)
					return type;
			return null;
		}

		public Item getItem() {
			checkItem();
			return this.item;
		}

		public ItemStack getItemStack() {
			checkItem();
			return this.item == null ? ItemStack.EMPTY : new ItemStack(this.item);
		}

		public static EnumFlowerType valueOfItem(ItemStack item) {
			return valueOfItem(item.getItem());
		}

		public static EnumFlowerType valueOfItem(Item item) {
			if (item == null || item.equals(Items.AIR))
				return EMPTY;
			for (EnumFlowerType type : EnumFlowerType.values()) {
				type.checkItem();
				// System.out.println(type + ":" + type.item);
				if (item.equals(type.item)) {
					return type;
				}
			}
			return null;
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

		private void checkItem() {
			if (item != null || this.loc == null)
				return;
			Item item = ForgeRegistries.ITEMS.getValue(this.loc);
			if (item != null)
				this.item = item;
		}
	}

}
