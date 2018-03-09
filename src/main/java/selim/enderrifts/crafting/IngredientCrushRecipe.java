package selim.enderrifts.crafting;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class IngredientCrushRecipe extends CrushRecipe {

	private final Ingredient input;

	public IngredientCrushRecipe(ItemStack output, Ingredient input) {
		super(output);
		this.input = input;
	}

	@Override
	public int getConsumed(ItemStack input) {
		if (input == null || input.isEmpty())
			return 0;
		ItemStack ingStack = arrContains(this.input.getMatchingStacks(), input);
		if (ingStack.isEmpty())
			return 0;
		return input.getCount() - (input.getCount() % ingStack.getCount());
	}

	private ItemStack arrContains(ItemStack[] arr, ItemStack val) {
		for (ItemStack v : arr)
			if (v.isItemEqual(val))
				return v;
		return ItemStack.EMPTY;
	}

	private <T> boolean arrContains(T[] arr, T val) {
		for (T t : arr)
			if (t != null && t.equals(val))
				return true;
		return false;
	}

	@Override
	public int getCrafted(ItemStack input) {
		ItemStack ingStack = arrContains(this.input.getMatchingStacks(), input);
		return this.getResult().getCount() * (input.getCount() / ingStack.getCount());
	}

	@Override
	public boolean isInputValid(ItemStack input) {
		return !arrContains(this.input.getMatchingStacks(), input).isEmpty();
	}

	@Override
	public List<ItemStack> getPossibleInputs() {
		LinkedList<ItemStack> list = new LinkedList<ItemStack>();
		for (ItemStack s : this.input.getMatchingStacks())
			list.add(s);
		return list;
	}

}
