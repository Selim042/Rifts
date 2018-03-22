package selim.rifts.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import selim.rifts.ModInfo;
import selim.rifts.gui.MainScreen;

public class BackButton extends GuiButton {

	private static final ResourceLocation BUTTONS = new ResourceLocation(ModInfo.ID,
			"textures/gui/buttons.png");

	private final MainScreen back;

	private int mouseX = 0;
	private int mouseY = 0;

	public BackButton(int id, int x, int y, MainScreen back) {
		super(id, x, y, null);
		this.back = back;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		mc.getTextureManager().bindTexture(BUTTONS);
		if (!this.enabled)
			GlStateManager.color(0.5f, 0.5f, 0.5f, 1.0f);
		else
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		PageButton.drawModalRectWithCustomSizedTexture(this.x, this.y, 0, 0, 48, 16, 48, 48);
		// button.drawTexturedModalRect(button.x, button.y-16, this.x,
		// this.y, this.width*16, this.height*16);
		GlStateManager.popMatrix();
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		if (this.enabled && this.isMouseOver()) {
			Minecraft.getMinecraft().displayGuiScreen(back);
		}
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		return this.enabled && this.visible && mouseX >= this.x && mouseY >= this.y
				&& mouseX < this.x + 48 && mouseY < this.y + 16;
	}

	@Override
	public boolean isMouseOver() {
		return this.mouseX >= this.x && this.mouseX < this.x + 48 && this.mouseY >= this.y
				&& this.mouseY < this.mouseY + 16;
	}

}
