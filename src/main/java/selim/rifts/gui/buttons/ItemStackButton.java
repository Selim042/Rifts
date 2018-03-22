package selim.rifts.gui.buttons;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class ItemStackButton extends GuiButton {

	private final Minecraft mc;
	private final FontRenderer fontRendererObj;
	private final RenderItem itemRender;

	private final ItemStack stack;
	private final List<String> list;
	private final int x;
	private final int y;
	private final int screenWidth;
	private final int screenHeight;

	public ItemStackButton(int id, ItemStack stack, List<String> list, int x, int y, int screenWidth,
			int screenHeight) {
		super(id, x, y, null);
		mc = Minecraft.getMinecraft();
		fontRendererObj = mc.fontRenderer;
		itemRender = mc.getRenderItem();
		this.stack = stack;
		if (list == null) {
			this.list = stack.getTooltip(mc.player, this.mc.gameSettings.advancedItemTooltips
					? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
		} else {
			this.list = list;
		}
		this.x = x;
		this.y = y;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		itemRender.renderItemAndEffectIntoGUI(stack, x, y);
		if (mouseX > x && mouseX < x + 16 && mouseY > y && mouseY < y + 16) {
			renderCustomToolTip(list, stack, mouseX, mouseY);
		}
		GL11.glPopMatrix();
	}

	protected void renderCustomToolTip(List<String> list, ItemStack stack, int x, int y) {
		if (list == null) {
			list = stack.getTooltip(this.mc.player, this.mc.gameSettings.advancedItemTooltips
					? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
		}
		for (int i = 0; i < list.size(); ++i) {
			if (i == 0) {
				list.set(i, stack.getRarity().rarityColor + (String) list.get(i));
			} else {
				list.set(i, TextFormatting.GRAY + (String) list.get(i));
			}
		}
		FontRenderer font = stack.getItem().getFontRenderer(stack);
		net.minecraftforge.fml.client.config.GuiUtils.preItemToolTip(stack);
		net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(list, x, y, screenWidth,
				screenHeight, -1, (font == null ? fontRendererObj : font));
		net.minecraftforge.fml.client.config.GuiUtils.postItemToolTip();
	}

	// private void renderCustomItemStack(ItemStack stack, List<String> list,
	// int x, int y, int mouseX, int mouseY,
	// float partialTicks) {
	// itemRender.renderItemAndEffectIntoGUI(stack, x, y);
	// if (mouseX > x && mouseX < x + 16 && mouseY > y && mouseY < y + 16) {
	// this.renderCustomToolTip(list, stack, mouseX, mouseY);
	// }
	// }
	//
	// private void renderItemStack(ItemStack stack, int x, int y, int mouseX,
	// int mouseY, float partialTicks) {
	// itemRender.renderItemAndEffectIntoGUI(stack, x, y);
	// if (mouseX > x && mouseX < x + 16 && mouseY > y && mouseY < y + 16) {
	// this.renderCustomToolTip(null, stack, mouseX, mouseY);
	// }
	// }
}
