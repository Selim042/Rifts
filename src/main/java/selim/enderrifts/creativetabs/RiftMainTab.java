package selim.enderrifts.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import selim.enderrifts.ModInfo;
import selim.enderrifts.RiftRegistry;

public class RiftMainTab extends CreativeTabs {

	public RiftMainTab() {
		super(ModInfo.ID + ":main");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(RiftRegistry.Items.RIFT_EYE);
	}

}
