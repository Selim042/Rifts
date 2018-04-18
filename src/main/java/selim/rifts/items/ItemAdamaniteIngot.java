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
import selim.rifts.api.docs.pages.DocPageCraftingRecipe;
import selim.rifts.api.docs.pages.DocPageText;

public class ItemAdamaniteIngot extends Item implements IDocEntryResource {

	public ItemAdamaniteIngot() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "adamanite_ingot"));
		this.setUnlocalizedName(ModInfo.ID + ":adamanite_ingot");
		this.setCreativeTab(EnderRifts.mainTab);
	}

	// Start DocEntryResource
	// @SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getEntryRegistryName() {
		return new ResourceLocation(ModInfo.ID, "adamanite");
	}

	// @SideOnly(Side.CLIENT)
	@Override
	public String getEntryUnlocalizedName() {
		return ModInfo.ID + ":adamanite";
	}

	// @SideOnly(Side.CLIENT)
	@Override
	public DocCategory getEntryCategory() {
		return RiftRegistry.Categories.RIFT_END;
	}

	// @SideOnly(Side.CLIENT)
	@Override
	public DocPage[] getEntryPages() {
		DocPage[] pages = new DocPage[10];
		pages[0] = new DocPageText(ModInfo.ID + ":adamanite_0_0", ModInfo.ID + ":adamanite_0_1");
		pages[1] = new DocPageCraftingRecipe(new ResourceLocation(ModInfo.ID, "adamanite_pickaxe"));
		pages[2] = new DocPageCraftingRecipe(new ResourceLocation(ModInfo.ID, "adamanite_axe"));
		pages[3] = new DocPageCraftingRecipe(new ResourceLocation(ModInfo.ID, "adamanite_shovel"));
		pages[4] = new DocPageCraftingRecipe(new ResourceLocation(ModInfo.ID, "adamanite_hoe"));
		pages[5] = new DocPageCraftingRecipe(new ResourceLocation(ModInfo.ID, "adamanite_sword"));
		pages[6] = new DocPageCraftingRecipe(new ResourceLocation(ModInfo.ID, "adamanite_helmet"));
		pages[7] = new DocPageCraftingRecipe(new ResourceLocation(ModInfo.ID, "adamanite_chestplate"));
		pages[8] = new DocPageCraftingRecipe(new ResourceLocation(ModInfo.ID, "adamanite_leggings"));
		pages[9] = new DocPageCraftingRecipe(new ResourceLocation(ModInfo.ID, "adamanite_boots"));
		return pages;
	}

	// @SideOnly(Side.CLIENT)
	@Override
	public ItemStack getEntryIcon() {
		return new ItemStack(this);
	}
	// End DocEntryResource

}
