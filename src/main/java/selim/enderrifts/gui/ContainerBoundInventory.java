package selim.enderrifts.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import selim.enderrifts.gui.slots.SlotBinding;
import selim.enderrifts.gui.slots.SlotFuel;
import selim.enderrifts.items.ItemRiftLink;
import selim.enderrifts.tiles.TileEntityBound;
import selim.enderrifts.tiles.TileEntityBound.BindingCheck;

public class ContainerBoundInventory extends Container {

	private final InventoryPlayer playerInv;
	private final ItemStackHandler boundHandler;
	private final ItemStackHandler fuelHandler;
	private final SlotBinding boundSlot;

	public ContainerBoundInventory(InventoryPlayer playerInv, TileEntityBound teb) {
		this(playerInv, teb.getBindingCheck(), teb.getBindingInventory(), teb.getFuelInventory());
	}

	public ContainerBoundInventory(InventoryPlayer playerInv, BindingCheck check,
			ItemStackHandler boundHandler, ItemStackHandler fuelHandler) {
		this.playerInv = playerInv;
		this.boundHandler = boundHandler;
		this.fuelHandler = fuelHandler;
		this.boundSlot = (SlotBinding) this
				.addSlotToContainer(new SlotBinding(check, boundHandler, 0, 26, 21));
		for (int i = 0; i < fuelHandler.getSlots(); i++)
			this.addSlotToContainer(new SlotFuel(fuelHandler, i, 44 + (i * 18), 53));

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
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		Slot slot = this.getSlot(index);
		ItemStack stack = slot.getStack();
		if (stack.isEmpty())
			return stack;
		if (stack.getItem() instanceof ItemRiftLink && this.boundSlot.getStack().isEmpty()) {
			this.boundSlot.putStack(stack);
			slot.putStack(ItemStack.EMPTY);
			return ItemStack.EMPTY;
		} else if (this.boundSlot.equals(slot)) {
			boolean trans = this.playerInv.addItemStackToInventory(stack);
			if (trans) {
				slot.putStack(ItemStack.EMPTY);
				return ItemStack.EMPTY;
			} else
				return stack;
		}
		return stack;
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
