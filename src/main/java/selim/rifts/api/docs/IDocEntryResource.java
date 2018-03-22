package selim.rifts.api.docs;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IDocEntryResource extends IDocEntryLink {

	// @SideOnly(Side.CLIENT)
	public DocCategory getEntryCategory();

	// @SideOnly(Side.CLIENT)
	public String getEntryUnlocalizedName();

	// @SideOnly(Side.CLIENT)
	public DocPage[] getEntryPages();

	// @SideOnly(Side.CLIENT)
	public ItemStack getEntryIcon();

	// @SideOnly(Side.CLIENT)
	public ResourceLocation getEntryRegistryName();

	@Override
	public default ResourceLocation getLinkedEntry() {
		return this.getEntryRegistryName();
	}

}
