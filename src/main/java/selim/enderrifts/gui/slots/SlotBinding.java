package selim.enderrifts.gui.slots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import selim.enderrifts.tiles.TileEntityBound.BindingCheck;

public class SlotBinding extends SlotItemHandler {

	private final BindingCheck check;

	public SlotBinding(BindingCheck check, IItemHandler itemHandler, int index, int xPosition,
			int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.check = check;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return check != null ? check.isBindingValid(stack) : false;
	}

}
