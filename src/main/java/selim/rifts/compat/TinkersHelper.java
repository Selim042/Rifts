package selim.rifts.compat;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TinkersHelper {

	public static void init() {// create fluid.
		// You don't need to add textures for the fluid, just create a Fluid
		// Class that overwrites getColor
		// and pass the following as still and flowing ResourceLocation:
		// still: "tconstruct:blocks/fluids/molten_metal"
		// flowing: "tconstruct:blocks/fluids/molten_metal_flow"
		Fluid moltenAdmanite = new Fluid("molten_admanite",
				new ResourceLocation("rifts:blocks/fluids/molten_adamanite"),
				new ResourceLocation("rifts:blocks/fluids/molten_adamanite_flow"));
		FluidRegistry.registerFluid(moltenAdmanite); // fluid has to be registered
		FluidRegistry.addBucketForFluid(moltenAdmanite); // add a bucket for the fluid

		// add block for fluid (if desired)
//		Block fluidBlock = new BlockFluidClassic(moltenAdmanite, Material.lava);
		// <register block regularly>

		// create NBT for the IMC
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("fluid", moltenAdmanite.getName()); // name of the fluid
		tag.setString("ore", "RiftAdamanite"); // ore-suffix: ingotFoo, blockFoo,
										// oreFoo,...
		tag.setBoolean("toolforge", true); // if set to true, blockFoo can be
											// used to build a toolforge
		// tag.setTag("alloy", alloysTagList); // you can also send an alloy
		// with the registration (see below)

		// send the NBT to TCon
		FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tag);
	}

}
