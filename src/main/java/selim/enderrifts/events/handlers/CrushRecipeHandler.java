package selim.enderrifts.events.handlers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import selim.enderrifts.RiftRegistry;
import selim.enderrifts.crafting.CrushRecipe;

public class CrushRecipeHandler {

	@SubscribeEvent
	public void onAnvilLand(NeighborNotifyEvent event) {
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		BlockPos lowerPos = pos.add(0, -1, 0);
		IBlockState lowerState = world.getBlockState(pos.add(0, -1, 0));
		if (event.getState().getBlock() instanceof BlockAnvil
				&& !lowerState.getBlock().isAir(lowerState, world, lowerPos)) {
			List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class,
					new AxisAlignedBB(pos));
			HashMap<CrushRecipe, EntityItem> toCraft = new HashMap<CrushRecipe, EntityItem>();
			for (EntityItem item : items)
				for (CrushRecipe e : RiftRegistry.Registries.CRUSH_RECIPES.getValuesCollection())
					if (e.isInputValid(item.getItem()))
						toCraft.put(e, item);
			List<ItemStack> toSpawn = new LinkedList<ItemStack>();
			for (Entry<CrushRecipe, EntityItem> e : toCraft.entrySet()) {
				CrushRecipe recipe = e.getKey();
				ItemStack stack = e.getValue().getItem();
				ItemStack result = e.getKey().getResult().copy();
				result.setCount(recipe.getCrafted(stack));
				toSpawn.add(result);
				stack.shrink(recipe.getConsumed(stack));
				if (!stack.isEmpty())
					toSpawn.add(stack);
				e.getValue().setDead();
			}
			for (ItemStack s : toSpawn)
				world.spawnEntity(
						new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5, s));
		}
	}

}
