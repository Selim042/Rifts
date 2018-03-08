package selim.enderrifts.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import selim.enderrifts.RiftRegistry;
import selim.enderrifts.blocks.BlockCustomFlowerPot;

public class ItemBlockFlower extends ItemBlock {

	public ItemBlockFlower(Block block) {
		super(block);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		// if (!worldIn.isRemote) {
		IBlockState hitBlock = worldIn.getBlockState(pos);
		if (hitBlock != null && !(hitBlock instanceof BlockAir)
				&& hitBlock.getBlock() instanceof BlockFlowerPot) {
			IBlockState pot = RiftRegistry.Blocks.FLOWER_POT.getDefaultState();
			ItemStack stack = player.getHeldItem(hand);
			BlockCustomFlowerPot.EnumFlowerType type = BlockCustomFlowerPot.EnumFlowerType
					.valueOfItem(stack);
			if (type != null && type != BlockCustomFlowerPot.EnumFlowerType.EMPTY) {
				pot = pot.withProperty(BlockCustomFlowerPot.CONTENTS, type);
				stack.shrink(1);
				worldIn.setBlockState(pos, pot);
				return EnumActionResult.SUCCESS;
			}
		}
		// }
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

}
