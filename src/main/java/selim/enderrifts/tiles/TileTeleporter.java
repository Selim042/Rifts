package selim.enderrifts.tiles;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileTeleporter extends TileEntity {

	private UUID placedBy;
	private boolean triggered;

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("placedByMost") && nbt.hasKey("placedByLeast"))
			this.placedBy = new UUID(nbt.getLong("placedByMost"), nbt.getLong("placedByLeast"));
		this.triggered = nbt.getBoolean("triggered");
		super.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (placedBy != null) {
			nbt.setLong("placedByMost", placedBy.getMostSignificantBits());
			nbt.setLong("placedByLeast", placedBy.getLeastSignificantBits());
		}
		nbt.setBoolean("triggered", this.triggered);
		return super.writeToNBT(nbt);
	}

	public boolean isTriggered() {
		return this.triggered;
	}

	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}

}
