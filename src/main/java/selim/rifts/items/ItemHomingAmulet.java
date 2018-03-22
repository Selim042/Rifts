package selim.rifts.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.compat.BaublesHelper;

@Optional.Interface(iface = "baubles.api.IBauble", modid = BaublesHelper.ID)
public class ItemHomingAmulet extends Item implements /* IDocEntryResource, */ IBauble {

	public ItemHomingAmulet() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "homing_amulet"));
		this.setUnlocalizedName(ModInfo.ID + ":homing_amulet");
		this.setCreativeTab(EnderRifts.mainTab);
		this.maxStackSize = 1;
	}

	// // Start DocEntryResource
	// @SideOnly(Side.CLIENT)
	// @Override
	// public ResourceLocation getEntryRegistryName() {
	// return new ResourceLocation(ModInfo.ID, "rift_transport_node");
	// }
	//
	// @SideOnly(Side.CLIENT)
	// @Override
	// public String getEntryUnlocalizedName() {
	// return ModInfo.ID + ":rift_transport_node";
	// }
	//
	// @SideOnly(Side.CLIENT)
	// @Override
	// public DocCategory getEntryCategory() {
	// return ClientProxy.Categories.RIFT_END;
	// }
	//
	// @SideOnly(Side.CLIENT)
	// @Override
	// public DocPage[] getEntryPages() {
	// DocPage[] pages = new DocPage[1];
	// pages[0] = new DocPageText("rift_transport_node_0");
	// return pages;
	// }
	//
	// @SideOnly(Side.CLIENT)
	// @Override
	// public ItemStack getEntryIcon() {
	// return new ItemStack(this);
	// }
	//
	// @SideOnly(Side.CLIENT)
	// @Override
	// public ResourceLocation getLinkedEntry() {
	// return this.getEntryRegistryName();
	// }
	// // End DocEntryResource

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (world.isRemote)
			return ActionResult.newResult(EnumActionResult.PASS, stack);
		if (!BaublesHelper.isBaublesInstalled()) {
			BlockPos pos = EntityPlayer.getBedSpawnLocation(world,
					player.getBedLocation(world.provider.getDimension()), false);
			if (pos == null)
				pos = world.getSpawnPoint();
			// TODO: Teleport player to pos
			
		}
		return super.onItemRightClick(world, player, hand);
	}

	// Start Baubles
	@Optional.Method(modid = BaublesHelper.ID)
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.AMULET;
	}
	// End Baubles

}
