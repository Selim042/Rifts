package selim.enderrifts.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import selim.enderrifts.ModInfo;

public class ItemDebugItem extends Item {

	public ItemDebugItem() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "debug_item"));
		this.setUnlocalizedName(ModInfo.ID + ":debug_item");
		this.setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack itemStack, World worldIn, List<String> list,
			ITooltipFlag flagIn) {
		list.add("This item should not show");
		list.add("up in released builds.");
		list.add("If it does, please report");
		list.add("this as a bug.");
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			if (player.isSneaking())
				player.sendMessage(new TextComponentString(worldIn.getBlockState(pos).toString()));
			else
				player.sendMessage(
						new TextComponentString(worldIn.getBlockState(pos.offset(facing)).toString()));
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot,
			boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		return super.onLeftClickEntity(stack, player, entity);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		return super.onEntityItemUpdate(entityItem);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

}
