package selim.enderrifts.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.enderrifts.ModInfo;
import selim.enderrifts.tiles.TileRiftPortal;
import selim.enderrifts.world.DimensionRift;
import selim.enderrifts.world.TeleporterCreative;

public class BlockRift extends BlockContainer {

	protected static final AxisAlignedBB END_PORTAL_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D,
			0.05D, 1.0D);
	protected static final AxisAlignedBB CACTUS_COLLISION_AABB = new AxisAlignedBB(0.0625D, 0.0D,
			0.0625D, 0.9375D, 0.9375D, 0.9375D);

	public BlockRift() {
		super(Material.ROCK);
		this.setRegistryName(ModInfo.ID, "rift_test");
		this.setUnlocalizedName(ModInfo.ID + ":rift_test");
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return END_PORTAL_AABB;
	}

	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		return side == EnumFacing.DOWN ? super.shouldSideBeRendered(blockState, blockAccess, pos, side)
				: false;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos,
			AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn,
			boolean isActualState) {}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
//		if (entity instanceof EntityPlayer)
//			teleport(world, pos, state, (EntityPlayer) entity);
	}

	@SideOnly(Side.CLIENT)
	@Override
	// TODO: Give it "portal" particles (enderman/nether portal)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return ItemStack.EMPTY;
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.BLACK;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos,
			EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//		return teleport(world, pos, state, player);
		return false;
	}

	private boolean teleport(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (world.isRemote || !(player instanceof EntityPlayerMP))
			return true;
		if (!(!world.isRemote && !player.isRiding() && !player.isBeingRidden() && player.isNonBoss()
				&& player.getEntityBoundingBox()
						.intersects(state.getBoundingBox(world, pos).offset(pos))))
			return true;
		// TeleporterCreative teleporter = new TeleporterCreative(
		// entity.getServer().getWorld(DimensionRift.DIMENSION_ID));
		if (player instanceof EntityPlayerMP) {
			if (player.getEntityWorld().provider.getDimensionType().equals(DimensionType.OVERWORLD)) {
				TeleporterCreative.changeDimension(player, DimensionRift.DIMENSION_ID);
				// CustomTeleporter.teleportToDimension((EntityPlayerMP)
				// playerIn,
				// DimensionRift.DIMENSION_ID, playerIn.getPosition());
			} else {
				TeleporterCreative.changeDimension(player, 0);
				// CustomTeleporter.teleportToDimension((EntityPlayerMP)
				// playerIn, 0,
				// playerIn.getPosition());
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileRiftPortal();
	}

}
