package selim.rifts.api.docs;

import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//@SideOnly(Side.CLIENT)
public abstract class DocPage {

	/**
	 * Does the rendering for this page.
	 */
	@SideOnly(Side.CLIENT)
	public abstract void renderScreen(IGuiInfo gui, int mouseX, int mouseY);

	/**
	 * Called per update tick.
	 */
	@SideOnly(Side.CLIENT)
	public void updateScreen(IGuiInfo gui) {}

	/**
	 * Called when this page is opened, be it via initGui() or when the player
	 * changes page. You can add buttons and whatever you'd do on initGui()
	 * here.
	 */
	@SideOnly(Side.CLIENT)
	public void onOpened(IGuiInfo gui) {}

	/**
	 * Called when this page is opened, be it via closing the gui or when the
	 * player changes page. Make sure to dispose of anything you don't use any
	 * more, such as buttons in the gui's buttonList.
	 */
	@SideOnly(Side.CLIENT)
	public void onClosed(IGuiInfo gui) {}

	/**
	 * Called when a button is pressed, equivalent to GuiScreen.actionPerformed.
	 */
	@SideOnly(Side.CLIENT)
	public void onActionPerformed(IGuiInfo gui, GuiButton button) {}

	/**
	 * Called when a key is pressed.
	 */
	@SideOnly(Side.CLIENT)
	public void onKeyPressed(char c, int key) {}

	// /**
	// * Called when {@link LexiconEntry#setLexiconPages(LexiconPage...)} is
	// * called.
	// */
	// public void onPageAdded(LexiconEntry entry, int index) {}

}
