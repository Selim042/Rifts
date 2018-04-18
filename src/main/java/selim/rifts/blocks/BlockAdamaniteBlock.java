package selim.rifts.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.api.docs.IDocEntryLink;

public class BlockAdamaniteBlock extends Block implements IDocEntryLink {

	public BlockAdamaniteBlock() {
		super(Material.IRON);
		this.setRegistryName(ModInfo.ID, "adamanite_block");
		this.setUnlocalizedName(ModInfo.ID + ":adamanite_block");
		this.setCreativeTab(EnderRifts.mainTab);
		this.setHardness(3.5f);
		this.setHarvestLevel("pickaxe", 3);
	}

	@Override
	public ResourceLocation getLinkedEntry() {
		return new ResourceLocation(ModInfo.ID, "adamanite");
	}

}
