package selim.enderrifts.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.WorldProvider;
import selim.enderrifts.ModInfo;
import selim.enderrifts.items.ItemRiftLink;
import selim.enderrifts.misc.WorldBlockPos;

@SuppressWarnings("deprecation")
public class BoundInventoryScreen extends GuiContainer {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ModInfo.ID,
			"textures/gui/bound_inventory.png");
	private final ContainerBoundInventory container;

	public BoundInventoryScreen(ContainerBoundInventory container) {
		super(container);
		this.container = container;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, 166);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(
				this.container.getPlayerInventory().getDisplayName().getUnformattedText(), 8,
				this.ySize - 94, 4210752);
		ItemStack boundStack = this.container.getBindingInventory().getStackInSlot(0);
		if (boundStack.isEmpty() || !(boundStack.getItem() instanceof ItemRiftLink))
			return;
		WorldBlockPos pos = ((ItemRiftLink) boundStack.getItem()).getPos(boundStack);
		if (pos == null)
			return;
		WorldProvider prov = pos.getWorld().provider;
		this.fontRenderer.drawString(I18n.translateToLocal("misc." + ModInfo.ID + ":bound_to") + ": ", 8,
				this.ySize - 160, 4210752);
		this.fontRenderer.drawString(
				prov.getDimensionType().toString() + " (" + prov.getDimension() + ")", 60,
				this.ySize - 160, 4210752);
		this.fontRenderer.drawString(" x: " + pos.getX(), 60, this.ySize - 148, 4210752);
		this.fontRenderer.drawString(" y: " + pos.getY(), 60, this.ySize - 138, 4210752);
		this.fontRenderer.drawString(" z: " + pos.getZ(), 60, this.ySize - 128, 4210752);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

}
