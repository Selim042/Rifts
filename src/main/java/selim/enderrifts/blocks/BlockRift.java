package selim.enderrifts.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;
import selim.enderrifts.world.DimensionRift;
import selim.enderrifts.world.TeleporterCreative;

public class BlockRift extends Block {

	public BlockRift() {
		super(Material.ROCK);
		this.setRegistryName(ModInfo.ID, "rift_test");
		this.setUnlocalizedName(ModInfo.ID + ":rift_test");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
			EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY,
			float hitZ) {
		if (worldIn.isRemote || !(playerIn instanceof EntityPlayerMP))
			return true;
		// TeleporterCreative teleporter = new TeleporterCreative(
		// entity.getServer().getWorld(DimensionRift.DIMENSION_ID));
		if (playerIn instanceof EntityPlayerMP) {
			if (playerIn.getEntityWorld().provider.getDimensionType().equals(DimensionType.OVERWORLD)) {
				TeleporterCreative.changeDimension(playerIn, DimensionRift.DIMENSION_ID);
				// CustomTeleporter.teleportToDimension((EntityPlayerMP)
				// playerIn,
				// DimensionRift.DIMENSION_ID, playerIn.getPosition());
			} else {
				TeleporterCreative.changeDimension(playerIn, 0);
				// CustomTeleporter.teleportToDimension((EntityPlayerMP)
				// playerIn, 0,
				// playerIn.getPosition());
			}
		}
		return true;
	}

}
