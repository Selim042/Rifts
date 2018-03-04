package selim.enderrifts.blocks;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileTeleporter extends TileEntity {

	private UUID placedBy;

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("placedByMost") && nbt.hasKey("placedByLeast"))
			this.placedBy = new UUID(nbt.getLong("placedByMost"), nbt.getLong("placedByLeast"));
		super.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (placedBy != null) {
			nbt.setLong("placedByMost", placedBy.getMostSignificantBits());
			nbt.setLong("placedByLeast", placedBy.getLeastSignificantBits());
		}
		return super.writeToNBT(nbt);
	}

}
