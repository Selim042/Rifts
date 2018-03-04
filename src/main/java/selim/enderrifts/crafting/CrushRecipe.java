package selim.enderrifts.crafting;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class CrushRecipe extends IForgeRegistryEntry.Impl<CrushRecipe> {

	private final ItemStack output;

	public CrushRecipe(ItemStack output) {
		this.output = output;
	}

	public abstract boolean isInputValid(ItemStack input);

	public abstract int getConsumed(ItemStack input);

	public abstract int getCrafted(ItemStack input);

	public abstract List<ItemStack> getPossibleInputs();

	public ItemStack getResult() {
		return this.output;
	}

}
