package selim.rifts.blocks;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.PlayerList;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.entities.EntityPhantomCart;
import selim.rifts.gui.GuiHandler;
import selim.rifts.misc.WorldBlockPos;
import selim.rifts.tiles.TileRiftRail;
import selim.rifts.world.RiftTeleporter;

public class BlockRiftRail extends BlockRailBase implements ITileEntityProvider, IBindable {

	public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.<BlockRailBase.EnumRailDirection>create(
			"shape", BlockRailBase.EnumRailDirection.class,
			new Predicate<BlockRailBase.EnumRailDirection>() {

				public boolean apply(@Nullable BlockRailBase.EnumRailDirection dir) {
					return dir != BlockRailBase.EnumRailDirection.NORTH_EAST
							&& dir != BlockRailBase.EnumRailDirection.NORTH_WEST
							&& dir != BlockRailBase.EnumRailDirection.SOUTH_EAST
							&& dir != BlockRailBase.EnumRailDirection.SOUTH_WEST;
				}
			});

	public BlockRiftRail() {
		super(true);
		this.setRegistryName(ModInfo.ID, "rift_rail");
		this.setUnlocalizedName(ModInfo.ID + ":rift_rail");
		this.setCreativeTab(EnderRifts.mainTab);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(SHAPE,
				BlockRailBase.EnumRailDirection.NORTH_SOUTH));
		this.hasTileEntity = true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileRiftRail();
	}

	@Override
	public IProperty<EnumRailDirection> getShapeProperty() {
		return SHAPE;
	}

	// @Override
	// public EnumRailDirection getRailDirection(IBlockAccess world, BlockPos
	// pos, IBlockState state,
	// @Nullable EntityMinecart cart) {
	// return state.getValue(SHAPE);
	// }

	@Override
	public BlockStateContainer getBlockState() {
		return new BlockStateContainer(this, new IProperty[] { SHAPE });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(SHAPE,
				BlockRailBase.EnumRailDirection.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(SHAPE).getMetadata();
	}

	@Override
	public void onMinecartPass(World world, EntityMinecart cart, BlockPos pos) {
		if (world.isRemote)
			return;
		TileEntity te = world.getTileEntity(pos);
		if (te == null || !(te instanceof TileRiftRail))
			return;
		TileRiftRail trr = (TileRiftRail) te;
		WorldBlockPos boundPos = trr.getBoundPos();
		if (boundPos != null && boundPos.getState().getBlock() instanceof BlockRailBase) {
			System.out.println("spawned: " + world.spawnEntity(new EntityPhantomCart(world, cart)));
			int currentDim = world.provider.getDimension();
			WorldServer currentWorld = FMLCommonHandler.instance().getMinecraftServerInstance()
					.getWorld(currentDim);
			if (!currentWorld.equals(boundPos.getWorldServer())) {
				PlayerList playerList = FMLCommonHandler.instance().getMinecraftServerInstance()
						.getPlayerList();
				RiftTeleporter tp = new RiftTeleporter(currentWorld);
				if (cart.getPassengers().size() != 0)
					return;
				for (Entity e : cart.getPassengers()) {
					if (e instanceof EntityPlayerMP)
						playerList.transferPlayerToDimension((EntityPlayerMP) e, boundPos.getDimension(),
								tp);
					else
						playerList.transferEntityToWorld(e, currentDim, currentWorld,
								boundPos.getWorldServer(), tp);
					e.setPosition(boundPos.getX(), boundPos.getY(), boundPos.getZ());
				}
				playerList.transferEntityToWorld(cart, currentDim, currentWorld,
						boundPos.getWorldServer(), tp);
			}
			cart.moveToBlockPosAndAngles(boundPos, cart.rotationYaw, cart.rotationPitch);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player.getHeldItem(hand).getItem() instanceof ItemMinecart)
			return false;
		if (player != null)
			player.openGui(EnderRifts.instance, GuiHandler.BOUND_BLOCK, world, pos.getX(),
					pos.getY(), pos.getZ());
		return true;
	}

	// Start copy from BlockContainer
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state,
			@Nullable TileEntity te, ItemStack stack) {
		if (te instanceof IWorldNameable && ((IWorldNameable) te).hasCustomName()) {
			player.addStat(StatList.getBlockStats(this));
			player.addExhaustion(0.005F);

			if (worldIn.isRemote) {
				return;
			}

			int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
			Item item = this.getItemDropped(state, worldIn.rand, i);

			if (item == Items.AIR) {
				return;
			}

			ItemStack itemstack = new ItemStack(item, this.quantityDropped(worldIn.rand));
			itemstack.setStackDisplayName(((IWorldNameable) te).getName());
			spawnAsEntity(worldIn, pos, itemstack);
		} else {
			super.harvestBlock(worldIn, player, pos, state, (TileEntity) null, stack);
		}
	}

	@Override
	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
		super.eventReceived(state, worldIn, pos, id, param);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}
	// End copy from BlockContainer

}
