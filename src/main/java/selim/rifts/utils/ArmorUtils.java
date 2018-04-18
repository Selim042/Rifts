package selim.rifts.utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import selim.rifts.api.docs.IDocEntryLink;

public class ArmorUtils {

	public static Item[] genArmor(ArmorMaterial material, CreativeTabs creativeTab,
			ResourceLocation regName, String unlocalName) {
		return genArmor(material, creativeTab, regName, unlocalName, null);
	}

	public static Item[] genArmor(ArmorMaterial material, CreativeTabs creativeTab,
			ResourceLocation regName, String unlocalName, ResourceLocation docLink) {
		ItemGenHelmet helm = new ItemGenHelmet(material, new ResourceLocation(regName + "_helmet"),
				creativeTab, unlocalName + "_helmet", docLink);
		ItemGenChestplate chest = new ItemGenChestplate(material,
				new ResourceLocation(regName + "_chestplate"), creativeTab, unlocalName + "_chestplate",
				docLink);
		ItemGenLeggings legs = new ItemGenLeggings(material, new ResourceLocation(regName + "_leggings"),
				creativeTab, unlocalName + "_leggings", docLink);
		ItemGenBoots boots = new ItemGenBoots(material, new ResourceLocation(regName + "_boots"),
				creativeTab, unlocalName + "_boots", docLink);
		return new Item[] { helm, chest, legs, boots };
	}

	public static class ItemGenHelmet extends ItemArmor implements IDocEntryLink {

		private final CreativeTabs creativeTab;
		private final ResourceLocation docLink;

		private ItemGenHelmet(ArmorMaterial material, ResourceLocation regName, CreativeTabs creativeTab,
				String unlocalName, ResourceLocation docLink) {
			super(material, 1, EntityEquipmentSlot.HEAD);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
			this.docLink = docLink;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.COMBAT, this.creativeTab };
		}

		@Override
		public ResourceLocation getLinkedEntry() {
			return this.docLink;
		}

	}

	public static class ItemGenChestplate extends ItemArmor implements IDocEntryLink {

		private final CreativeTabs creativeTab;
		private final ResourceLocation docLink;

		private ItemGenChestplate(ArmorMaterial material, ResourceLocation regName,
				CreativeTabs creativeTab, String unlocalName, ResourceLocation docLink) {
			super(material, 1, EntityEquipmentSlot.CHEST);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
			this.docLink = docLink;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.COMBAT, this.creativeTab };
		}

		@Override
		public ResourceLocation getLinkedEntry() {
			return this.docLink;
		}

	}

	public static class ItemGenLeggings extends ItemArmor implements IDocEntryLink {

		private final CreativeTabs creativeTab;
		private final ResourceLocation docLink;

		private ItemGenLeggings(ArmorMaterial material, ResourceLocation regName,
				CreativeTabs creativeTab, String unlocalName, ResourceLocation docLink) {
			super(material, 1, EntityEquipmentSlot.LEGS);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
			this.docLink = docLink;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.COMBAT, this.creativeTab };
		}

		@Override
		public ResourceLocation getLinkedEntry() {
			return this.docLink;
		}

	}

	public static class ItemGenBoots extends ItemArmor implements IDocEntryLink {

		private final CreativeTabs creativeTab;
		private final ResourceLocation docLink;

		private ItemGenBoots(ArmorMaterial material, ResourceLocation regName, CreativeTabs creativeTab,
				String unlocalName, ResourceLocation docLink) {
			super(material, 1, EntityEquipmentSlot.FEET);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
			this.docLink = docLink;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.COMBAT, this.creativeTab };
		}

		@Override
		public ResourceLocation getLinkedEntry() {
			return this.docLink;
		}

	}

}
