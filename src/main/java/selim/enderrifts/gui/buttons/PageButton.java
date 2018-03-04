package selim.enderrifts.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import selim.enderrifts.ModInfo;

public class PageButton extends GuiButton {

	private static final ResourceLocation BUTTONS = new ResourceLocation(ModInfo.ID,
			"textures/gui/page_buttons.png");

	private final EnumPageButton button;

	private int mouseX = 0;
	private int mouseY = 0;

	public PageButton(int id, int x, int y, EnumPageButton button) {
		super(id, x, y, null);
		this.button = button;
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
		if (!enabled)
			GlStateManager.color(0.5f, 0.5f, 0.5f, 1.0f);
		else
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		PageButton.drawModalRectWithCustomSizedTexture(this.x, this.y, button.u, button.v,
				button.textureWidth, button.textureHeight, 48, 48);
		GlStateManager.popMatrix();
	}

	public EnumPageButton getButtonType() {
		return this.button;
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		return this.enabled && this.visible && mouseX >= this.x && mouseY >= this.y
				&& mouseX < this.x + 16 && mouseY < this.y + 48;
	}

	@Override
	public boolean isMouseOver() {
		return this.enabled && this.visible && this.mouseX >= this.x && this.mouseX < this.x + 16
				&& this.mouseY >= this.y && this.mouseY < this.mouseY + 48;
	}

	public static enum EnumPageButton {
		LEFT(0, 0, 16, 48),
		RIGHT(16, 0, 32, 48);

		protected final int u;
		protected final int v;
		protected final int textureWidth;
		protected final int textureHeight;

		EnumPageButton(int u, int v, int textureWidth, int textureHeight) {
			this.u = u;
			this.v = v;
			this.textureWidth = textureWidth;
			this.textureHeight = textureHeight;
		}
	}

}
