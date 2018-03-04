package selim.enderrifts.fuild;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidFinite;
import selim.enderrifts.ModInfo;
import selim.enderrifts.RiftsRegistry;

public class BlockFluidMatter extends BlockFluidFinite {

	public BlockFluidMatter() {
		super(RiftsRegistry.Fluids.MATTER, new MaterialLiquid(MapColor.PURPLE));
		this.setRegistryName(new ResourceLocation(ModInfo.ID, "matter"));
	}

}
