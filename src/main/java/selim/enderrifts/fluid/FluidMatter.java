package selim.enderrifts.fluid;

import java.awt.Color;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import selim.enderrifts.ModInfo;

public class FluidMatter extends Fluid {

	public FluidMatter() {
		super(ModInfo.ID + ":matter", new ResourceLocation("minecraft:water_still"),
				new ResourceLocation("minecraft:water_flow"),
				new Color((int) (ModInfo.PURPLE_COLOR.x * 255), (int) (ModInfo.PURPLE_COLOR.y * 255),
						(int) (ModInfo.PURPLE_COLOR.z * 255)));
	}

	@Override
	public boolean doesVaporize(FluidStack fluidStack) {
		return false;
	}

	@Override
	public int getDensity(FluidStack stack) {
		return 2;
	}

	@Override
	public int getDensity(World world, BlockPos pos) {
		return 2;
	}

	@Override
	public int getViscosity(World world, BlockPos pos) {
		return 5;
	}

}
