package selim.enderrifts.api.docs.pages;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.translation.I18n;
import selim.enderrifts.api.docs.DocPage;
import selim.enderrifts.api.docs.IGuiInfo;

@SuppressWarnings("deprecation")
public class DocPageText extends DocPage {

	private final String[] displayText;

	public DocPageText(String... displayText) {
		this.displayText = displayText;
		for (int i = 0; i < this.displayText.length; i++)
			this.displayText[i] = "rift_page." + this.displayText[i] + ".name";
	}

	@Override
	public void renderScreen(IGuiInfo gui, int mouseX, int mouseY) {
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		int linesPrinted = 0;
		for (int i = 0; i < displayText.length; i++) {
			List<String> lines = font.listFormattedStringToWidth(I18n.translateToLocal(displayText[i]),
					136);
			for (int l = 0; l < lines.size(); l++) {
				font.drawString(lines.get(l), gui.getGuiEdge() + 68,
						gui.getGuiTop() + 68 + (linesPrinted * 10) + (i * 6), 0xFFFFFF);
				linesPrinted++;
			}
		}
	}

}
