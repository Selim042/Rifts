package selim.enderrifts.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import selim.enderrifts.ModInfo;
import selim.enderrifts.RiftsRegistry;

public class RiftMainTab extends CreativeTabs {

	public RiftMainTab() {
		super(ModInfo.ID + ":main");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(RiftsRegistry.Items.RIFT_EYE);
	}

}
