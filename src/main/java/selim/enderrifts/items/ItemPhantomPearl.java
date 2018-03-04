package selim.enderrifts.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;
import selim.enderrifts.entities.EntityPhantomPearl;

public class ItemPhantomPearl extends Item {

	public ItemPhantomPearl() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "phantom_pearl"));
		this.setUnlocalizedName(ModInfo.ID + ":phantom_pearl");
		this.setCreativeTab(EnderRifts.mainTab);
		this.maxStackSize = 16;
	}

	/**
	 * Called when the equipped item is right clicked.
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn,
			EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		if (!playerIn.capabilities.isCreativeMode) {
			itemstack.shrink(1);
		}

		worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ,
				SoundEvents.ENTITY_ENDERPEARL_THROW, SoundCategory.NEUTRAL, 0.5F,
				0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		playerIn.getCooldownTracker().setCooldown(this, 20);

		if (!worldIn.isRemote) {
			EntityPhantomPearl entityenderpearl = new EntityPhantomPearl(worldIn, playerIn);
			entityenderpearl.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F,
					1.0F);
			worldIn.spawnEntity(entityenderpearl);
		}

		playerIn.addStat(StatList.getObjectUseStats(this));
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	}

}
