package selim.enderrifts.gui.buttons;

import net.minecraft.client.Minecraft;
import selim.enderrifts.api.docs.DocCategory;
import selim.enderrifts.gui.CategoryScreen;
import selim.enderrifts.gui.MainScreen;

public class CategoryButton extends ItemLabelButtom {

	private final DocCategory cat;
	private final MainScreen screen;

	public CategoryButton(int id, DocCategory cat, MainScreen screen, int x, int y, int hoverColor) {
		super(id, cat.getIcon(), cat.getLocalizedName(), x, y, hoverColor);
		this.cat = cat;
		this.screen = screen;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		super.drawButton(mc, mouseX, mouseY, partialTicks);
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		Minecraft.getMinecraft().displayGuiScreen(new CategoryScreen(cat, screen));
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		return super.mousePressed(mc, mouseX, mouseY);
	}

}
