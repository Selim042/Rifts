package selim.rifts.api.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityTeleportHandler {

	private static boolean registered = false;

	@CapabilityInject(ITeleportHandler.class)
	public static Capability<ITeleportHandler> TELEPORT_HANDLER_CAPABILITY = null;

	public static void register() {
		if (registered)
			return;
		CapabilityManager.INSTANCE.register(ITeleportHandler.class,
				new Capability.IStorage<ITeleportHandler>() {

					@Override
					public NBTBase writeNBT(Capability<ITeleportHandler> capability,
							ITeleportHandler instance, EnumFacing side) {
						return null;
					}

					@Override
					public void readNBT(Capability<ITeleportHandler> capability,
							ITeleportHandler instance, EnumFacing side, NBTBase base) {}
				}, TeleporterHandler::new);
	}

}
