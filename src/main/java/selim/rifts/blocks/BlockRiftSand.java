package selim.rifts.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;

public class BlockRiftSand extends BlockReverseFalling {

	public BlockRiftSand() {
		super(Material.SAND);
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "rift_sand"));
		this.setUnlocalizedName(ModInfo.ID + ":rift_sand");
		this.setCreativeTab(EnderRifts.mainTab);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getDustColor(IBlockState state) {
		return MathHelper.rgb((float) ModInfo.PURPLE_COLOR.x, (float) ModInfo.PURPLE_COLOR.y,
				(float) ModInfo.PURPLE_COLOR.z);
	}

}
