package selim.rifts.blocks;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import selim.rifts.EnderRifts;
import selim.rifts.ModInfo;

public class BlockWillowPressurePlate extends BlockPressurePlate {

	public BlockWillowPressurePlate() {
		super(Material.WOOD, Sensitivity.EVERYTHING);
		this.setRegistryName(ModInfo.ID, "willow_pressure_plate");
		this.setUnlocalizedName(ModInfo.ID + ":willow_pressure_plate");
		this.setCreativeTab(EnderRifts.mainTab);
	}

}
