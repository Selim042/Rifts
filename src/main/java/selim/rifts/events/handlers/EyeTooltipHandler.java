package selim.rifts.events.handlers;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.rifts.ModInfo;
import selim.rifts.RiftRegistry;
import selim.rifts.api.docs.DocEntry;
import selim.rifts.api.docs.IDocEntryLink;
import selim.rifts.gui.EntryScreen;
import selim.rifts.gui.GuiHandler;
import selim.rifts.items.ItemRiftEye;

@SideOnly(Side.CLIENT)
@SuppressWarnings("deprecation")
public class EyeTooltipHandler {

	private static final int DEFAULT_TIMER = 160;
	private static final String holdText = I18n.translateToLocal("misc." + ModInfo.ID + ":hold_ctrl");
	private int timer = DEFAULT_TIMER;
	private ItemStack lastHovered;
	private int backgroundColor;
	private int borderColorStart;
	private int borderColorEnd;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void tooltipColors(RenderTooltipEvent.Color event) {
		backgroundColor = event.getBackground();
		borderColorStart = event.getBorderStart();
		borderColorEnd = event.getBorderEnd();
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onTooltip(RenderTooltipEvent.PostText event) {
		if (!hasEye(Minecraft.getMinecraft().player))
			return;
		ItemStack stack = event.getStack();
		IDocEntryLink link = isEyeEntry(stack);
		if (link == null) {
			timer = DEFAULT_TIMER;
			return;
		}
		if (!GuiScreen.isCtrlKeyDown())
			timer = DEFAULT_TIMER;
		FontRenderer font = event.getFontRenderer();
		int x = event.getX();
		int y = event.getY() - 12;
		// int holdWidth = font.getStringWidth(holdText);
		String toDraw = holdText;
		if (link != null) {
			// font.drawString(holdText, x, y, 0xFFFFFF);
			if (!GuiScreen.isCtrlKeyDown())
				toDraw = holdText + "...";
			else if (lastHovered == null || !lastHovered.equals(link)) {
				DocEntry entry = RiftRegistry.Registries.DOC_ENTRIES.getValue(link.getLinkedEntry());
				if (entry == null || entry.getPages().size() == 0)
					return;
				this.lastHovered = stack;
				toDraw = holdText + ": " + Integer.toString(timer / 40);
				// font.drawString(":", x + holdWidth, y, 0xFFFFFF);
				// font.drawString(Integer.toString(timer / 20), x + holdWidth +
				// font.getStringWidth(": "),
				// y, 0xFFFFFF);
				if (timer < 0) {
					Minecraft.getMinecraft()
							.displayGuiScreen(new EntryScreen(entry, GuiHandler.getOpenScreen()));
					timer = DEFAULT_TIMER;
				} else
					timer--;
			} else {
				toDraw = holdText + "...";
				// font.drawString("...", x + holdWidth, y, 0xFFFFFF);
			}
			renderText(toDraw, stack, font, x, y, event.getWidth(), event.getHeight());
		}
	}

	private IDocEntryLink isEyeEntry(ItemStack stack) {
		Item item = stack.getItem();
		IDocEntryLink link = null;
		if (item instanceof IDocEntryLink)
			link = (IDocEntryLink) item;
		if (item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof IDocEntryLink)
			link = (IDocEntryLink) ((ItemBlock) item).getBlock();
		if (link != null && link.getLinkedEntry() != null)
			return link;
		return null;
	}

	private boolean hasEye(EntityPlayer player) {
		if (player == null || player.inventoryContainer == null)
			return false;
		for (ItemStack stack : player.inventoryContainer.inventoryItemStacks)
			if (!stack.isEmpty() && stack.getItem() instanceof ItemRiftEye)
				return true;
		return false;
	}

	@SideOnly(Side.CLIENT)
	protected void renderText(String line, ItemStack stack, FontRenderer font, int x, int y, int width,
			int height) {
		// font.drawString(line, x, y, 0xFFFFFF);
		drawHoveringText(Collections.singletonList(line), x + 100, y, width, height, -1, font);
		// TODO: Figure out why this isn't working
		// GuiUtils.preItemToolTip(stack);
		// GuiUtils.drawHoveringText(Collections.singletonList(line), x, y,
		// width, height, -1, font);
		// GuiUtils.postItemToolTip();
	}

	// Start copy from GuiUtils, removing events & stack references
	protected void drawHoveringText(List<String> textLines, int mouseX, int mouseY, int screenWidth,
			int screenHeight, int maxTextWidth, FontRenderer font) {
		if (!textLines.isEmpty()) {
			// RenderTooltipEvent.Pre event = new RenderTooltipEvent.Pre(stack,
			// textLines, mouseX, mouseY,
			// screenWidth, screenHeight, maxTextWidth, font);
			// if (MinecraftForge.EVENT_BUS.post(event)) {
			// return;
			// }
			// mouseX = event.getX();
			// mouseY = event.getY();
			// screenWidth = event.getScreenWidth();
			// screenHeight = event.getScreenHeight();
			// maxTextWidth = event.getMaxWidth();
			// font = event.getFontRenderer();

			GlStateManager.disableRescaleNormal();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			int tooltipTextWidth = 0;

			for (String textLine : textLines) {
				int textLineWidth = font.getStringWidth(textLine);

				if (textLineWidth > tooltipTextWidth) {
					tooltipTextWidth = textLineWidth;
				}
			}

			// boolean needsWrap = false;

			int titleLinesCount = 1;
			int tooltipX = mouseX + 12;
			// Commented out below as text doesn't need wrapping & interfered
			// with positioning
			// if (tooltipX + tooltipTextWidth + 4 > screenWidth) {
			// tooltipX = mouseX - 16 - tooltipTextWidth;
			// if (tooltipX < 4) // if the tooltip doesn't fit on the screen
			// {
			// if (mouseX > screenWidth / 2) {
			// tooltipTextWidth = mouseX - 12 - 8;
			// } else {
			// tooltipTextWidth = screenWidth - 16 - mouseX;
			// }
			// needsWrap = true;
			// }
			// }
			//
			// if (maxTextWidth > 0 && tooltipTextWidth > maxTextWidth) {
			// tooltipTextWidth = maxTextWidth;
			// needsWrap = true;
			// }
			//
			// if (needsWrap) {
			// int wrappedTooltipWidth = 0;
			// List<String> wrappedTextLines = new ArrayList<String>();
			// for (int i = 0; i < textLines.size(); i++) {
			// String textLine = textLines.get(i);
			// List<String> wrappedLine =
			// font.listFormattedStringToWidth(textLine,
			// tooltipTextWidth);
			// if (i == 0) {
			// titleLinesCount = wrappedLine.size();
			// }
			//
			// for (String line : wrappedLine) {
			// int lineWidth = font.getStringWidth(line);
			// if (lineWidth > wrappedTooltipWidth) {
			// wrappedTooltipWidth = lineWidth;
			// }
			// wrappedTextLines.add(line);
			// }
			// }
			// tooltipTextWidth = wrappedTooltipWidth;
			// textLines = wrappedTextLines;
			//
			// if (mouseX > screenWidth / 2) {
			// tooltipX = mouseX - 16 - tooltipTextWidth;
			// } else {
			// tooltipX = mouseX + 12;
			// }
			// }

			int tooltipY = mouseY - 12;
			int tooltipHeight = 8;

			if (textLines.size() > 1) {
				tooltipHeight += (textLines.size() - 1) * 10;
				if (textLines.size() > titleLinesCount) {
					tooltipHeight += 2; // gap between title lines and next
										// lines
				}
			}

			if (tooltipY < 4) {
				tooltipY = 4;
			} else if (tooltipY + tooltipHeight + 4 > screenHeight) {
				tooltipY = screenHeight - tooltipHeight - 4;
			}

			final int zLevel = 300;
			// int backgroundColor = 0xF0100010;
			// int borderColorStart = 0x505000FF;
			// int borderColorEnd = (borderColorStart & 0xFEFEFE) >> 1 |
			// borderColorStart & 0xFF000000;
			// RenderTooltipEvent.Color colorEvent = new
			// RenderTooltipEvent.Color(stack, textLines,
			// tooltipX, tooltipY, font, backgroundColor, borderColorStart,
			// borderColorEnd);
			// MinecraftForge.EVENT_BUS.post(colorEvent);
			// backgroundColor = colorEvent.getBackground();
			// borderColorStart = colorEvent.getBorderStart();
			// borderColorEnd = colorEvent.getBorderEnd();
			drawGradientRect(zLevel, tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3,
					tooltipY - 3, backgroundColor, backgroundColor);
			drawGradientRect(zLevel, tooltipX - 3, tooltipY + tooltipHeight + 3,
					tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor,
					backgroundColor);
			drawGradientRect(zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3,
					tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
			drawGradientRect(zLevel, tooltipX - 4, tooltipY - 3, tooltipX - 3,
					tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
			drawGradientRect(zLevel, tooltipX + tooltipTextWidth + 3, tooltipY - 3,
					tooltipX + tooltipTextWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor,
					backgroundColor);
			drawGradientRect(zLevel, tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1,
					tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
			drawGradientRect(zLevel, tooltipX + tooltipTextWidth + 2, tooltipY - 3 + 1,
					tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColorStart,
					borderColorEnd);
			drawGradientRect(zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3,
					tooltipY - 3 + 1, borderColorStart, borderColorStart);
			drawGradientRect(zLevel, tooltipX - 3, tooltipY + tooltipHeight + 2,
					tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, borderColorEnd,
					borderColorEnd);

			// MinecraftForge.EVENT_BUS.post(new
			// RenderTooltipEvent.PostBackground(stack, textLines,
			// tooltipX, tooltipY, font, tooltipTextWidth, tooltipHeight));
			// int tooltipTop = tooltipY;

			for (int lineNumber = 0; lineNumber < textLines.size(); ++lineNumber) {
				String line = textLines.get(lineNumber);
				font.drawStringWithShadow(line, (float) tooltipX, (float) tooltipY, -1);

				if (lineNumber + 1 == titleLinesCount) {
					tooltipY += 2;
				}

				tooltipY += 10;
			}

			// MinecraftForge.EVENT_BUS.post(new
			// RenderTooltipEvent.PostText(stack, textLines, tooltipX,
			// tooltipTop, font, tooltipTextWidth, tooltipHeight));

			GlStateManager.enableLighting();
			GlStateManager.enableDepth();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.enableRescaleNormal();
		}
	}

	private static void drawGradientRect(int zLevel, int left, int top, int right, int bottom,
			int startColor, int endColor) {
		float startAlpha = (float) (startColor >> 24 & 255) / 255.0F;
		float startRed = (float) (startColor >> 16 & 255) / 255.0F;
		float startGreen = (float) (startColor >> 8 & 255) / 255.0F;
		float startBlue = (float) (startColor & 255) / 255.0F;
		float endAlpha = (float) (endColor >> 24 & 255) / 255.0F;
		float endRed = (float) (endColor >> 16 & 255) / 255.0F;
		float endGreen = (float) (endColor >> 8 & 255) / 255.0F;
		float endBlue = (float) (endColor & 255) / 255.0F;

		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		buffer.pos(right, top, zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
		buffer.pos(left, top, zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
		buffer.pos(left, bottom, zLevel).color(endRed, endGreen, endBlue, endAlpha).endVertex();
		buffer.pos(right, bottom, zLevel).color(endRed, endGreen, endBlue, endAlpha).endVertex();
		tessellator.draw();

		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}
	// End copy from GuiUtils

}
