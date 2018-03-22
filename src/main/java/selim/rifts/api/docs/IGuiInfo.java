package selim.rifts.api.docs;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//@SideOnly(Side.CLIENT)
public interface IGuiInfo {

	public int getGuiEdge();

	public int getGuiTop();

	public int getWidth();

	public int getHeight();

}
