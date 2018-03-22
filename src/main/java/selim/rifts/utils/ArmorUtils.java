package selim.rifts.utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;

public class ArmorUtils {

	public static Item[] genArmor(ArmorMaterial material, CreativeTabs creativeTab,
			ResourceLocation regName, String unlocalName) {
		ItemGenHelmet helm = new ItemGenHelmet(material, new ResourceLocation(regName + "_helmet"),
				creativeTab, unlocalName + "_helmet");
		ItemGenChestplate chest = new ItemGenChestplate(material,
				new ResourceLocation(regName + "_chestplate"), creativeTab, unlocalName + "_chestplate");
		ItemGenLeggings legs = new ItemGenLeggings(material,
				new ResourceLocation(regName + "_leggings"), creativeTab, unlocalName + "_leggings");
		ItemGenBoots boots = new ItemGenBoots(material, new ResourceLocation(regName + "_boots"),
				creativeTab, unlocalName + "_boots");
		return new Item[] { helm, chest, legs, boots };
	}

	public static class ItemGenHelmet extends ItemArmor {

		private final CreativeTabs creativeTab;

		private ItemGenHelmet(ArmorMaterial material, ResourceLocation regName, CreativeTabs creativeTab,
				String unlocalName) {
			super(material, 1, EntityEquipmentSlot.HEAD);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.COMBAT, this.creativeTab };
		}

	}

	public static class ItemGenChestplate extends ItemArmor {

		private final CreativeTabs creativeTab;

		private ItemGenChestplate(ArmorMaterial material, ResourceLocation regName,
				CreativeTabs creativeTab, String unlocalName) {
			super(material, 1, EntityEquipmentSlot.CHEST);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.COMBAT, this.creativeTab };
		}

	}

	public static class ItemGenLeggings extends ItemArmor {

		private final CreativeTabs creativeTab;

		private ItemGenLeggings(ArmorMaterial material, ResourceLocation regName,
				CreativeTabs creativeTab, String unlocalName) {
			super(material, 1, EntityEquipmentSlot.LEGS);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.COMBAT, this.creativeTab };
		}

	}

	public static class ItemGenBoots extends ItemArmor {

		private final CreativeTabs creativeTab;

		private ItemGenBoots(ArmorMaterial material, ResourceLocation regName, CreativeTabs creativeTab,
				String unlocalName) {
			super(material, 1, EntityEquipmentSlot.FEET);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.COMBAT, this.creativeTab };
		}

	}

}
