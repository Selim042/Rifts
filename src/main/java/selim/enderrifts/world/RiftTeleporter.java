package selim.enderrifts.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class RiftTeleporter extends Teleporter {

	public RiftTeleporter(WorldServer world) {
		super(world);
	}

	@Override
	public void placeInPortal(Entity entity, float rotationYaw) {}

}
