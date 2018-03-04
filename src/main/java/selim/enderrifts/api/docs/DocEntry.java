package selim.enderrifts.api.docs;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.registries.IForgeRegistryEntry;

@SuppressWarnings("deprecation")
public class DocEntry extends IForgeRegistryEntry.Impl<DocEntry> implements Comparable<DocEntry> {

	private final DocCategory category;
	private final String unlocalName;
	private int priority = 5;
	private ItemStack icon;
	private final List<DocPage> pages = new LinkedList<DocPage>();

	public DocEntry(DocCategory category, String unlocalName) {
		this.category = category;
		if (category == null)
			throw new IllegalArgumentException("category must not be null");
		this.category.addEntry(this);
		this.unlocalName = "rift_entry." + unlocalName + ".name";
	}

	public DocCategory getCategory() {
		return this.category;
	}

	/**
	 * High number means higher priority (higher in the list). Same priority
	 * puts the categories in order in which they were registered
	 */
	public DocEntry setPriority(int priority) {
		this.priority = priority;
		return this;
	}

	public int getPriority() {
		return priority;
	}

	public final String getLocalizedName() {
		return I18n.translateToLocal(this.unlocalName);
	}

	public DocEntry setIcon(ItemStack icon) {
		this.icon = icon;
		return this;
	}

	public ItemStack getIcon() {
		return icon;
	}

	public List<DocPage> getPages() {
		return new LinkedList<DocPage>(this.pages);
	}

	public DocEntry addPage(DocPage page) {
		this.pages.add(page);
		return this;
	}

	public DocEntry addPages(DocPage...pages) {
		for (DocPage page : pages)
			this.pages.add(page);
		return this;
	}

	@Override
	public int compareTo(DocEntry entry) {
		return priority == entry.priority ? getLocalizedName().compareTo(entry.getLocalizedName())
				: entry.priority - priority;
	}

}
