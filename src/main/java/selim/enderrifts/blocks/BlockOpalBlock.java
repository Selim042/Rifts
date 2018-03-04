package selim.enderrifts.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;

public class BlockOpalBlock extends Block {

	public BlockOpalBlock() {
		super(Material.ROCK);
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "opal_block"));
		this.setUnlocalizedName(ModInfo.ID + ":opal_block");
		this.setCreativeTab(EnderRifts.mainTab);
		this.setHardness(1.5f);
	}

}
