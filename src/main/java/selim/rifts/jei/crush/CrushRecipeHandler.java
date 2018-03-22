package selim.rifts.jei.crush;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import selim.rifts.crafting.CrushRecipe;
import selim.rifts.jei.JeiPlugin;

public class CrushRecipeHandler implements IRecipeHandler<CrushRecipe> {

	@Override
	public Class<CrushRecipe> getRecipeClass() {
		return CrushRecipe.class;
	}

	// @Override
	// public String getRecipeCategoryUid() {
	// return JeiPlugin.ANVIL_CRAFTING;
	// }

	@Override
	public String getRecipeCategoryUid(CrushRecipe recipe) {
		return JeiPlugin.ANVIL_CRAFTING;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CrushRecipe recipe) {
		return new CrushRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(CrushRecipe recipe) {
		return recipe.getPossibleInputs().size() != 0 && !recipe.getResult().isEmpty();
	}

}
