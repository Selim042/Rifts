package selim.rifts.items;

import java.awt.Color;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.api.docs.IDocEntryLink;

public class ItemUniversalDye extends Item implements IDocEntryLink {

	public ItemUniversalDye() {
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "universal_dye"));
		this.setCreativeTab(EnderRifts.mainTab);
		this.setHasSubtypes(true);
		// this.setMaxStackSize(1);
	}

	@Override
	public ResourceLocation getLinkedEntry() {
		return new ResourceLocation(ModInfo.ID, "rift_flower");
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {
		// NBTTagCompound nbt = stack.getTagCompound();
		// if (nbt != null)
		// tooltip.add("timer: " + nbt.getShort("timer"));
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot,
			boolean isSelected) {
		// if (!worldIn.isRemote) {
		// NBTTagCompound nbt = stack.getTagCompound();
		// if (nbt == null)
		// nbt = new NBTTagCompound();
		// short timer = nbt.getShort("timer");
		// if (timer >= 1024) {
		// timer = 0;
		// stack.setItemDamage(worldIn.rand.nextInt(16));
		// } else
		// timer += worldIn.rand.nextInt(8);
		// nbt.setShort("timer", timer);
		// stack.setTagCompound(nbt);
		// }
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab != null && this.isInCreativeTab(tab))
			for (int i = 0; i < 17; i++)
				items.add(new ItemStack(this, 1, i));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getMetadata() == 16)
			return "item." + ModInfo.ID + ":universal_dye_unstable";
		return "item." + ModInfo.ID + ":universal_dye_"
				+ EnumDyeColor.byDyeDamage(stack.getMetadata()).name().toLowerCase();
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		return stack.copy();
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return stack.getMetadata() != 16;
	}

	@SideOnly(Side.CLIENT)
	public static class UniversalDyeItemColor implements IItemColor, IBlockColor {

		public static final UniversalDyeItemColor INSTANCE = new UniversalDyeItemColor();

		private UniversalDyeItemColor() {}

		private static float hue = 0.0f;

		@Override
		public int colorMultiplier(ItemStack stack, int tintIndex) {
			if (stack.getMetadata() == 16) {
				if (hue >= Float.MAX_VALUE / 4)
					hue = 0.0f;
				// TODO: Possibly have saturation & brightness fade as well
				return Color.HSBtoRGB(hue += Item.itemRand.nextFloat() / 4000, 1.0f, 1.0f);
			}
			EnumDyeColor dye = EnumDyeColor.byDyeDamage(stack.getMetadata());
			return dye.getColorValue();
		}

		@Override
		public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos,
				int tintIndex) {
			return Color.HSBtoRGB(hue, 1.0f, 1.0f);
		}

	}

}
