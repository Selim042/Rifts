package selim.rifts.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import selim.rifts.misc.WorldBlockPos;

public class TileRedstoneConnector extends TileEntityBound {

	public int getWeakPower(EnumFacing side) {
		if (!this.isBound())
			return 0;
		WorldBlockPos pos = this.getBoundPos();
		IBlockState state = pos.getState();
		return state.getWeakPower(world, pos, side);
	}

	public int getStrongPower(EnumFacing side) {
		if (!this.isBound())
			return 0;
		WorldBlockPos pos = this.getBoundPos();
		IBlockState state = pos.getState();
		return state.getStrongPower(world, pos, side);
	}

	// @Override
	// public BindingCheck getBindingCheck() {
	// return new BindingCheck() {
	//
	// @Override
	// public boolean isBindingValid(ItemStack stack) {
	// return false;
	// }};
	// }

}
