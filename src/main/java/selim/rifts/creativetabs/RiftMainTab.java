package selim.rifts.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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

	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> items) {
		items.add(new ItemStack(RiftRegistry.Items.RIFT_EYE));
		super.displayAllRelevantItems(items);
		for (int i = 1; i < items.size(); i++) {
			if (items.get(i).getItem().equals(RiftRegistry.Items.RIFT_EYE)) {
				items.remove(i);
				break;
			}
		}
	}

}
