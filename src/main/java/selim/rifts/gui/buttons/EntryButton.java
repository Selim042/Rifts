package selim.rifts.gui.buttons;

import net.minecraft.client.Minecraft;
import selim.rifts.api.docs.DocEntry;
import selim.rifts.gui.EntryScreen;
import selim.rifts.gui.MainScreen;

public class EntryButton extends ItemLabelButtom {

	private final DocEntry entry;
	private final MainScreen back;

	public EntryButton(int id, DocEntry entry, MainScreen back, int x, int y, int hoverColor) {
		super(id, entry.getIcon(), entry.getLocalizedName(), x, y, hoverColor);
		this.entry = entry;
		this.back = back;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		super.drawButton(mc, mouseX, mouseY, partialTicks);
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		if (this.entry.getPages().size() != 0)
			Minecraft.getMinecraft().displayGuiScreen(new EntryScreen(this.entry, this.back));
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		return super.mousePressed(mc, mouseX, mouseY);
	}

}
