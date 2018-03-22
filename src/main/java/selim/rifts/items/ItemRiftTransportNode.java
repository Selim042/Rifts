package selim.rifts.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.RiftRegistry;
import selim.rifts.api.docs.DocCategory;
import selim.rifts.api.docs.DocPage;
import selim.rifts.api.docs.IDocEntryResource;
import selim.rifts.api.docs.pages.DocPageText;
import selim.rifts.compat.BaublesHelper;
import selim.rifts.misc.WorldBlockPos;
import selim.rifts.utils.NBTUtils;

@Optional.Interface(iface = "baubles.api.IBauble", modid = BaublesHelper.ID)
public class ItemRiftTransportNode extends ItemBound implements IDocEntryResource, IBauble {

	public ItemRiftTransportNode() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "rift_transport_node"));
		this.setUnlocalizedName(ModInfo.ID + ":rift_transport_node");
		this.setCreativeTab(EnderRifts.mainTab);
		this.maxStackSize = 1;
	}

	// Start DocEntryResource
//	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getEntryRegistryName() {
		return new ResourceLocation(ModInfo.ID, "rift_transport_node");
	}

//	@SideOnly(Side.CLIENT)
	@Override
	public String getEntryUnlocalizedName() {
		return ModInfo.ID + ":rift_transport_node";
	}

//	@SideOnly(Side.CLIENT)
	@Override
	public DocCategory getEntryCategory() {
		return RiftRegistry.Categories.RIFT_END;
	}

//	@SideOnly(Side.CLIENT)
	@Override
	public DocPage[] getEntryPages() {
		DocPage[] pages = new DocPage[1];
		pages[0] = new DocPageText("rift_transport_node_0");
		return pages;
	}

//	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getEntryIcon() {
		return new ItemStack(this);
	}

//	@SideOnly(Side.CLIENT)
	@Override
	public ResourceLocation getLinkedEntry() {
		return this.getEntryRegistryName();
	}
	// End DocEntryResource

//	@Override
//	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
//		tooltip.add(ChatFormatting.GRAY + ChatFormatting.ITALIC.toString()
//				+ "Will not pick up if target inventory is full.");
//		super.addInformation(stack, world, tooltip, flagIn);
//	}

	@Override
	public boolean isValid(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		return te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
	}

	@SubscribeEvent
	public void onPickup(EntityItemPickupEvent event) {
		ItemStack stack = event.getItem().getItem();
		ItemStack originalStack = stack.copy();
		EntityPlayer player = event.getEntityPlayer();
		ItemStack offStack = player.getHeldItemOffhand();
		// Baubles compat
		if (Loader.isModLoaded(BaublesHelper.ID) && !isBound(offStack)) {
			offStack = BaublesHelper.getBauble(player, this);
		}
		if (isBound(offStack)) {
			WorldBlockPos pos = (WorldBlockPos) NBTUtils.getPos(offStack.getSubCompound(BOUND_KEY));
			TileEntity te = pos.getTileEntity();
			if (te == null)
				return;
			IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			for (int i = 0; !stack.isEmpty() && i < handler.getSlots(); i++)
				stack = handler.insertItem(i, stack, false);
			if (!stack.isEmpty()) {
				if (areStacksSimilar(originalStack, stack, 0)) {
					System.out.println("test");
					return;
				}
				event.setResult(Result.ALLOW);
				World world = player.world;
				EntityItem oldItem = event.getItem();
				world.spawnEntity(
						new EntityItem(world, oldItem.posX, oldItem.posY, oldItem.posZ, stack));
				event.getItem().setDead();
				// player.sendMessage(new TextComponentString("test"));
				// event.getItem().getItem().setCount(stack.getCount());
			} else {
				event.setResult(Result.ALLOW);
				event.getItem().setDead();
			}
		}
	}

	/**
	 * Flags: 0, none, 1 ignore metadata, 2 ignore count, 4 ignore NBT
	 */
	private boolean areStacksSimilar(ItemStack a, ItemStack b, int flags) {
		return a.getItem().equals(b.getItem()) && ((flags & 1) > 0 || a.getMetadata() == b.getMetadata())
				&& ((flags & 2) > 0 || a.getCount() == b.getCount())
				&& ((flags & 4) > 0 || (a.hasTagCompound() == b.hasTagCompound() && a.hasTagCompound()
						&& a.getTagCompound().equals(b.getTagCompound())));
	}

	// Start Baubles
	@Optional.Method(modid = BaublesHelper.ID)
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.TRINKET;
	}
	// End Baubles

}
