package selim.rifts.tiles;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import selim.rifts.blocks.BlockTeleporter;
import selim.rifts.blocks.BlockTeleporter.EnumFacingAxis;
import selim.rifts.items.ItemRiftLink;
import selim.rifts.misc.WorldBlockPos;

public class TileTeleporter extends TileEntityBound {

	// private NonNullList<ItemStack> contents =
	// NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
	private UUID placedBy;
	private boolean triggered;

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("placedByMost") && nbt.hasKey("placedByLeast"))
			this.placedBy = new UUID(nbt.getLong("placedByMost"), nbt.getLong("placedByLeast"));
		this.triggered = nbt.getBoolean("triggered");
		super.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (placedBy != null) {
			nbt.setLong("placedByMost", placedBy.getMostSignificantBits());
			nbt.setLong("placedByLeast", placedBy.getLeastSignificantBits());
		}
		nbt.setBoolean("triggered", this.triggered);
		return super.writeToNBT(nbt);
	}

	public boolean isTriggered() {
		return this.triggered;
	}

	public void setTriggered(boolean triggered) {
		if (this.triggered != triggered)
			this.markDirty();
		this.triggered = triggered;
	}

	public Entity getPlacer() {
		if (this.placedBy == null)
			return null;
		for (Entity e : this.world.loadedEntityList)
			if (e.getUniqueID().equals(this.placedBy))
				return e;
		return null;
	}

	public void setPlacer(Entity placer) {
		if (this.placedBy == null || !this.placedBy.equals(placer.getUniqueID()))
			this.markDirty();
		this.placedBy = placer.getUniqueID();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				|| super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		IBlockState state = this.world.getBlockState(this.pos);
		EnumFacing stateFacing = state.getValue(BlockTeleporter.FACING).getVanillaFacing();
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing.equals(stateFacing) || facing.equals(stateFacing.getOpposite()))
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.getBindingInventory());
			else
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.getFuelInventory());
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public BindingCheck getBindingCheck() {
		return new BindingCheck() {

			@Override
			public boolean isBindingValid(ItemStack stack) {
				if (stack.isEmpty() || !(stack.getItem() instanceof ItemRiftLink))
					return false;
				WorldBlockPos boundPos = ((ItemRiftLink) stack.getItem()).getBoundPos(stack);
				IBlockState boundState = boundPos.getState();
				return boundState.getBlock() instanceof BlockTeleporter;
			}
		};
	}

	public void teleport(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof TileTeleporter))
			return;
		TileTeleporter tp = (TileTeleporter) te;
		if (tp != null && tp.isTriggered() && tp.isBound()) {
			WorldBlockPos boundPosCont = tp.getBoundPos();
			if (pos.equals(boundPosCont))
				return;
			TeleporterShape shape = getShape(world, pos);
			if (shape == null || !tp.isBound())
				return;
			World boundWorld = boundPosCont.getWorld();
			TeleporterShape boundShape = getShape(boundWorld, boundPosCont);
			if (shape != null && shape.equals(boundShape) && shape.getCorners().size() >= 4
					&& boundShape.getCorners().size() >= 4) {
//				System.out.println(this.getFuelUsage(boundWorld, boundPosCont));
				// List<BlockPos> corners = shape.getCorners();
				// List<BlockPos> boundCorners = boundShape.getCorners();
				// AxisAlignedBB aabb = new AxisAlignedBB(corners.get(0),
				// corners.get(2));
				// AxisAlignedBB boundAabb = new
				// AxisAlignedBB(boundCorners.get(0), boundCorners.get(2));
				List<BlockPos> included = getIncludedPos(shape);
				List<BlockPos> boundIncluded = getIncludedPos(boundShape);
				if (included != null && boundIncluded != null) {
					for (int i = 0; i < included.size() && i < boundIncluded.size(); i++) {
						BlockPos localPos = included.get(i);
						if (world.getTileEntity(localPos) != null)
							continue;
						BlockPos boundPos = boundIncluded.get(i);
						IBlockState localState = world.getBlockState(localPos);
						IBlockState boundState = boundWorld.getBlockState(boundPos);
						Block localBlock = localState.getBlock();
						Block boundBlock = boundState.getBlock();
						if (localBlock instanceof BlockAir
								|| localBlock.isAir(localState, world, localPos)
								|| !(boundBlock instanceof BlockAir)
								|| !boundBlock.isAir(boundState, boundWorld, boundPos))
							continue;
						boundWorld.setBlockState(boundPos, localState);
						world.setBlockToAir(localPos);
					}
				}
				// for (BlockPos p : getIncludedPos(aabb)) {
				// System.out.println(p + ":" + aabb.contains(new
				// Vec3d(p.getX(), p.getY(), p.getZ())));
				// world.setBlockState(p.offset(EnumFacing.UP),
				// Blocks.GLASS.getDefaultState());
				// }
			}
		}
	}

	private List<BlockPos> getIncludedPos(TeleporterShape shape) {
		if (shape.getCorners().size() < 4)
			return null;
		List<BlockPos> corners = shape.getCorners();
		return getIncludedPos(new AxisAlignedBB(corners.get(0), corners.get(2)));
	}

	private List<BlockPos> getIncludedPos(AxisAlignedBB aabb) {
		List<BlockPos> poss = new LinkedList<BlockPos>();
		for (double x = aabb.minX; x < aabb.maxX + 1; x++)
			for (double y = aabb.minY; y < aabb.maxY + 1; y++)
				for (double z = aabb.minZ; z < aabb.maxZ + 1; z++)
					poss.add(new BlockPos(x, y, z));
		return poss;
	}

	private static final Block frameBlock = Blocks.STONEBRICK;

	private TeleporterShape getShape(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		EnumFacingAxis facingAxis = state.getValue(BlockTeleporter.FACING);
		EnumFacing.Axis axis = facingAxis.getOppositeAxis().getAxis();
		EnumFacing dir = null;
		switch (facingAxis.getAxis()) {
		case X:
			dir = EnumFacing.EAST;
			break;
		case Y:
			dir = EnumFacing.UP;
			break;
		case Z:
			dir = EnumFacing.SOUTH;
			break;
		}
		if (dir == null)
			return null;
		BlockPos curPos = pos.offset(dir);
		IBlockState curState = world.getBlockState(curPos);
		int x = -2;
		int y = -2;
		int z = -2;
		List<BlockPos> corners = new LinkedList<BlockPos>();
		while (!curState.equals(state)) {
			// world.setBlockState(curPos.offset(EnumFacing.EAST),
			// Blocks.GLASS.getDefaultState());
			if (curState.getBlock().equals(frameBlock)) {
				curPos = curPos.offset(dir);
				curState = world.getBlockState(curPos);
				switch (dir.getAxis()) {
				case X:
					x++;
					break;
				case Y:
					y++;
					break;
				case Z:
					z++;
					break;
				}
			} else {
				EnumFacing oldDir = dir.getOpposite();
				curPos = curPos.offset(dir.getOpposite());
				dir = dir.rotateAround(axis);
				curPos = curPos.offset(dir);
				curState = world.getBlockState(curPos);
				if (!curState.getBlock().equals(frameBlock)) {
					dir = dir.getOpposite();
					curPos = curPos.offset(dir, 2);
					curState = world.getBlockState(curPos);
					if (!curState.getBlock().equals(frameBlock))
						return null;
				}
				corners.add(curPos.offset(oldDir));
			}
		}
		switch (dir.getAxis()) {
		case X:
			x++;
			break;
		case Y:
			y++;
			break;
		case Z:
			z++;
			break;
		}
		return new TeleporterShape(x / 2, y / 2, z / 2, corners);
	}

	public static class TeleporterShape {

		private final int x;
		private final int y;
		private final int z;
		private final List<BlockPos> corners;

		protected TeleporterShape(int x, int y, int z, List<BlockPos> corners) {
			if (x < 0)
				this.x = 0;
			else
				this.x = x;
			if (y < 0)
				this.y = 0;
			else
				this.y = y;
			if (z < 0)
				this.z = 0;
			else
				this.z = z;
			this.corners = corners;
		}

		public int getX() {
			return this.x;
		}

		public int getY() {
			return this.y;
		}

		public int getZ() {
			return this.z;
		}

		public List<BlockPos> getCorners() {
			return this.corners;
		}

		@Override
		public String toString() {
			return this.getClass().getSimpleName() + "[x:" + this.x + ",y:" + this.y + ",z:" + this.z
					+ ",numCorn:" + this.corners.size() + "]";
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof TeleporterShape))
				return false;
			TeleporterShape f = (TeleporterShape) obj;
			return f.x == this.x && f.y == this.y && f.z == this.z;
		}
	}

}
