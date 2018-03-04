package selim.enderrifts.compat;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BaublesHelper {

	public static final String ID = "baubles";

	public static int isBaubleEquipped(EntityPlayer player, Item item) {
		return BaublesApi.isBaubleEquipped(player, item);
	}

	public static IBaublesItemHandler getBaublesHandler(EntityPlayer player) {
		return BaublesApi.getBaublesHandler(player);
	}

	public static ItemStack getBauble(EntityPlayer player, int slot) {
		return getBaublesHandler(player).getStackInSlot(slot);
	}

	public static ItemStack getBauble(EntityPlayer player, Item item) {
		IBaublesItemHandler handler = getBaublesHandler(player);
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack stack = handler.getStackInSlot(i);
			if (stack.getItem().equals(item))
				return stack;
		}
		return ItemStack.EMPTY;
	}

}
