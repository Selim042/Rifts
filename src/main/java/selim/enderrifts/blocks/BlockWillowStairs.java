package selim.enderrifts.blocks;

import net.minecraft.block.BlockStairs;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;

public class BlockWillowStairs extends BlockStairs {

	public BlockWillowStairs(BlockWillowPlanks planks) {
		super(planks.getDefaultState());
		this.setRegistryName(ModInfo.ID, "willow_stairs");
		this.setUnlocalizedName(ModInfo.ID + ":" + "willow_stairs");
		this.setCreativeTab(EnderRifts.mainTab);
		this.useNeighborBrightness = true;
	}

}
