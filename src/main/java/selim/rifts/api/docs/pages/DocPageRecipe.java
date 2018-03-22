package selim.rifts.api.docs.pages;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.rifts.api.docs.DocPage;
import selim.rifts.api.docs.IGuiInfo;

//@SideOnly(Side.CLIENT)
public class DocPageRecipe extends DocPage {

	@SideOnly(Side.CLIENT)
	@Override
	public void renderScreen(IGuiInfo gui, int mouseX, int mouseY) {
		this.renderRecipe(gui, mouseX, mouseY);
	}

	@SideOnly(Side.CLIENT)
	public void renderRecipe(IGuiInfo gui, int mouseX, int mouseY) {}

}
