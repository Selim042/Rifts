package selim.rifts.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;

public class BlockWillowPlanks extends Block {

	public BlockWillowPlanks() {
		super(Material.WOOD);
		this.setRegistryName(ModInfo.ID, "willow_planks");
		this.setUnlocalizedName(ModInfo.ID + ":willow_planks");
		this.setCreativeTab(EnderRifts.mainTab);
	}

}
