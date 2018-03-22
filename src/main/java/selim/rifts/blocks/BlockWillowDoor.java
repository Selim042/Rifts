package selim.rifts.blocks;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.RiftRegistry;

public class BlockWillowDoor extends BlockDoor {

	public BlockWillowDoor() {
		super(Material.WOOD);
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "willow_door"));
		this.setUnlocalizedName(ModInfo.ID + ":willow_door");
		this.setCreativeTab(EnderRifts.mainTab);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(RiftRegistry.Items.WILLOW_DOOR);
	}

}
