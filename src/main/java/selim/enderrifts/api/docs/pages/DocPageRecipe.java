package selim.enderrifts.api.docs.pages;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.enderrifts.api.docs.DocPage;
import selim.enderrifts.api.docs.IGuiInfo;

@SideOnly(Side.CLIENT)
public class DocPageRecipe extends DocPage {

	@Override
	public void renderScreen(IGuiInfo gui, int mouseX, int mouseY) {
		this.renderRecipe(gui, mouseX, mouseY);
	}

	public void renderRecipe(IGuiInfo gui, int mouseX, int mouseY) {}

}
