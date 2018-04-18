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
import selim.rifts.api.docs.IDocEntryLink;

public class ToolUtils {

	public static Item[] genToolset(ToolMaterial material, CreativeTabs creativeTab,
			ResourceLocation regName, String unlocalName, float axeSpeed) {
		return genToolset(material, creativeTab, regName, unlocalName, axeSpeed, null);
	}

	public static Item[] genToolset(ToolMaterial material, CreativeTabs creativeTab,
			ResourceLocation regName, String unlocalName, float axeSpeed, ResourceLocation docLink) {
		ItemGenPickaxe pick = new ItemGenPickaxe(material, new ResourceLocation(regName + "_pickaxe"),
				creativeTab, unlocalName + "_pickaxe", docLink);
		ItemGenAxe axe = new ItemGenAxe(material, new ResourceLocation(regName + "_axe"), creativeTab,
				unlocalName + "_axe", axeSpeed, docLink);
		ItemGenShovel shovel = new ItemGenShovel(material, new ResourceLocation(regName + "_shovel"),
				creativeTab, unlocalName + "_shovel", docLink);
		ItemGenHoe hoe = new ItemGenHoe(material, new ResourceLocation(regName + "_hoe"), creativeTab,
				unlocalName + "_hoe", docLink);
		ItemGenSword sword = new ItemGenSword(material, new ResourceLocation(regName + "_sword"),
				creativeTab, unlocalName + "_sword", docLink);
		return new Item[] { pick, axe, shovel, hoe, sword };
	}

	public static class ItemGenPickaxe extends ItemPickaxe implements IDocEntryLink {

		private final CreativeTabs creativeTab;
		private final ResourceLocation docLink;

		private ItemGenPickaxe(ToolMaterial material, ResourceLocation regName, CreativeTabs creativeTab,
				String unlocalName, ResourceLocation docLink) {
			super(material);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
			this.docLink = docLink;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.TOOLS, this.creativeTab };
		}

		@Override
		public ResourceLocation getLinkedEntry() {
			return docLink;
		}

	}

	public static class ItemGenAxe extends ItemAxe implements IDocEntryLink {

		private final CreativeTabs creativeTab;
		private final ResourceLocation docLink;

		private ItemGenAxe(ToolMaterial material, ResourceLocation regName, CreativeTabs creativeTab,
				String unlocalName, float axeSpeed, ResourceLocation docLink) {
			super(material, material.getAttackDamage() + 7.0f, axeSpeed);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
			this.docLink = docLink;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.TOOLS, this.creativeTab };
		}

		@Override
		public ResourceLocation getLinkedEntry() {
			return docLink;
		}

	}

	public static class ItemGenShovel extends ItemSpade implements IDocEntryLink {

		private final CreativeTabs creativeTab;
		private final ResourceLocation docLink;

		private ItemGenShovel(ToolMaterial material, ResourceLocation regName, CreativeTabs creativeTab,
				String unlocalName, ResourceLocation docLink) {
			super(material);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
			this.docLink = docLink;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.TOOLS, this.creativeTab };
		}

		@Override
		public ResourceLocation getLinkedEntry() {
			return docLink;
		}

	}

	public static class ItemGenHoe extends ItemHoe implements IDocEntryLink {

		private final CreativeTabs creativeTab;
		private final ResourceLocation docLink;

		private ItemGenHoe(ToolMaterial material, ResourceLocation regName, CreativeTabs creativeTab,
				String unlocalName, ResourceLocation docLink) {
			super(material);
			this.setRegistryName(regName);
			this.setUnlocalizedName(unlocalName);
			this.creativeTab = creativeTab;
			this.docLink = docLink;
		}

		@Override
		public CreativeTabs[] getCreativeTabs() {
			return new CreativeTabs[] { CreativeTabs.TOOLS, this.creativeTab };
		}

		@Override
		public ResourceLocation getLinkedEntry() {
			return docLink;
		}

	}

	public static class ItemGenSword extends ItemSword implements IDocEntryLink {

		private final CreativeTabs creativeTab;
		private final ResourceLocation docLink;

		private ItemGenSword(ToolMaterial material, ResourceLocation regName, CreativeTabs creativeTab,
				String unlocalName, ResourceLocation docLink) {
			super(material);
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
			return docLink;
		}

	}

}
