package selim.rifts.gui.slots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import selim.rifts.tiles.TileEntityBound.BindingCheck;

public class SlotBinding extends SlotItemHandler {

	private final BindingCheck check;

	public SlotBinding(BindingCheck check, IItemHandler itemHandler, int index, int xPosition,
			int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.check = check;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		// TODO: Make this work on multiplayer
		// return check != null ? check.isBindingValid(stack) : false;
		return true;
	}

}
