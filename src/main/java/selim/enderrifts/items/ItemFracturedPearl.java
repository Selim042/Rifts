package selim.enderrifts.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;
import selim.enderrifts.api.docs.DocCategory;
import selim.enderrifts.api.docs.DocPage;
import selim.enderrifts.api.docs.IDocEntryResource;
import selim.enderrifts.api.docs.pages.DocPageCraftingRecipe;
import selim.enderrifts.api.docs.pages.DocPageText;
import selim.enderrifts.proxy.ClientProxy;

public class ItemFracturedPearl extends Item implements IDocEntryResource {

	public ItemFracturedPearl() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "fractured_pearl"));
		this.setUnlocalizedName(ModInfo.ID + ":fractured_pearl");
		this.setCreativeTab(EnderRifts.mainTab);
	}

	// Start DocEntryResource
	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getEntryRegistryName() {
		return new ResourceLocation(ModInfo.ID, "pearls");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getEntryUnlocalizedName() {
		return ModInfo.ID + ":pearls";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public DocCategory getEntryCategory() {
		return ClientProxy.Categories.PRIMARY_CATEGORY;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public DocPage[] getEntryPages() {
		DocPage[] pages = new DocPage[1];
		pages[0] = new DocPageText(ModInfo.ID + ":pearls_0_0");
		return pages;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getEntryIcon() {
		return new ItemStack(this);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getLinkedEntry() {
		return this.getEntryRegistryName();
	}
	// End DocEntryResource

}
