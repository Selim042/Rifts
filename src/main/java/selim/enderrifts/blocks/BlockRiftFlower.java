package selim.enderrifts.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;

public class BlockRiftFlower extends BlockBush {

	public BlockRiftFlower() {
		super(Material.PLANTS);
		this.setRegistryName(ModInfo.ID, "rift_flower");
		this.setUnlocalizedName(ModInfo.ID + ":rift_flower");
		this.setCreativeTab(EnderRifts.mainTab);
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return false;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Plains;
	}

	@Override
	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.XZ;
	}

}
