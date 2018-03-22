package selim.rifts.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.misc.WorldBlockPos;

public class ItemRiftAccess extends ItemBound {

	public ItemRiftAccess() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "inter_rift_access"));
		this.setUnlocalizedName(ModInfo.ID + ":inter_rift_access");
		this.setCreativeTab(EnderRifts.mainTab);
		this.maxStackSize = 1;
	}

	@Override
	public boolean isValid(World world, BlockPos pos) {
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!player.isSneaking() && this.isBound(stack)) {
			WorldBlockPos pos = getBoundPos(stack);
			IBlockState state = pos.getState();
			if (state.getBlock().onBlockActivated(pos.getWorld(), pos, state, player, hand,
					EnumFacing.NORTH, 0, 0, 0))
				return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
			else
				return ActionResult.newResult(EnumActionResult.FAIL, stack);
		} else
			return super.onItemRightClick(world, player, hand);
	}

}
