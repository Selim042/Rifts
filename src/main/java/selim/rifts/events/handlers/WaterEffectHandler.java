package selim.rifts.events.handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WaterEffectHandler {

	public static final String TAG = "water_upgrade";
	public static final Potion WATER_BREATHING = Potion
			.getPotionFromResourceLocation("minecraft:water_breathing");

	@SubscribeEvent
	public void onLivingTick(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		World world = entity.world;
		if (!(entity instanceof EntityPlayer))
			return;
		if (world.isRemote || entity.isSneaking())
			return;
		for (ItemStack stack : entity.getArmorInventoryList()) {
			if (stack.isEmpty() || !(stack.getItem() instanceof ItemArmor) || !isUpgraded(stack))
				continue;
			ItemArmor armor = (ItemArmor) stack.getItem();
			switch (armor.armorType) {
			case CHEST:
				break;
			case FEET:
				if (world.getBlockState(entity.getPosition().add(0, -1, 0)).getBlock()
						.equals(Blocks.WATER))
					entity.motionY += 5f;
//				System.out.println("s");
				break;
			case HEAD:
				entity.addPotionEffect(new PotionEffect(WATER_BREATHING, 200, 0, true, true));
				break;
			case LEGS:
				if (world.getBlockState(entity.getPosition()).getBlock().equals(Blocks.WATER)) {
					entity.motionX *= 2f;
					entity.motionZ *= 1.3f;
					entity.motionY *= 2f;
				}
				break;
			default:
				break;
			}
		}
	}

	public static boolean isUpgraded(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null)
			return false;
		return nbt.getBoolean(TAG);
	}

}
