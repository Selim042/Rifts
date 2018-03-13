package selim.enderrifts.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import selim.enderrifts.tiles.TileEntityBound;

public class ContainerBoundInventory extends Container {

	private final InventoryPlayer playerInv;
	private final ItemStackHandler boundHandler;
	private final ItemStackHandler fuelHandler;

	public ContainerBoundInventory(InventoryPlayer playerInv, TileEntityBound teb) {
		this(playerInv, teb.getBindingInventory(), teb.getFuelInventory());
	}

	public ContainerBoundInventory(InventoryPlayer playerInv, ItemStackHandler boundHandler,
			ItemStackHandler fuelHandler) {
		this.playerInv = playerInv;
		this.boundHandler = boundHandler;
		this.fuelHandler = fuelHandler;
		this.addSlotToContainer(new SlotItemHandler(boundHandler, 0, 26, 21));
		for (int i = 0; i < fuelHandler.getSlots(); i++)
			this.addSlotToContainer(new SlotItemHandler(fuelHandler, i, 44 + (i * 18), 53));

		// main player inv
		for (int x = 0; x < 3; x++)
			for (int y = 0; y < 9; y++)
				this.addSlotToContainer(
						new Slot(playerInv, y + (x * 9) + 9, 8 + (y * 18), 84 + (x * 18)));

		// player hotbar
		for (int x = 0; x < 9; x++)
			this.addSlotToContainer(new Slot(playerInv, x, 8 + (x * 18), 142));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	public InventoryPlayer getPlayerInventory() {
		return this.playerInv;
	}

	public ItemStackHandler getBindingInventory() {
		return this.boundHandler;
	}

	public ItemStackHandler getFuelInventory() {
		return this.fuelHandler;
	}

}
