package selim.enderrifts.tiles;

import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemStackHandler;
import selim.enderrifts.RiftRegistry;
import selim.enderrifts.api.BoundFuelEntry;
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
		WorldBlockPos boundPos = bound.getBoundPos(boundStack);
		return boundPos;
	}

	public boolean isBound() {
		ItemStack boundStack = this.getBindingInventory().getStackInSlot(0);
		if (boundStack.isEmpty())
			return false;
		ItemBound bound = (ItemBound) boundStack.getItem();
		return bound.isBound(boundStack) && this.getBindingCheck().isBindingValid(boundStack);
	}

	public boolean hasBoundCapability(Capability<?> capability, EnumFacing facing) {
		ItemStack boundStack = this.getBindingInventory().getStackInSlot(0);
		if (boundStack.isEmpty())
			return false;
		ItemBound bound = (ItemBound) boundStack.getItem();
		WorldBlockPos boundPos = bound.getBoundPos(boundStack);
		if (boundPos == null || !boundPos.isLoaded())
			return false;
		TileEntity te = boundPos.getTileEntity();
		if (te == null)
			return false;
		return te.hasCapability(capability, facing);
	}

	public int getFuel() {
		int curFuel = 0;
		for (int s = 0; s < this.fuel.getSlots(); s++) {
			ItemStack stack = this.fuel.getStackInSlot(s);
			for (Entry<ResourceLocation, BoundFuelEntry> e : RiftRegistry.Registries.BOUND_FUEL
					.getEntries()) {
				BoundFuelEntry entry = e.getValue();
				ItemStack entryStack = entry.getFuel();
				if (!stack.isItemEqual(entryStack))
					continue;
				curFuel += stack.getCount() * entry.getPower();
			}
		}
		return curFuel;
	}

	public int getFuelUsage(World boundWorld, BlockPos boundPos) {
		double ratio = this.world.provider.getMovementFactor() / boundWorld.provider.getMovementFactor();
		double dist = this.pos.getDistance(boundPos.getX(), boundPos.getY(), boundPos.getZ());
		return (int) (0.5 * ratio * dist) + (this.world.equals(boundWorld) ? 0 : 50);
	}

	public <T> T getBoundCapability(Capability<T> capability, EnumFacing facing) {
		ItemStack boundStack = this.getBindingInventory().getStackInSlot(0);
		if (boundStack.isEmpty())
			return null;
		ItemBound bound = (ItemBound) boundStack.getItem();
		WorldBlockPos boundPos = bound.getBoundPos(boundStack);
		if (boundPos == null || !boundPos.isLoaded())
			return null;
		return boundPos.getTileEntity().getCapability(capability, facing);
	}

	public BindingCheck getBindingCheck() {
		return BindingCheck.ALLOW_ALL;
	}

	public abstract static class BindingCheck {

		public static final BindingCheck ALLOW_ALL = new BindingCheck() {

			@Override
			public boolean isBindingValid(ItemStack stack) {
				return true;
			}
		};

		public abstract boolean isBindingValid(ItemStack stack);
	}

}
