package selim.enderrifts.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;
import selim.enderrifts.api.docs.IDocEntryLink;

public class BlockAdamaniteOre extends Block implements IDocEntryLink {

	public BlockAdamaniteOre() {
		super(Material.ROCK);
		this.setRegistryName(ModInfo.ID, "adamanite_ore");
		this.setUnlocalizedName(ModInfo.ID + ":adamanite_ore");
		this.setCreativeTab(EnderRifts.mainTab);
		this.setHardness(3.5f);
		this.setHarvestLevel("pickaxe", 3);
	}

	@Override
	public ResourceLocation getLinkedEntry() {
		return new ResourceLocation(ModInfo.ID, "adamanite");
	}

}
