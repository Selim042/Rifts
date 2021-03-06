package selim.rifts.jei;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.ItemStack;
import selim.rifts.RiftRegistry;
import selim.rifts.jei.crush.CrushRecipeCategory;
import selim.rifts.jei.crush.CrushRecipeHandler;

//@JEIPlugin
public class JeiPlugin extends BlankModPlugin {

	public static final String ANVIL_CRAFTING = "enderrifts:crush";

	@Override
	public void register(IModRegistry registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();

		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(new CrushRecipeCategory(guiHelper));
		registry.addRecipeHandlers(new CrushRecipeHandler());

		registry.addRecipeClickArea(GuiRepair.class, 102, 48, 22, 15, ANVIL_CRAFTING);

		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();

		recipeTransferRegistry.addRecipeTransferHandler(ContainerRepair.class, ANVIL_CRAFTING, 0, 2, 3,
				36);

		registry.addRecipeCategoryCraftingItem(new ItemStack(Blocks.ANVIL), ANVIL_CRAFTING);

		registry.addRecipes(RiftRegistry.Registries.CRUSH_RECIPES.getValuesCollection());
	}

}
