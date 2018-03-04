package selim.enderrifts.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;

public class BlockAmethystBlock extends BlockNegativeLight {

	private static final int RANGE = 5;
	private static final int OPACITY = 2;

	public BlockAmethystBlock() {
		super(Material.ROCK, OPACITY, RANGE);
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "amethyst_block"));
		this.setUnlocalizedName(ModInfo.ID + ":amethyst_block");
		this.setCreativeTab(EnderRifts.mainTab);
		this.setHardness(1.5f);
		this.setTickRandomly(true);
		this.lightValue = 1;
	}

}
