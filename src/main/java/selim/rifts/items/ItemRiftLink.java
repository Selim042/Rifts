package selim.rifts.items;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;

public class ItemRiftLink extends ItemBound {

	public ItemRiftLink() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "rift_link"));
		this.setUnlocalizedName(ModInfo.ID + ":rift_link");
		this.setCreativeTab(EnderRifts.mainTab);
		this.setMaxStackSize(1);
	}

	@Override
	public boolean isValid(World world, BlockPos pos) {
		return true;
	}

}
