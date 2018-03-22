package selim.rifts.gui.buttons;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderItem;

public class HotSpotButton extends GuiButton {
	private final Minecraft mc;
	private final FontRenderer fontRendererObj;
	@SuppressWarnings("unused")
	private final RenderItem itemRender;

	private final List<String> list;
	private final int x;
	private final int y;
	private final int buttonWidth;
	private final int buttonHeight;
	private final int screenWidth;
	private final int screenHeight;

	public HotSpotButton(int id, List<String> list, int x, int y, int buttonWidth, int buttonHeight, int screenWidth,
			int screenHeight) {
		super(id, x, y, null);
		mc = Minecraft.getMinecraft();
		fontRendererObj = mc.fontRenderer;
		itemRender = mc.getRenderItem();
		this.list = list;
		this.x = x;
		this.y = y;
		this.buttonWidth = buttonWidth;
		this.buttonHeight = buttonHeight;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (mouseX > x && mouseX < x + buttonWidth && mouseY > y && mouseY < y + buttonHeight) {
			net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(list, mouseX, mouseY, screenWidth,
					screenHeight, -1, fontRendererObj);
		}
	}

}
