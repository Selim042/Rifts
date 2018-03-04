package selim.enderrifts.items;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;

public class ItemOpal extends Item {

	public ItemOpal() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "opal"));
		this.setUnlocalizedName(ModInfo.ID + ":opal");
		this.setCreativeTab(EnderRifts.mainTab);
	}

}
