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

public class ItemOpal extends Item implements IDocEntryResource {

	public ItemOpal() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "opal"));
		this.setUnlocalizedName(ModInfo.ID + ":opal");
		this.setCreativeTab(EnderRifts.mainTab);
	}

	// Start DocEntryResource
	// @SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getEntryRegistryName() {
		return new ResourceLocation(ModInfo.ID, "opal");
	}

	// @SideOnly(Side.CLIENT)
	@Override
	public String getEntryUnlocalizedName() {
		return ModInfo.ID + ":opal";
	}

	// @SideOnly(Side.CLIENT)
	@Override
	public DocCategory getEntryCategory() {
		return RiftRegistry.Categories.RIFT_OVERWORLD;
	}

	// @SideOnly(Side.CLIENT)
	@Override
	public DocPage[] getEntryPages() {
		DocPage[] pages = new DocPage[1];
		pages[0] = new DocPageText("opal_0");
		return pages;
	}

	// @SideOnly(Side.CLIENT)
	@Override
	public ItemStack getEntryIcon() {
		return new ItemStack(this);
	}

	// @SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getLinkedEntry() {
		return this.getEntryRegistryName();
	}
	// End DocEntryResource

}
