package selim.rifts.gui.slots;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import selim.rifts.RiftRegistry;
import selim.rifts.api.BoundFuelEntry;

public class SlotFuel extends SlotItemHandler {

	private static final List<ItemStack> ALLOWED_CACHE = new LinkedList<ItemStack>();
	private static final List<ItemStack> DISALLOWED_CACHE = new LinkedList<ItemStack>();

	public SlotFuel(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return isValid(stack);
	}

	public static boolean isValid(ItemStack stack) {
		stack = stack.copy();
		stack.setCount(1);
		if (DISALLOWED_CACHE.contains(stack))
			return false;
		if (ALLOWED_CACHE.contains(stack))
			return true;
		for (Entry<ResourceLocation, BoundFuelEntry> e : RiftRegistry.Registries.BOUND_FUEL
				.getEntries()) {
			if (e.getValue().getFuel().isItemEqual(stack)) {
				ALLOWED_CACHE.add(stack);
				return true;
			}
		}
		DISALLOWED_CACHE.add(stack);
		return false;
	}

}
