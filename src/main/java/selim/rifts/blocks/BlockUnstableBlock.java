package selim.rifts.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;

public class BlockUnstableBlock extends Block {

	private final Block base;

	public BlockUnstableBlock(final IBlockState baseState) {
		super(baseState.getMaterial());
		this.base = baseState.getBlock();
		this.setRegistryName(ModInfo.ID, this.base.getRegistryName().getResourcePath() + "_unstable");
		int colonIndex = this.base.getUnlocalizedName().indexOf(":");
		if (colonIndex == -1)
			this.setUnlocalizedName(this.base.getUnlocalizedName() + "_unstable");
		else
			this.setUnlocalizedName(this.base.getUnlocalizedName().substring(colonIndex) + "_unstable");
		this.setCreativeTab(EnderRifts.mainTab);
	}

}
