package selim.enderrifts.crafting;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreCrushRecipe extends CrushRecipe {

	private final String ore;
	private final int used;

	public OreCrushRecipe(ItemStack output, String ore) {
		this(output, ore, 1);
	}

	public OreCrushRecipe(ItemStack output, String ore, int used) {
		super(output);
		this.ore = ore;
		this.used = used;
	}

	@Override
	public int getConsumed(ItemStack stack) {
		return stack.getCount() - (stack.getCount() % this.used);
	}

	@Override
	public int getCrafted(ItemStack input) {
		return getConsumed(input) * this.getResult().getCount();
	}

	@Override
	public boolean isInputValid(ItemStack input) {
		return arrContains(OreDictionary.getOreIDs(input), OreDictionary.getOreID(this.ore));
	}

	@Override
	public List<ItemStack> getPossibleInputs() {
		return new LinkedList<ItemStack>(OreDictionary.getOres(this.ore));
	}

	private boolean arrContains(int[] arr, int val) {
		for (int i : arr)
			if (i == val)
				return true;
		return false;
	}

}
