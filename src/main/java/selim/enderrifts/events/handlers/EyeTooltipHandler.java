package selim.enderrifts.events.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.enderrifts.ModInfo;
import selim.enderrifts.api.docs.DocEntry;
import selim.enderrifts.api.docs.IDocEntryLink;
import selim.enderrifts.gui.EntryScreen;
import selim.enderrifts.gui.GuiHandler;
import selim.enderrifts.items.ItemRiftEye;
import selim.enderrifts.proxy.ClientProxy;

@SuppressWarnings("deprecation")
public class EyeTooltipHandler {

	private static final int DEFAULT_TIMER = 160;
	private static final String holdText = I18n.translateToLocal("misc." + ModInfo.ID + ":hold_ctrl");
	private int timer = DEFAULT_TIMER;
	private ItemStack lastHovered;

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
			font.drawString(holdText, x, y, 0xFFFFFF);
			if (!GuiScreen.isCtrlKeyDown())
				toDraw = holdText + "...";
			else if (lastHovered == null || !lastHovered.equals(link)) {
				DocEntry entry = ClientProxy.Registries.DOC_ENTRIES.getValue(link.getLinkedEntry());
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
		if (item instanceof IDocEntryLink)
			return (IDocEntryLink) item;
		if (item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof IDocEntryLink)
			return (IDocEntryLink) ((ItemBlock) item).getBlock();
		return null;
	}

	private boolean hasEye(EntityPlayer player) {
		for (ItemStack stack : player.inventoryContainer.inventoryItemStacks)
			if (!stack.isEmpty() && stack.getItem() instanceof ItemRiftEye)
				return true;
		return false;
	}

	protected void renderText(String line, ItemStack stack, FontRenderer font, int x, int y, int width,
			int height) {
		font.drawString(line, x, y, 0xFFFFFF);
		// TODO: Figure out why this isn't working V
		// GuiUtils.preItemToolTip(stack);
		// GuiUtils.drawHoveringText(Collections.singletonList(line), x, y,
		// width, height, -1, font);
		// GuiUtils.postItemToolTip();
	}

}
