package selim.rifts.tiles;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import selim.rifts.items.ItemBound;
import selim.rifts.misc.WorldBlockPos;

public class TileRiftRail extends TileEntityBound {

	@Override
	public BindingCheck getBindingCheck() {
		return new BindingCheck() {

			@Override
			public boolean isBindingValid(ItemStack stack) {
				if (stack.isEmpty() || !(stack.getItem() instanceof ItemBound))
					return false;
				WorldBlockPos pos = ((ItemBound) stack.getItem()).getBoundPos(stack);
				IBlockState state = pos.getState();
				return state.getBlock() instanceof BlockRailBase;
			}

		};
	}

}
