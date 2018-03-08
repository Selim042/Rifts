package selim.enderrifts.events.handlers;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import selim.enderrifts.RiftRegistry;
import selim.enderrifts.events.EnhancedEnderTeleportEvent;

public class EnderTeleport {

	private static final Random rand = new Random();

	@SubscribeEvent
	public void onEnderTeleport(EnderTeleportEvent event) {
		if (!(event instanceof EnhancedEnderTeleportEvent) && event.getEntity() instanceof EntityPlayer
				&& rand.nextInt(2) == 1) {
			Entity entity = event.getEntity();
			World world = entity.getEntityWorld();
			world.setBlockState(new BlockPos(event.getTargetX(), event.getTargetY(), event.getTargetZ()),
					RiftRegistry.Blocks.RIFT_TEST.getDefaultState());
		}
	}

}
