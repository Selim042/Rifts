package selim.enderrifts.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class EnhancedEnderTeleportEvent extends EnderTeleportEvent {

	private EntityThrowable pearl;

	public EnhancedEnderTeleportEvent(EntityLivingBase entity, EntityThrowable pearl, double targetX,
			double targetY, double targetZ, float attackDamage) {
		super(entity, targetX, targetY, targetZ, attackDamage);
		this.pearl = pearl;
	}

	public EntityThrowable getPearl() {
		return this.pearl;
	}

}
