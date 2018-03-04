package selim.enderrifts.items;

import java.util.List;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;

public class ItemUniversalDye extends Item {

	public ItemUniversalDye() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "universal_dye"));
		this.setCreativeTab(EnderRifts.mainTab);
		this.setHasSubtypes(true);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt != null)
			tooltip.add("timer: " + nbt.getShort("timer"));
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot,
			boolean isSelected) {
		if (!worldIn.isRemote) {
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt == null)
				nbt = new NBTTagCompound();
			short timer = nbt.getShort("timer");
			if (timer >= 1024) {
				timer = 0;
				stack.setItemDamage(worldIn.rand.nextInt(16));
			} else
				timer += worldIn.rand.nextInt(8);
			nbt.setShort("timer", timer);
			stack.setTagCompound(nbt);
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		// for (int i = 0; i < 16; i++)
		// items.add(new ItemStack(this, 1, i));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + ModInfo.ID + ":universal_dye_"
				+ EnumDyeColor.byDyeDamage(stack.getMetadata()).getDyeColorName().toLowerCase();
	}

	@SideOnly(Side.CLIENT)
	public class UniversalDyeItemColor implements IItemColor {

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			return 0;
		}

	}

}
