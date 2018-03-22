package selim.rifts.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.RiftRegistry;
import selim.rifts.api.docs.DocCategory;
import selim.rifts.api.docs.DocPage;
import selim.rifts.api.docs.IDocEntryResource;
import selim.rifts.api.docs.pages.DocPageText;

public class ItemAdamantineIngot extends Item implements IDocEntryResource {

	public ItemAdamantineIngot() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "adamantine_ingot"));
		this.setUnlocalizedName(ModInfo.ID + ":adamantine_ingot");
		this.setCreativeTab(EnderRifts.mainTab);
	}

	// Start DocEntryResource
//	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getEntryRegistryName() {
		return new ResourceLocation(ModInfo.ID, "adamantine");
	}

//	@SideOnly(Side.CLIENT)
	@Override
	public String getEntryUnlocalizedName() {
		return ModInfo.ID + ":adamantine";
	}

//	@SideOnly(Side.CLIENT)
	@Override
	public DocCategory getEntryCategory() {
		return RiftRegistry.Categories.RIFT_END;
	}

//	@SideOnly(Side.CLIENT)
	@Override
	public DocPage[] getEntryPages() {
		DocPage[] pages = new DocPage[1];
		pages[0] = new DocPageText(ModInfo.ID + ":adamantine_0");
		return pages;
	}

//	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getEntryIcon() {
		return new ItemStack(this);
	}
	// End DocEntryResource

}
