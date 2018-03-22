package selim.rifts.utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;

public class ToolUtils {

	public static Item[] genToolset(ToolMaterial material, CreativeTabs creativeTab,
			ResourceLocation regName, String unlocalName, float axeSpeed) {
		ItemGenPickaxe pick = new ItemGenPickaxe(material, new ResourceLocation(regName + "_pickaxe"),
				creativeTab, unlocalName + "_pickaxe");
		ItemGenAxe axe = new ItemGenAxe(material, new ResourceLocation(regName + "_axe"), creativeTab,
				unlocalName + "_axe", axeSpeed);
		ItemGenShovel shovel = new ItemGenShovel(material, new ResourceLocation(regName + "_shovel"),
				creativeTab, unlocalName + "_shovel");
		ItemGenHoe hoe = new ItemGenHoe(material, new ResourceLocation(regName + "_hoe"), creativeTab,
				unlocalName + "_hoe");
		ItemGenSword sword = new ItemGenSword(material, new ResourceLocation(regName + "_sword"),
				creativeTab, unlocalName + "_sword");
		return new Item[] { pick, axe, shovel, hoe, sword };
	}

	public static class ItemGenPickaxe extends ItemPickaxe {

		private final CreativeTabs creativeTab;

		private ItemGenPickaxe(ToolMaterial material, ResourceLocation regName, CreativeTabs creativeTab,
				String unlocalName) {
			super(material);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.TOOLS, this.creativeTab };
		}

	}

	public static class ItemGenAxe extends ItemAxe {

		private final CreativeTabs creativeTab;

		private ItemGenAxe(ToolMaterial material, ResourceLocation regName, CreativeTabs creativeTab,
				String unlocalName, float axeSpeed) {
			super(material, material.getAttackDamage() + 7.0f, axeSpeed);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.TOOLS, this.creativeTab };
		}

	}

	public static class ItemGenShovel extends ItemSpade {

		private final CreativeTabs creativeTab;

		private ItemGenShovel(ToolMaterial material, ResourceLocation regName, CreativeTabs creativeTab,
				String unlocalName) {
			super(material);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.TOOLS, this.creativeTab };
		}

	}

	public static class ItemGenHoe extends ItemHoe {

		private final CreativeTabs creativeTab;

		private ItemGenHoe(ToolMaterial material, ResourceLocation regName, CreativeTabs creativeTab,
				String unlocalName) {
			super(material);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.TOOLS, this.creativeTab };
		}

	}

	public static class ItemGenSword extends ItemSword {

		private final CreativeTabs creativeTab;

		private ItemGenSword(ToolMaterial material, ResourceLocation regName, CreativeTabs creativeTab,
				String unlocalName) {
			super(material);
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
