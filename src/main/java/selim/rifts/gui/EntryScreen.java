package selim.rifts.gui;

import java.io.IOException;
import java.util.List;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.rifts.api.docs.DocEntry;
import selim.rifts.api.docs.DocPage;
import selim.rifts.api.docs.IGuiInfo;
import selim.rifts.gui.buttons.BackButton;
import selim.rifts.gui.buttons.PageButton;
import selim.rifts.gui.buttons.PageButton.EnumPageButton;

@SideOnly(Side.CLIENT)
public class EntryScreen extends MainScreen implements IGuiInfo {

	private final DocEntry entry;
	private final MainScreen back;
	private final DocPage page;

	private int pageNum = 0;

	private PageButton leftButton;
	private PageButton rightButton;
	private BackButton backButton;

	public EntryScreen(DocEntry entry, MainScreen back) {
		this(entry, back, 0);
	}

	public EntryScreen(DocEntry entry, MainScreen back, int page) {
		super(entry.getLocalizedName());
		this.entry = entry;
		this.back = back;
		this.pageNum = page;
		List<DocPage> pages = entry.getPages();
		if (this.pageNum >= pages.size())
			this.pageNum = pages.size() - 1;
		if (this.pageNum < 0)
			this.pageNum = 0;
		this.page = entry.getPages().get(this.pageNum);
	}

	@Override
	public void updateScreen() {
		this.page.updateScreen(this);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		page.renderScreen(this, mouseX, mouseY);
	}

	@Override
	public void initGui() {
		GuiHandler.lastScreen = this;
		this.guiHeight = getGuiHeight(this.height);
		this.guiEdge = getCenter(this.width, BACKGROUND_WIDTH);
		this.leftButton = this.addButton(
				new PageButton(LEFT_TURN_ID, guiEdge + 32, guiHeight + 112, EnumPageButton.LEFT));
		this.rightButton = this.addButton(
				new PageButton(RIGHT_TURN_ID, guiEdge + 224, guiHeight + 112, EnumPageButton.RIGHT));
		this.backButton = this
				.addButton(new BackButton(BACK_ID, guiEdge + 112, guiHeight + 224, this.back));
		this.backButton.enabled = true;
		List<DocPage> pages = entry.getPages();
		if (this.pageNum >= pages.size())
			this.pageNum = pages.size();
		if (pages.size() > this.pageNum + 1)
			this.rightButton.enabled = true;
		else
			this.rightButton.enabled = false;
		if (this.pageNum != 0)
			this.leftButton.enabled = true;
		else
			this.leftButton.enabled = false;
		page.onOpened(this);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		boolean changed = false;
		super.keyTyped(typedChar, keyCode);
		switch (keyCode) {
		case 1: // escape
			changed = true;
			break;
		case 14: // backspace
			changed = true;
			Minecraft.getMinecraft().displayGuiScreen(this.back);
			break;
		case 203: // left arrow
			changed = true;
			Minecraft.getMinecraft()
					.displayGuiScreen(new EntryScreen(this.entry, this.back, pageNum - 1));
			break;
		case 205: // right arrow
			changed = true;
			Minecraft.getMinecraft()
					.displayGuiScreen(new EntryScreen(this.entry, this.back, pageNum + 1));
			break;
		}
		if (changed)
			page.onClosed(this);
		else
			page.onKeyPressed(typedChar, keyCode);
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
			// if (wheelState > 0)
			// this.pageNum++;
			// else if (wheelState < 0)
			// this.pageNum--;
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
						.displayGuiScreen(new EntryScreen(this.entry, this.back, pageNum - 1));
				break;
			case RIGHT:
				Minecraft.getMinecraft()
						.displayGuiScreen(new EntryScreen(this.entry, this.back, pageNum + 1));
				break;
			}
		}
		if (button.id != LEFT_TURN_ID && button.id != RIGHT_TURN_ID && button.id != BACK_ID)
			page.onActionPerformed(this, button);
	}

	@Override
	public int getGuiEdge() {
		return this.guiEdge;
	}

	@Override
	public int getGuiTop() {
		return this.guiHeight;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

}
