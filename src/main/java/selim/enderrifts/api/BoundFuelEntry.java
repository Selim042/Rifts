package selim.enderrifts.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class BoundFuelEntry extends IForgeRegistryEntry.Impl<BoundFuelEntry> {

	private final ItemStack fuel;
	private final int power;

	public BoundFuelEntry(ItemStack fuel, int power) {
		this.fuel = fuel;
		this.power = power;
	}

	public ItemStack getFuel() {
		return this.fuel.copy();
	}

	public int getPower() {
		return this.power;
	}

}
