package selim.enderrifts.api.docs;

import net.minecraft.util.ResourceLocation;

/***
 * Use on a Block or Item class to link it to a DocEntry.
 */
public interface IDocEntryLink {

	public default ResourceLocation getLinkedEntry() {
		return null;
	}

}
