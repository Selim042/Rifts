package selim.enderrifts.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;
import selim.enderrifts.gui.GuiHandler.EnumGui;
import selim.enderrifts.tiles.TileRiftConnector;

public class BlockRiftConnector extends BlockContainer implements IBindable {

	public BlockRiftConnector() {
		super(Material.ROCK);
		this.setRegistryName(ModInfo.ID, "rift_connector");
		this.setUnlocalizedName(ModInfo.ID + ":rift_connector");
		this.setCreativeTab(EnderRifts.mainTab);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileRiftConnector();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player != null)
			player.openGui(EnderRifts.instance, EnumGui.BOUND_BLOCK.getId(), world, pos.getX(),
					pos.getY(), pos.getZ());
		return true;
	}

}
