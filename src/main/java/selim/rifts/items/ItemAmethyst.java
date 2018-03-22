package selim.rifts.items;

import com.mojang.text2speech.Narrator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.RiftRegistry;
import selim.rifts.api.docs.DocCategory;
import selim.rifts.api.docs.DocPage;
import selim.rifts.api.docs.IDocEntryResource;
import selim.rifts.api.docs.pages.DocPageCraftingRecipe;
import selim.rifts.api.docs.pages.DocPageText;

public class ItemAmethyst extends Item implements IDocEntryResource {

	public ItemAmethyst() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "amethyst"));
		this.setUnlocalizedName(ModInfo.ID + ":amethyst");
		this.setCreativeTab(EnderRifts.mainTab);
	}

	// Start DocEntryResource
//	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getEntryRegistryName() {
		return new ResourceLocation(ModInfo.ID, "amethyst");
	}

//	@SideOnly(Side.CLIENT)
	@Override
	public String getEntryUnlocalizedName() {
		return ModInfo.ID + ":amethyst";
	}

//	@SideOnly(Side.CLIENT)
	@Override
	public DocCategory getEntryCategory() {
		return RiftRegistry.Categories.RIFT_OVERWORLD;
	}

//	@SideOnly(Side.CLIENT)
	@Override
	public DocPage[] getEntryPages() {
		DocPage[] pages = new DocPage[3];
		pages[0] = new DocPageText(ModInfo.ID + ":amethyst_0_0", ModInfo.ID + ":amethyst_0_1");
		pages[1] = new DocPageCraftingRecipe(new ResourceLocation(ModInfo.ID, "amethyst_block"));
		pages[2] = new DocPageCraftingRecipe(new ResourceLocation(ModInfo.ID, "amethyst_torch"));
		return pages;
	}

//	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getEntryIcon() {
		return new ItemStack(this);
	}

//	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getLinkedEntry() {
		return this.getEntryRegistryName();
	}
	// End DocEntryResource

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn,
			EnumHand handIn) {
		if (worldIn.isRemote) {
			Narrator narrator = Narrator.getNarrator();
			narrator.say("Hello!");
		}
		return ActionResult.newResult(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

}
