package selim.enderrifts.items;

import com.mojang.text2speech.Narrator;

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

public class ItemAmethyst extends Item {

	public ItemAmethyst() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "amethyst"));
		this.setUnlocalizedName(ModInfo.ID + ":amethyst");
		this.setCreativeTab(EnderRifts.mainTab);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn,
			EnumHand handIn) {
		if (worldIn.isRemote) {
			Narrator narrator = Narrator.getNarrator();
			narrator.say("Hello!");
		}
		return ActionResult.newResult(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

}
