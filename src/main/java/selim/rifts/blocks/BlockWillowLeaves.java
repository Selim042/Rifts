package selim.rifts.blocks;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import selim.rifts.ModInfo;
import selim.rifts.RiftRegistry;

public class BlockWillowLeaves extends BlockLeaves {

	public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
	public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
	// protected boolean leavesFancy = true;
	// int[] surroundings;

	public BlockWillowLeaves() {
		// super(Material.LEAVES);
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "willow_leaves"));
		this.setUnlocalizedName(ModInfo.ID + ":willow_leaves");
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setHardness(0.2F);
		this.setLightOpacity(1);
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX,
			float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getStateFromMeta(meta).withProperty(CHECK_DECAY, false).withProperty(DECAYABLE,
				false);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		switch (meta) {
		case 0:
			return this.getDefaultState().withProperty(CHECK_DECAY, false).withProperty(DECAYABLE,
					false);
		case 1:
			return this.getDefaultState().withProperty(CHECK_DECAY, false).withProperty(DECAYABLE, true);
		case 2:
			return this.getDefaultState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, false);
		default:
			return this.getDefaultState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true);
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		if (state.getValue(DECAYABLE).booleanValue())
			meta += 1;
		if (state.getValue(CHECK_DECAY).booleanValue())
			meta += 2;
		return meta;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { CHECK_DECAY, DECAYABLE });
	}

	// // Start copy from BlockLeaves
	// @Override
	// public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
	// int i = 1;
	// int j = 2;
	// int k = pos.getX();
	// int l = pos.getY();
	// int i1 = pos.getZ();
	//
	// if (worldIn.isAreaLoaded(new BlockPos(k - 2, l - 2, i1 - 2),
	// new BlockPos(k + 2, l + 2, i1 + 2))) {
	// for (int j1 = -1; j1 <= 1; ++j1) {
	// for (int k1 = -1; k1 <= 1; ++k1) {
	// for (int l1 = -1; l1 <= 1; ++l1) {
	// BlockPos blockpos = pos.add(j1, k1, l1);
	// IBlockState iblockstate = worldIn.getBlockState(blockpos);
	//
	// if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos)) {
	// iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
	// }
	// }
	// }
	// }
	// }
	// }
	//
	// @Override
	// public void updateTick(World worldIn, BlockPos pos, IBlockState state,
	// Random rand) {
	// if (!worldIn.isRemote) {
	// if (((Boolean) state.getValue(CHECK_DECAY)).booleanValue()
	// && ((Boolean) state.getValue(DECAYABLE)).booleanValue()) {
	// int i = 4;
	// int j = 5;
	// int k = pos.getX();
	// int l = pos.getY();
	// int i1 = pos.getZ();
	// int j1 = 32;
	// int k1 = 1024;
	// int l1 = 16;
	//
	// if (this.surroundings == null) {
	// this.surroundings = new int[32768];
	// }
	//
	// if (worldIn.isAreaLoaded(new BlockPos(k - 5, l - 5, i1 - 5),
	// new BlockPos(k + 5, l + 5, i1 + 5))) {
	// BlockPos.MutableBlockPos blockpos$mutableblockpos = new
	// BlockPos.MutableBlockPos();
	//
	// for (int i2 = -4; i2 <= 4; ++i2) {
	// for (int j2 = -4; j2 <= 4; ++j2) {
	// for (int k2 = -4; k2 <= 4; ++k2) {
	// IBlockState iblockstate = worldIn.getBlockState(
	// blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2));
	// Block block = iblockstate.getBlock();
	//
	// if (!block.canSustainLeaves(iblockstate, worldIn,
	// blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2))) {
	// if (block.isLeaves(iblockstate, worldIn,
	// blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2))) {
	// this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2
	// + 16] = -2;
	// } else {
	// this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2
	// + 16] = -1;
	// }
	// } else {
	// this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = 0;
	// }
	// }
	// }
	// }
	//
	// for (int i3 = 1; i3 <= 4; ++i3) {
	// for (int j3 = -4; j3 <= 4; ++j3) {
	// for (int k3 = -4; k3 <= 4; ++k3) {
	// for (int l3 = -4; l3 <= 4; ++l3) {
	// if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3
	// + 16] == i3 - 1) {
	// if (this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3
	// + 16] == -2) {
	// this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3
	// + 16] = i3;
	// }
	//
	// if (this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3
	// + 16] == -2) {
	// this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3
	// + 16] = i3;
	// }
	//
	// if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3
	// + 16] == -2) {
	// this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3
	// + 16] = i3;
	// }
	//
	// if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3
	// + 16] == -2) {
	// this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3
	// + 16] = i3;
	// }
	//
	// if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32
	// + (l3 + 16 - 1)] == -2) {
	// this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32
	// + (l3 + 16 - 1)] = i3;
	// }
	//
	// if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16
	// + 1] == -2) {
	// this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16
	// + 1] = i3;
	// }
	// }
	// }
	// }
	// }
	// }
	// }
	//
	// int l2 = this.surroundings[16912];
	//
	// if (l2 >= 0) {
	// worldIn.setBlockState(pos, state.withProperty(CHECK_DECAY,
	// Boolean.valueOf(false)),
	// 4);
	// } else {
	// this.destroy(worldIn, pos);
	// }
	// }
	// }
	// }
	//
	// private void destroy(World worldIn, BlockPos pos) {
	// this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
	// worldIn.setBlockToAir(pos);
	// }
	//
	// @SideOnly(Side.CLIENT)
	// @Override
	// public void randomDisplayTick(IBlockState stateIn, World worldIn,
	// BlockPos pos, Random rand) {
	// if (worldIn.isRainingAt(pos.up()) &&
	// !worldIn.getBlockState(pos.down()).isTopSolid()
	// && rand.nextInt(15) == 1) {
	// double d0 = (double) ((float) pos.getX() + rand.nextFloat());
	// double d1 = (double) pos.getY() - 0.05D;
	// double d2 = (double) ((float) pos.getZ() + rand.nextFloat());
	// worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D,
	// 0.0D, 0.0D);
	// }
	// }
	//
	// @Override
	// public int quantityDropped(Random random) {
	// return random.nextInt(20) == 0 ? 1 : 0;
	// }

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return RiftRegistry.Items.WILLOW_SAPLING;
	}

	// @Override
	// public void dropBlockAsItemWithChance(World worldIn, BlockPos pos,
	// IBlockState state, float chance,
	// int fortune) {
	// super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
	// }
	//
	// protected int getSaplingDropChance(IBlockState state) {
	// return 20;
	// }

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return !Minecraft.getMinecraft().gameSettings.fancyGraphics;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return Minecraft.getMinecraft().gameSettings.fancyGraphics ? BlockRenderLayer.CUTOUT_MIPPED
				: BlockRenderLayer.SOLID;
	}

	// @Override
	// public boolean causesSuffocation(IBlockState state) {
	// return false;
	// }
	//
	// // Removed wood type method
	//
	// @Override
	// public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos
	// pos) {
	// return true;
	// }
	//
	// @Override
	// public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos
	// pos) {
	// return true;
	// }
	//
	// @Override
	// public void beginLeavesDecay(IBlockState state, World world, BlockPos
	// pos) {
	// if (!(Boolean) state.getValue(CHECK_DECAY)) {
	// world.setBlockState(pos, state.withProperty(CHECK_DECAY, true), 4);
	// }
	// }
	//
	// @Override
	// public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops,
	// IBlockAccess world,
	// BlockPos pos, IBlockState state, int fortune) {
	// Random rand = world instanceof World ? ((World) world).rand : new
	// Random();
	// int chance = this.getSaplingDropChance(state);
	//
	// if (fortune > 0) {
	// chance -= 2 << fortune;
	// if (chance < 10)
	// chance = 10;
	// }
	//
	// if (rand.nextInt(chance) == 0) {
	// ItemStack drop = new ItemStack(getItemDropped(state, rand, fortune), 1,
	// damageDropped(state));
	// if (!drop.isEmpty())
	// drops.add(drop);
	// }
	//
	// chance = 200;
	// if (fortune > 0) {
	// chance -= 10 << fortune;
	// if (chance < 40)
	// chance = 40;
	// }
	//
	// this.captureDrops(true);
	// // if (world instanceof World)
	// // this.dropApple((World) world, pos, state, chance); // Dammet mojang
	// drops.addAll(this.captureDrops(false));
	// }

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		return true;
	}
	// // End copy from BlockLeaves

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return Collections.singletonList(new ItemStack(this));
	}

	@Override
	public EnumType getWoodType(int meta) {
		return EnumType.BIRCH;
	}

}
