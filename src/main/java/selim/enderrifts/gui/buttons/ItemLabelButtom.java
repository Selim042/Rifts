package selim.enderrifts.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public class ItemLabelButtom extends GuiButton {

	private final Minecraft mc;
	private final FontRenderer fontRenderer;
	private final RenderItem itemRender;

	private final ItemStack stack;
	private final String label;
	private final int x;
	private final int y;
	private final int color;

	private int mouseX = 0;
	private int mouseY = 0;

	public ItemLabelButtom(int id, ItemStack stack, String label, int x, int y, int hoverColor) {
		super(id, x, y, null);
		mc = Minecraft.getMinecraft();
		this.fontRenderer = mc.fontRenderer;
		this.itemRender = mc.getRenderItem();
		this.stack = stack;
		this.label = label;
		this.x = x;
		this.y = y;
		this.color = hoverColor;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		GlStateManager.pushMatrix();
		GlStateManager.enableLighting();
		RenderHelper.disableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();
		if (stack != null)
			this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		if (isMouseOver())
			this.fontRenderer.drawString(this.label, x + 18, y + 4, this.color, false);
		else
			this.fontRenderer.drawString(this.label, x + 18, y + 4, 0xFFFFFF, false);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.popMatrix();
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		super.mouseReleased(mouseX, mouseY);
	}

	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		return super.mousePressed(mc, mouseX, mouseY);
	}

	@Override
	public boolean isMouseOver() {
		return mouseY >= y && mouseY < y + 16 && mouseX >= x
				&& mouseX < (x + 18 + this.fontRenderer.getStringWidth(this.label));
	}

}
