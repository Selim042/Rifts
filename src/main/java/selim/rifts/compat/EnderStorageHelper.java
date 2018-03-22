package selim.rifts.compat;

import codechicken.enderstorage.block.BlockEnderStorage;
import codechicken.enderstorage.init.ModBlocks;
import codechicken.enderstorage.tile.TileEnderChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import selim.rifts.items.ItemBound;
import selim.rifts.items.ItemEnderLink;
import selim.rifts.misc.WorldBlockPos;

public class EnderStorageHelper {

	public static final String ID = "enderstorage";

	public static boolean isEnderChest(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		return state != null && state.getBlock().equals(ModBlocks.blockEnderStorage);
	}

	public static void openChest(EntityPlayer player, ItemStack stack) {
		if (stack.isEmpty() || !(stack.getItem() instanceof ItemEnderLink))
			return;
		WorldBlockPos pos = ((ItemBound) stack.getItem()).getBoundPos(stack);
		IBlockState state = pos.getState();
		if (state != null && state.getBlock() instanceof BlockEnderStorage) {
			TileEntity te = pos.getTileEntity();
			if (te == null || !(te instanceof TileEnderChest))
				return;
			TileEnderChest tec = (TileEnderChest) te;
			tec.getStorage().openSMPGui(player, "tile.enderChest.name");
		}
	}

}
