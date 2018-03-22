package selim.rifts.crafting;

import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

public class SpecificCrushRecipe extends CrushRecipe {

	private final ItemStack input;

	public SpecificCrushRecipe(ItemStack output, ItemStack input) {
		super(output);
		this.input = input;
	}

	@Override
	public int getConsumed(ItemStack input) {
		if (input == null || input.isEmpty())
			return 0;
		return input.getCount() - (input.getCount() % this.input.getCount());
	}

	@Override
	public int getCrafted(ItemStack input) {
		return this.getResult().getCount() * (input.getCount() / this.input.getCount());
	}

	@Override
	public boolean isInputValid(ItemStack input) {
		return ItemStack.areItemsEqual(this.input, input);
	}

	@Override
	public List<ItemStack> getPossibleInputs() {
		return Collections.singletonList(this.input);
	}

}
