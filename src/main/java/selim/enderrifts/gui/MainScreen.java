package selim.enderrifts.gui;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;
import selim.enderrifts.api.docs.DocCategory;
import selim.enderrifts.gui.buttons.BackButton;
import selim.enderrifts.gui.buttons.CategoryButton;
import selim.enderrifts.gui.buttons.PageButton;
import selim.enderrifts.gui.buttons.PageButton.EnumPageButton;

@SideOnly(Side.CLIENT)
public class MainScreen extends GuiScreen {

	private static final IForgeRegistry<DocCategory> CATEGORIES = GameRegistry
			.findRegistry(DocCategory.class);
	private static final ResourceLocation BACKGROUND = new ResourceLocation(ModInfo.ID,
			"textures/items/rift_eye.png");
	protected static final int docPerPage = 9;
	protected static final int LEFT_TURN_ID = -12;
	protected static final int RIGHT_TURN_ID = -13;
	protected static final int BACK_ID = -11;
	protected static final int BACKGROUND_WIDTH = 256;
	protected static final int BACKGROUND_HEIGHT = 256;

	private final String title;

	private int pageNum;

	private List<DocCategory> allCats;
	private List<DocCategory> displayCats;
	protected int guiHeight;
	protected int guiEdge;

	public MainScreen() {
		this(0, ModInfo.NAME);
	}

	public MainScreen(int page) {
		this(page, ModInfo.NAME);
	}

	public MainScreen(String title) {
		this(0, title);
	}

	public MainScreen(int page, String title) {
		this.title = title;
		this.pageNum = page;
		GuiHandler.lastScreen = this;
		reloadCategories();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.guiHeight = getGuiHeight(this.height);
		this.guiEdge = getCenter(this.width, BACKGROUND_WIDTH);
		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		GlStateManager.color(0.5f, 0.5f, 0.5f, 1.0f);
		this.mc.getTextureManager().bindTexture(BACKGROUND);
		this.drawTexturedModalRect(guiEdge, guiHeight, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		GlStateManager.popMatrix();
		this.fontRenderer.drawString(this.title,
				getCenter(this.width, this.fontRenderer.getStringWidth(this.title)) + 8, guiHeight + 52,
				0xFFFFFF, true);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void onResize(Minecraft mcIn, int w, int h) {
		super.onResize(mcIn, w, h);
		this.guiHeight = getGuiHeight(this.height);
		this.guiEdge = getCenter(this.width, BACKGROUND_WIDTH);
	}

	@Override
	public void initGui() {
		GuiHandler.lastScreen = this;
		this.guiHeight = getGuiHeight(this.height);
		this.guiEdge = getCenter(this.width, BACKGROUND_WIDTH);
		this.reloadCategories();
		PageButton left = this.addButton(
				new PageButton(LEFT_TURN_ID, guiEdge + 32, guiHeight + 112, EnumPageButton.LEFT));
		PageButton right = this.addButton(
				new PageButton(RIGHT_TURN_ID, guiEdge + 224, guiHeight + 112, EnumPageButton.RIGHT));
		BackButton back = this.addButton(new BackButton(BACK_ID, guiEdge + 112, guiHeight + 224, this));
		back.enabled = false;
		if (displayCats.size() / docPerPage > this.pageNum + 1)
			right.enabled = true;
		else
			right.enabled = false;
		if (this.pageNum != 0)
			left.enabled = true;
		else
			left.enabled = false;
		int heightIndex = 0;
		for (int i = pageNum * docPerPage; i < displayCats.size()
				&& i < (pageNum * docPerPage) + docPerPage; i++) {
			DocCategory cat = displayCats.get(i);
			this.addButton(new CategoryButton(this.buttonList.size() + 1, cat, this, guiEdge + 64,
					guiHeight + 64 + (heightIndex * 16), 0x4242f4));
			heightIndex++;
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		switch (keyCode) {
		case 14: // backspace -- nothing to go back to
			break;
		case 203: // left arrow
			Minecraft.getMinecraft().displayGuiScreen(new MainScreen(pageNum - 1));
			break;
		case 205: // right arrow
			Minecraft.getMinecraft().displayGuiScreen(new MainScreen(pageNum + 1));
			break;
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled && button.isMouseOver() && button instanceof PageButton) {
			switch (((PageButton) button).getButtonType()) {
			case LEFT:
				Minecraft.getMinecraft().displayGuiScreen(new MainScreen(pageNum - 1));
				break;
			case RIGHT:
				Minecraft.getMinecraft().displayGuiScreen(new MainScreen(pageNum + 1));
				break;
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	protected static int getCenter(int windowWidth, int textureWidth) {
		return (windowWidth - textureWidth) / 2;
	}

	protected static int getGuiHeight(int windowHeight) {
		if (windowHeight <= BACKGROUND_HEIGHT)
			return -16;
		return ((windowHeight - BACKGROUND_HEIGHT) / 3) - 16;
	}

	private void reloadCategories() {
		this.allCats = new LinkedList<DocCategory>(CATEGORIES.getValues());
		this.displayCats = new LinkedList<DocCategory>();
		for (DocCategory cat : allCats)
			if (cat.getRequriedDim() == null
					|| EnderRifts.proxy.hasVisitedFromPersistance(cat.getRequriedDim()))
				displayCats.add(cat);
		this.displayCats.sort(null);
		if (this.pageNum >= displayCats.size() / docPerPage)
			this.pageNum = (displayCats.size() / docPerPage) - 1;
		if (this.pageNum < 0)
			this.pageNum = 0;
	}

}
