package selim.enderrifts.jei.crush;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import selim.enderrifts.crafting.CrushRecipe;

public class CrushRecipeWrapper implements IRecipeWrapper {

	private final CrushRecipe recipe;

	public CrushRecipeWrapper(CrushRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, this.getInputs());
		ingredients.setOutputs(ItemStack.class, this.getOutputs());
	}

	public List<ItemStack> getInputs() {
		List<ItemStack> inputs = new ArrayList<ItemStack>();
		inputs.addAll(this.recipe.getPossibleInputs());
		return inputs;
	}

	public List<ItemStack> getOutputs() {
		return Collections.singletonList(recipe.getResult());
	}

	// @Override
	// public List<FluidStack> getFluidInputs() {
	// return null;
	// }

	// @Override
	// public List<FluidStack> getFluidOutputs() {
	// return null;
	// }

	// @Override
	// public void drawInfo(Minecraft minecraft, int recipeWidth, int
	// recipeHeight, int mouseX,
	// int mouseY) {
	// minecraft.fontRendererObj.drawString("Enchantment Cost: " +
	// recipe.getXp(),
	// 127 - minecraft.fontRendererObj
	// .getStringWidth("Enchantment Cost: " + recipe.getXp()),
	// 23, 0xFF80ff20, true);
	// }

	// @Override
	// public void drawAnimations(Minecraft minecraft, int recipeWidth, int
	// recipeHeight) {
	//
	// }

	// @Override
	// public List<String> getTooltipStrings(int mouseX, int mouseY) {
	// return null;
	// }

	// @Override
	// public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY,
	// int mouseButton) {
	// return false;
	// }

}
