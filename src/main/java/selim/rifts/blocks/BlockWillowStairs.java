package selim.rifts.blocks;

import net.minecraft.block.BlockStairs;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;

public class BlockWillowStairs extends BlockStairs {

	public BlockWillowStairs(BlockWillowPlanks planks) {
		super(planks.getDefaultState());
		this.setRegistryName(ModInfo.ID, "willow_stairs");
		this.setUnlocalizedName(ModInfo.ID + ":" + "willow_stairs");
		this.setCreativeTab(EnderRifts.mainTab);
		this.useNeighborBrightness = true;
	}

}
