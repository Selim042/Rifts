package selim.enderrifts.blocks;

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
import selim.enderrifts.EnderRifts;
import selim.enderrifts.ModInfo;
import selim.enderrifts.RiftRegistry;
import selim.enderrifts.api.docs.IDocEntryLink;

public class BlockAmethystOre extends Block implements IDocEntryLink {

	private static final Random rand = new Random();

	public BlockAmethystOre() {
		super(Material.ROCK);
		this.setRegistryName(ModInfo.ID, "amethyst_ore");
		this.setUnlocalizedName(ModInfo.ID + ":amethyst_ore");
		this.setCreativeTab(EnderRifts.mainTab);
		this.setHardness(1.5f);
	}

	@Override
	public ResourceLocation getLinkedEntry() {
		return new ResourceLocation(ModInfo.ID, "amethyst");
	}

	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
		return fortune * rand.nextInt(20) + 10;
	}

	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) {
		return new ItemStack(RiftRegistry.Blocks.AMETHYST_ORE);
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos,
			IBlockState state, int fortune) {
		drops.add(new ItemStack(RiftRegistry.Items.AMETHYST,
				(fortune * rand.nextInt(3)) + (rand.nextInt(3) + 1)));
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return true;
	}

}
