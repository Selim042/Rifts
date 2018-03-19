package selim.enderrifts.blocks;

import net.minecraft.block.BlockButtonWood;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;

public class BlockWillowButton extends BlockButtonWood {

	public BlockWillowButton() {
		this.setRegistryName(ModInfo.ID, "willow_button");
		this.setUnlocalizedName(ModInfo.ID + ":willow_button");
		this.setCreativeTab(EnderRifts.mainTab);
	}

}
