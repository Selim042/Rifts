package selim.enderrifts.api.docs.pages;

import selim.enderrifts.api.docs.DocPage;
import selim.enderrifts.api.docs.IGuiInfo;

public class DocPageRecipe extends DocPage {

	@Override
	public void renderScreen(IGuiInfo gui, int mouseX, int mouseY) {
		this.renderRecipe(gui, mouseX, mouseY);
	}

	public void renderRecipe(IGuiInfo gui, int mouseX, int mouseY) {}

}
