package selim.rifts.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.gui.GuiHandler;
import selim.rifts.tiles.TileEntityBound;
import selim.rifts.tiles.TileRedstoneConnector;

public class BlockRedstoneConnector extends BlockContainer implements IBindable {

	public BlockRedstoneConnector() {
		super(Material.ROCK);
		this.setRegistryName(ModInfo.ID, "redstone_connector");
		this.setUnlocalizedName(ModInfo.ID + ":redstone_connector");
		this.setCreativeTab(EnderRifts.mainTab);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileRedstoneConnector();
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		TileEntity te = blockAccess.getTileEntity(pos);
		if (!(te instanceof TileRedstoneConnector))
			return 0;
		System.out.println("echking2);");
		return ((TileRedstoneConnector) te).getWeakPower(side);
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		TileEntity te = blockAccess.getTileEntity(pos);
		if (!(te instanceof TileRedstoneConnector))
			return 0;
		System.out.println("echking);");
		return ((TileRedstoneConnector) te).getStrongPower(side);
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player != null)
			player.openGui(EnderRifts.instance, GuiHandler.BOUND_BLOCK, world, pos.getX(), pos.getY(),
					pos.getZ());
		return true;
	}

}
