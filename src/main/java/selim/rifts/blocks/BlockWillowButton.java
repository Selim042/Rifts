package selim.rifts.blocks;

import net.minecraft.block.BlockButtonWood;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;

public class BlockWillowButton extends BlockButtonWood {

	public BlockWillowButton() {
		this.setRegistryName(ModInfo.ID, "willow_button");
		this.setUnlocalizedName(ModInfo.ID + ":willow_button");
		this.setCreativeTab(EnderRifts.mainTab);
	}

}
