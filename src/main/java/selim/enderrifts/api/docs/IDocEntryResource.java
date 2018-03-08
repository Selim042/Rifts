package selim.enderrifts.api.docs;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IDocEntryResource extends IDocEntryLink {

	@SideOnly(Side.CLIENT)
	public default DocCategory getEntryCategory() {
		return null;
	}

	@SideOnly(Side.CLIENT)
	public default String getEntryUnlocalizedName() {
		return null;
	}

	@SideOnly(Side.CLIENT)
	public default DocPage[] getEntryPages() {
		return null;
	}

	@SideOnly(Side.CLIENT)
	public default ItemStack getEntryIcon() {
		return null;
	}

	@SideOnly(Side.CLIENT)
	public default ResourceLocation getEntryRegistryName() {
		return null;
	}

}
