package selim.rifts.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;
import selim.rifts.RiftRegistry;
import selim.rifts.api.docs.IDocEntryLink;

public class BlockOpalOre extends Block implements IDocEntryLink {

	private static final Random rand = new Random();

	public BlockOpalOre() {
		super(Material.ROCK);
		this.setRegistryName(ModInfo.ID, "opal_ore");
		this.setUnlocalizedName(ModInfo.ID + ":opal_ore");
		this.setCreativeTab(EnderRifts.mainTab);
		this.setHardness(1.5f);
	}

	@Override
	public ResourceLocation getLinkedEntry() {
		return new ResourceLocation(ModInfo.ID, "opal");
	}

	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
		return fortune * rand.nextInt(20) + 10;
	}

	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) {
		return new ItemStack(RiftRegistry.Blocks.OPAL_ORE);
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos,
			IBlockState state, int fortune) {
		drops.add(new ItemStack(RiftRegistry.Items.OPAL,
				(fortune * rand.nextInt(3)) + (rand.nextInt(3) + 1)));
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return true;
	}

}
