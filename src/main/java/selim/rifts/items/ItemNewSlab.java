package selim.rifts.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class ItemNewSlab extends ItemSlab {

	public ItemNewSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab) {
		super(block, singleSlab, doubleSlab);
		this.setRegistryName(singleSlab.getRegistryName());
		this.setHasSubtypes(false);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "tile." + super.getUnlocalizedName(stack);
	}

}