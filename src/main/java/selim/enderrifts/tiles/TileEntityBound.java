package selim.enderrifts.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemStackHandler;
import selim.enderrifts.items.ItemBound;
import selim.enderrifts.misc.WorldBlockPos;

public abstract class TileEntityBound extends TileEntity {

	private ItemStackHandler binding = new ItemStackHandler(1);
	private ItemStackHandler fuel = new ItemStackHandler(5);

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		binding.setStackInSlot(0, new ItemStack(nbt.getCompoundTag("binding")));
		NBTTagList fuelInv = nbt.getTagList("fuelInv", 10);
		for (int i = 0; i < fuelInv.tagCount(); i++)
			fuel.setStackInSlot(i, new ItemStack(fuelInv.getCompoundTagAt(i)));
		super.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setTag("binding", this.binding.getStackInSlot(0).serializeNBT());
		NBTTagList fuelInv = new NBTTagList();
		for (int i = 0; i < fuel.getSlots(); i++)
			fuelInv.appendTag(fuel.getStackInSlot(i).serializeNBT());
		nbt.setTag("fuelInv", fuelInv);
		return super.writeToNBT(nbt);
	}

	public ItemStackHandler getBindingInventory() {
		return this.binding;
	}

	public ItemStackHandler getFuelInventory() {
		return this.fuel;
	}

	public WorldBlockPos getBoundPos() {
		ItemStack boundStack = this.getBindingInventory().getStackInSlot(0);
		if (boundStack.isEmpty())
			return null;
		ItemBound bound = (ItemBound) boundStack.getItem();
		WorldBlockPos boundPos = bound.getPos(boundStack);
		return boundPos;
	}

	public boolean hasBoundCapability(Capability<?> capability, EnumFacing facing) {
		ItemStack boundStack = this.getBindingInventory().getStackInSlot(0);
		if (boundStack.isEmpty())
			return false;
		ItemBound bound = (ItemBound) boundStack.getItem();
		WorldBlockPos boundPos = bound.getPos(boundStack);
		if (boundPos == null)
			return false;
		TileEntity te = boundPos.getTileEntity();
		if (te == null)
			return false;
		return te.hasCapability(capability, facing);
	}

	public <T> T getBoundCapability(Capability<T> capability, EnumFacing facing) {
		ItemStack boundStack = this.getBindingInventory().getStackInSlot(0);
		if (boundStack.isEmpty())
			return null;
		ItemBound bound = (ItemBound) boundStack.getItem();
		WorldBlockPos boundPos = bound.getPos(boundStack);
		if (boundPos == null)
			return null;
		return boundPos.getTileEntity().getCapability(capability, facing);
	}

}
