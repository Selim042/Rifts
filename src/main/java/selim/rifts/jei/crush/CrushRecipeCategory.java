package selim.rifts.jei.crush;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import selim.rifts.ModInfo;
import selim.rifts.jei.JeiPlugin;

@SuppressWarnings("deprecation")
public class CrushRecipeCategory implements IRecipeCategory<CrushRecipeWrapper> {

	public static final int width = 128;
	public static final int height = 45;

	private final IDrawable background;
	private final String localizedName;

	public CrushRecipeCategory(IGuiHelper guiHelper) {
		ResourceLocation location = new ResourceLocation(ModInfo.ID,
				"textures/gui/jeiAnvilCrafting.png");
		background = guiHelper.createDrawable(location, 0, 0, width, height);
		localizedName = I18n.translateToLocal("gui.enderrifts:crush_recipe.name");
	}

	@Override
	public String getUid() {
		return JeiPlugin.ANVIL_CRAFTING;
	}

	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return null;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {

	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CrushRecipeWrapper recipeWrapper,
			IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(0, true, 2, 2);
		guiItemStacks.init(1, true, 51, 2);
		// guiItemStacks.init(2, false, 109, 2);

		List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
		List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

		if (inputs.get(0) != null) {
			guiItemStacks.set(0, inputs.get(0));
		}
		if (outputs.get(0) != null) {
			guiItemStacks.set(1, outputs.get(0));
		}
	}

	@Override
	public String getModName() {
		return ModInfo.NAME;
	}

}
