package selim.rifts.tiles;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileRiftConnector extends TileEntityBound {

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (facing == null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return this.hasBoundCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (facing == null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.getBindingInventory());
		return this.getBoundCapability(capability, facing);
	}

}
