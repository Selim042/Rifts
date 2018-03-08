package selim.enderrifts.api.docs;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DimensionType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

@SideOnly(Side.CLIENT)
@SuppressWarnings("deprecation")
public class DocCategory extends IForgeRegistryEntry.Impl<DocCategory>
		implements Comparable<DocCategory> {

	private static int idCounter = 0;

	private final List<DocEntry> entries = new LinkedList<DocEntry>();
	private final int id;
	private final String unlocalName;
	private int priority = 5;
	private ItemStack icon;
	private DimensionType required;

	public DocCategory(String unlocalName) {
		this.id = idCounter++;
		this.unlocalName = "rift_category." + unlocalName + ".name";
	}

	protected void addEntry(DocEntry entry) {
		this.entries.add(entry);
	}

	public List<DocEntry> getEntries() {
		List<DocEntry> copy = new LinkedList<DocEntry>(entries);
		copy.sort(null);
		return copy;
	}

	public String getUnlocalizedName() {
		return this.unlocalName;
	}

	public final String getLocalizedName() {
		return I18n.translateToLocal(this.unlocalName);
	}

	/**
	 * High number means higher priority (higher in the list). Same priority
	 * puts the categories in order in which they were registered.
	 * 
	 * "The Rift" category has a priority of 9, the specific dimensional rift
	 * pages handled in Ender Rifts have a priority of 5, as should yours.
	 */
	public DocCategory setPriority(int priority) {
		this.priority = priority;
		return this;
	}

	public int getPriority() {
		return priority;
	}

	public int getId() {
		return this.id;
	}

	public DocCategory setIcon(ItemStack icon) {
		this.icon = icon;
		return this;
	}

	public ItemStack getIcon() {
		return icon;
	}

	public DocCategory setRequiredDim(DimensionType type) {
		this.required = type;
		return this;
	}

	public DimensionType getRequriedDim() {
		return this.required;
	}

	@Override
	public int compareTo(DocCategory category) {
		return priority == category.priority ? id - category.id : category.priority - priority;
	}

}
