package selim.enderrifts.events.handlers;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class AmethystTooltip {

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void tooltipRender(RenderTooltipEvent.Pre event) {
		ItemStack stack = event.getStack();
		if (!isInfused(stack))
			return;
	}

	@SubscribeEvent
	public void tooltipText(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		// List<String> nbtStrings = new LinkedList<String>();
		// NBTUtils.nbtToStringList(nbtStrings, stack.getTagCompound());
		// event.getToolTip().addAll(nbtStrings);
		for (int oreId : OreDictionary.getOreIDs(stack))
			event.getToolTip().add(" - " + OreDictionary.getOreName(oreId));
		if (!isInfused(stack))
			return;
		event.getToolTip()
				.add(ChatFormatting.DARK_PURPLE.toString() + ChatFormatting.ITALIC + "Amethyst Infused");
	}

	private boolean isInfused(ItemStack stack) {
		if (stack == null || stack.isEmpty())
			return false;
		NBTTagCompound nbt = stack.getTagCompound();
		return nbt == null ? false : nbt.getBoolean("amethystInfused");
	}

}
