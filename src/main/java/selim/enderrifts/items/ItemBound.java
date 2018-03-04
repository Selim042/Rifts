package selim.enderrifts.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.enderrifts.misc.WorldBlockPos;
import selim.enderrifts.utils.NBTUtils;

public abstract class ItemBound extends Item {

	protected static final String BOUND_KEY = "boundPos";

	public abstract boolean isValid(World world, BlockPos pos);

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
		if (isBound(stack)) {
			WorldBlockPos pos = (WorldBlockPos) NBTUtils.getPos(stack.getSubCompound(BOUND_KEY));
			IBlockState state = pos.getState();
			tooltip.add("Bound to " + state.getBlock().getLocalizedName());
			tooltip.add(ChatFormatting.GRAY + ChatFormatting.ITALIC.toString()
					+ "Shift right click in the air to clear binding.");
			if (!GuiScreen.isShiftKeyDown())
				tooltip.add(ChatFormatting.GRAY + ChatFormatting.ITALIC.toString()
						+ "Shift for more information...");
			else {
				WorldProvider prov = pos.getWorld().provider;
				tooltip.add(ChatFormatting.GRAY + ChatFormatting.ITALIC.toString() + "World: "
						+ ChatFormatting.GRAY + prov.getDimensionType().toString() + " ("
						+ prov.getDimension() + ")");
				tooltip.add(ChatFormatting.GRAY + ChatFormatting.ITALIC.toString() + "Coordinates:");
				tooltip.add(ChatFormatting.GRAY + ChatFormatting.ITALIC.toString() + " x: "
						+ ChatFormatting.GRAY + pos.getX());
				tooltip.add(ChatFormatting.GRAY + ChatFormatting.ITALIC.toString() + " y: "
						+ ChatFormatting.GRAY + pos.getY());
				tooltip.add(ChatFormatting.GRAY + ChatFormatting.ITALIC.toString() + " z: "
						+ ChatFormatting.GRAY + pos.getZ());
			}
		} else {
			tooltip.add("Unbound");
			tooltip.add(ChatFormatting.GRAY + ChatFormatting.ITALIC.toString()
					+ "Shift right click a block to set binding.");
		}
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote && player.isSneaking()) {
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt == null)
				nbt = new NBTTagCompound();
			if (nbt.getBoolean("clicked"))
				return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
			nbt.setBoolean("clicked", true);
			if (!this.isValid(world, pos))
				return EnumActionResult.FAIL;
			// TileEntity te = world.getTileEntity(pos);
			// if (te == null ||
			// !te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
			// null))
			// return EnumActionResult.FAIL;
			nbt.setTag(BOUND_KEY, NBTUtils.getNBT(new WorldBlockPos(world, pos)));
			stack.setTagCompound(nbt);
			player.sendMessage(new TextComponentString("Bound to block."));
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote && player.isSneaking() && isBound(stack)) {
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt == null)
				nbt = new NBTTagCompound();
			if (nbt.getBoolean("clicked"))
				return super.onItemRightClick(world, player, hand);
			nbt.setBoolean("clicked", true);
			nbt.removeTag(BOUND_KEY);
			stack.setTagCompound(nbt);
			player.sendMessage(new TextComponentString("Cleared binding."));
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (!world.isRemote) {
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt == null)
				return;
			nbt.removeTag("clicked");
			stack.setTagCompound(nbt);
		}
	}

	public WorldBlockPos getPos(ItemStack stack) {
		return (WorldBlockPos) NBTUtils.getPos(stack.getSubCompound(BOUND_KEY));
	}

	public boolean isBound(ItemStack stack) {
		if (!stack.getItem().equals(this))
			return false;
		BlockPos bPos = NBTUtils.getPos(stack.getSubCompound(BOUND_KEY));
		if (bPos == null || !(bPos instanceof WorldBlockPos))
			return false;
		WorldBlockPos wPos = (WorldBlockPos) bPos;
		return this.isValid(wPos.getWorld(), wPos);
		// TileEntity te = wPos.getTileEntity();
		// if (te == null ||
		// te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
		// null))
		// return true;
		// return false;
	}

}
