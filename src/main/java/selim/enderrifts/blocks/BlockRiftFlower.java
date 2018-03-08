package selim.enderrifts.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
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

public class BlockRiftFlower extends BlockBush implements IDocEntryResource {

	public BlockRiftFlower() {
		super(Material.PLANTS);
		this.setRegistryName(ModInfo.ID, "rift_flower");
		this.setUnlocalizedName(ModInfo.ID + ":rift_flower");
		this.setCreativeTab(EnderRifts.mainTab);
	}

	// Start DocEntryResource
	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getEntryRegistryName() {
		return new ResourceLocation(ModInfo.ID, "rift_flower");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getEntryUnlocalizedName() {
		return ModInfo.ID + ":rift_flower";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public DocCategory getEntryCategory() {
		return ClientProxy.Categories.RIFT_OVERWORLD;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public DocPage[] getEntryPages() {
		DocPage[] pages = new DocPage[2];
		pages[0] = new DocPageText(ModInfo.ID + ":rift_flower_0_0", ModInfo.ID + ":rift_flower_0_1");
		pages[1] = new DocPageCraftingRecipe(new ResourceLocation(ModInfo.ID, "unstable_universal_dye"));
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

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return false;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Plains;
	}

	@Override
	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.XZ;
	}

}
