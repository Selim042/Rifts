package selim.enderrifts.gui;

import java.io.IOException;
import java.util.List;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.enderrifts.api.docs.DocCategory;
import selim.enderrifts.api.docs.DocEntry;
import selim.enderrifts.gui.buttons.BackButton;
import selim.enderrifts.gui.buttons.EntryButton;
import selim.enderrifts.gui.buttons.PageButton;
import selim.enderrifts.gui.buttons.PageButton.EnumPageButton;

@SideOnly(Side.CLIENT)
public class CategoryScreen extends MainScreen {

	private final DocCategory cat;
	private final MainScreen back;

	private List<DocEntry> entries;
	private int pageNum = 0;

	public CategoryScreen(DocCategory cat, MainScreen back) {
		this(cat, back, 0);
	}

	public CategoryScreen(DocCategory cat, MainScreen back, int page) {
		super(cat.getLocalizedName());
		this.cat = cat;
		this.back = back;
		this.pageNum = page;
		this.entries = cat.getEntries();
		if (this.pageNum >= entries.size() / docPerPage)
			this.pageNum = (entries.size() / docPerPage) - 1;
		if (this.pageNum < 0)
			this.pageNum = 0;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		// String name = cat.getLocalizedName();
		// this.fontRenderer.drawString(name,
		// getCenter(this.width, this.fontRenderer.getStringWidth(name)) + 8,
		// this.guiHeight + 68,
		// 0xFFFFFF, true);
	}

	@Override
	public void initGui() {
		GuiHandler.lastScreen = this;
		this.guiHeight = getGuiHeight(this.height);
		this.guiEdge = getCenter(this.width, BACKGROUND_WIDTH);
		PageButton left = this.addButton(
				new PageButton(LEFT_TURN_ID, guiEdge + 32, guiHeight + 112, EnumPageButton.LEFT));
		PageButton right = this.addButton(
				new PageButton(RIGHT_TURN_ID, guiEdge + 224, guiHeight + 112, EnumPageButton.RIGHT));
		BackButton backButton = this
				.addButton(new BackButton(BACK_ID, guiEdge + 112, guiHeight + 224, this.back));
		backButton.enabled = true;
		if (entries.size() / docPerPage > this.pageNum + 1)
			right.enabled = true;
		else
			right.enabled = false;
		if (this.pageNum != 0)
			left.enabled = true;
		else
			left.enabled = false;
		int heightIndex = 0;
		for (int i = pageNum * docPerPage; i < entries.size()
				&& i < (pageNum * docPerPage) + docPerPage; i++) {
			DocEntry entry = entries.get(i);
			this.addButton(new EntryButton(this.buttonList.size() + 1, entry, this, this.guiEdge + 64,
					this.guiHeight + 64 + (heightIndex * 16), 0x4242f4));
			heightIndex++;
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		switch (keyCode) {
		case 14: // backspace
			Minecraft.getMinecraft().displayGuiScreen(this.back);
			break;
		case 203: // left arrow
			Minecraft.getMinecraft()
					.displayGuiScreen(new CategoryScreen(this.cat, this.back, pageNum - 1));
			break;
		case 205: // right arrow
			Minecraft.getMinecraft()
					.displayGuiScreen(new CategoryScreen(this.cat, this.back, pageNum + 1));
			break;
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		if (mouseButton == 1 && this.back != null) // right click
			Minecraft.getMinecraft().displayGuiScreen(this.back);
	}

	private static final int scrollMax = 93;
	private int scrollPos = 0; // up to 93

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		int wheelState = Mouse.getEventDWheel();
		if (wheelState != 0) {
//			if (wheelState > 0)
//				this.pageNum++;
//			else if (wheelState < 0)
//				this.pageNum--;
			scrollPos += wheelState > 0 ? -8 : 8;
			handleScrollPos();
		}
	}

	private void handleScrollPos() {
		if (scrollPos < 0)
			scrollPos = 0;
		else if (scrollPos > scrollMax)
			scrollPos = scrollMax;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled && button.isMouseOver() && button instanceof PageButton) {
			switch (((PageButton) button).getButtonType()) {
			case LEFT:
				Minecraft.getMinecraft()
						.displayGuiScreen(new CategoryScreen(this.cat, this.back, pageNum - 1));
				break;
			case RIGHT:
				Minecraft.getMinecraft()
						.displayGuiScreen(new CategoryScreen(this.cat, this.back, pageNum + 1));
				break;
			}
		}
	}

}
