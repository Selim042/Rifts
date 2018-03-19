package selim.enderrifts.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;
import selim.enderrifts.gui.GuiHandler;

public class ItemRiftEye extends Item {

	public ItemRiftEye() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "rift_eye"));
		this.setUnlocalizedName(ModInfo.ID + ":rift_eye");
		this.setCreativeTab(EnderRifts.mainTab);
		this.maxStackSize = 1;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (world.isRemote)
			player.openGui(EnderRifts.instance, GuiHandler.RIFT_EYE, world, 0, 0, 0);
		return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand));
	}

}
