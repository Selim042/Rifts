package selim.rifts.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import selim.rifts.ModInfo;
import selim.rifts.RiftRegistry;

public class RiftMainTab extends CreativeTabs {

	public RiftMainTab() {
		super(ModInfo.ID + ":main");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(RiftRegistry.Items.RIFT_EYE);
	}

}
