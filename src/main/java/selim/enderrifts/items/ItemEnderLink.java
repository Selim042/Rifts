package selim.enderrifts.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;
import selim.enderrifts.compat.EnderStorageHelper;

public class ItemEnderLink extends ItemBound {

	public ItemEnderLink() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "ender_link"));
		this.setUnlocalizedName(ModInfo.ID + ":ender_link");
		this.setCreativeTab(EnderRifts.mainTab);
		this.maxStackSize = 1;
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
		if (Loader.isModLoaded(EnderStorageHelper.ID)) {
			if (!this.isBound(stack))
				tooltip.add(ChatFormatting.GRAY + ChatFormatting.ITALIC.toString()
						+ "Unbound will connect to your vanilla Ender Chest");
			super.addInformation(stack, world, tooltip, flagIn);
		}
	}

	@Override
	public boolean isValid(World world, BlockPos pos) {
		return Loader.isModLoaded(EnderStorageHelper.ID) && EnderStorageHelper.isEnderChest(world, pos);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn,
			EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (!worldIn.isRemote) {
			if (playerIn.isSneaking())
				return super.onItemRightClick(worldIn, playerIn, handIn);
			if (Loader.isModLoaded(EnderStorageHelper.ID) && this.isBound(stack)) {
				EnderStorageHelper.openChest(playerIn, stack);
			} else {
				InventoryEnderChest eChest = playerIn.getInventoryEnderChest();
				if (eChest != null)
					playerIn.displayGUIChest(eChest);
			}
		}
		return ActionResult.newResult(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

}
