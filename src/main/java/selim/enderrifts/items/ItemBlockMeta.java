package selim.enderrifts.items;

import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class ItemBlockMeta extends ItemBlock {

	public ItemBlockMeta(Block block) {
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		String unlocal = this.block.getUnlocalizedName();
		@SuppressWarnings("deprecation")
		IBlockState state = this.block.getStateFromMeta(stack.getMetadata());
		for (Entry<IProperty<?>, Comparable<?>> e : state.getProperties().entrySet()) {
			Comparable<?> val = e.getValue();
			if (val instanceof IStringSerializable)
				unlocal += "_" + ((IStringSerializable) val).getName();
			else
				unlocal += "_" + val.toString();
		}
		return unlocal;
	}

}
