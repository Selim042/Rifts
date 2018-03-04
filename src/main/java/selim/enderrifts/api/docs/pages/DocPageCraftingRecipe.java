package selim.enderrifts.api.docs.pages;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import selim.enderrifts.ModInfo;
import selim.enderrifts.api.docs.IGuiInfo;

@SuppressWarnings("deprecation")
public class DocPageCraftingRecipe extends DocPageRecipe {

	private static final ResourceLocation CRAFTING = new ResourceLocation(ModInfo.ID,
			"textures/gui/crafting.png");
	protected static final int GRID_WIDTH = 256;
	protected static final int GRID_HEIGHT = 256;

	private final ResourceLocation recipeLoc;
	private IRecipe recipe;
	private boolean shapeless;
	private boolean oreDict;
	private int timer;

	public DocPageCraftingRecipe(ResourceLocation recipe) {
		this.recipeLoc = recipe;
	}

	@Override
	public void renderScreen(IGuiInfo gui, int mouseX, int mouseY) {
		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		GlStateManager.color(0.5f, 0.5f, 0.5f, 1.0f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(CRAFTING);
		GuiScreen.drawModalRectWithCustomSizedTexture(gui.getGuiEdge() + 80, gui.getGuiTop() + 94, 0, 0,
				112, 54, GRID_WIDTH, GRID_HEIGHT);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		GuiScreen.drawModalRectWithCustomSizedTexture(gui.getGuiEdge() + 100, gui.getGuiTop() + 96, 112,
				16, 129, 13, GRID_WIDTH, GRID_HEIGHT);
		GlStateManager.popMatrix();
		super.renderScreen(gui, mouseX, mouseY);
	}

	@Override
	public void updateScreen(IGuiInfo gui) {
		if (this.oreDict)
			timer++;
	}

	@Override
	public void onOpened(IGuiInfo gui) {
		this.recipe = ForgeRegistries.RECIPES.getValue(this.recipeLoc);
		this.recipe = ForgeRegistries.RECIPES.getValue(new ResourceLocation(ModInfo.ID, "rift_eye"));
		if (recipe instanceof ShapedRecipes || recipe instanceof ShapedOreRecipe)
			this.shapeless = false;
		else if (recipe instanceof ShapelessRecipes || recipe instanceof ShapelessOreRecipe)
			this.shapeless = true;
		if (recipe instanceof ShapedOreRecipe || recipe instanceof ShapelessOreRecipe)
			this.oreDict = true;
		else if (recipe instanceof ShapedRecipes || recipe instanceof ShapelessRecipes)
			this.oreDict = false;
	}

	@Override
	public void renderRecipe(IGuiInfo gui, int mouseX, int mouseY) {
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		if (recipe == null)
			return;
		if (recipe instanceof ShapedRecipes || recipe instanceof ShapedOreRecipe) {
			IShapedRecipe shaped = (IShapedRecipe) recipe;
			for (int x = 0; x < shaped.getRecipeWidth(); x++) {
				for (int y = 0; y < shaped.getRecipeHeight(); y++) {
					Ingredient ing = recipe.getIngredients().get(y * shaped.getRecipeWidth() + x);
					ItemStack[] stacks = ing.getMatchingStacks();
					if (stacks.length > 0) {
						// renderItemAtGridPos(gui, 1 + x, 1 + y,
						// stacks[(ticksElapsed / 40) % stacks.length], true);
						renderItemOnGrid(gui, x + 1, y + 1, mouseX, mouseY,
								stacks[(this.timer / 20) % stacks.length]);
					}
				}
			}
		} else if (recipe instanceof ShapelessRecipes || recipe instanceof ShapelessOreRecipe) {
			for (int x = 0; x < 3 && x < recipe.getIngredients().size(); x++) {
				for (int y = 0; y < 3 && (y * 3) + x < recipe.getIngredients().size(); y++) {
					Ingredient ing = recipe.getIngredients().get((y * 3) + x);
					ItemStack[] stacks = ing.getMatchingStacks();
					if (stacks.length > 0)
						renderItemOnGrid(gui, x + 1, y + 1, mouseX, mouseY,
								stacks[(this.timer / 20) % stacks.length]);
				}
			}
		}
		renderItemOnGrid(gui, 6, 2, mouseX, mouseY, recipe.getRecipeOutput());
		renderTooltip(gui, mouseX, mouseY);
	}

	private ItemStack tooltipStack;

	private void renderItemOnGrid(IGuiInfo gui, int gridX, int gridY, int mouseX, int mouseY,
			ItemStack stack) {
		RenderItem render = Minecraft.getMinecraft().getRenderItem();
		GlStateManager.pushMatrix();
		GlStateManager.enableLighting();
		RenderHelper.disableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();
		float height = render.zLevel;
		int stackX = gui.getGuiEdge() + 63 + (gridX * 18);
		int stackY = gui.getGuiTop() + 77 + (gridY * 18);
		render.renderItemIntoGUI(stack, stackX, stackY);
		render.renderItemOverlays(Minecraft.getMinecraft().fontRenderer, stack, stackX, stackY);
		if (mouseX >= stackX && mouseX < stackX + 16 && mouseY >= stackY && mouseY < stackY + 16) {
			this.tooltipStack = stack;
		}
		RenderHelper.enableStandardItemLighting();
		GlStateManager.popMatrix();
	}

	protected void renderTooltip(IGuiInfo gui, int x, int y) {
		if (tooltipStack == null || tooltipStack == ItemStack.EMPTY)
			return;
		Minecraft mc = Minecraft.getMinecraft();
		List<String> list = tooltipStack.getTooltip(mc.player, mc.gameSettings.advancedItemTooltips
				? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
		for (int i = 0; i < list.size(); ++i) {
			if (i == 0) {
				list.set(i, tooltipStack.getRarity().rarityColor + (String) list.get(i));
			} else {
				list.set(i, TextFormatting.GRAY + (String) list.get(i));
			}
		}
		FontRenderer font = tooltipStack.getItem().getFontRenderer(tooltipStack);
		GuiUtils.preItemToolTip(tooltipStack);
		GuiUtils.drawHoveringText(list, x, y, gui.getWidth(), gui.getHeight(), -1,
				(font == null ? mc.fontRenderer : font));
		GuiUtils.postItemToolTip();
		tooltipStack = ItemStack.EMPTY;
	}

}
