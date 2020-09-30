package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;

import novola.zori.club.utils.RobotUtils;
import novola.zori.club.utils.TimerUtils;
import novola.zori.club.utils.Utils;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.RobotUtils;
import novola.zori.club.utils.TimerUtils;
import novola.zori.club.utils.Utils;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;

public class FireballReturn extends Hack{
	
    public NumberValue yaw;
    public NumberValue pitch;
    public NumberValue range;
    
	public EntityFireball target;
	public TimerUtils timer;
	
	public FireballReturn() {
		super("FireballReturn", HackCategory.COMBAT);
		yaw = new NumberValue("Yaw", 25.0D, 0D, 50D);
		pitch = new NumberValue("Pitch", 25.0D, 0D, 50D);
		range = new NumberValue("Range", 10.0D, 0.1D, 10D);
		
		this.addValue(yaw, pitch, range);
		
		this.timer = new TimerUtils();
	}
	
	@Override
	public String getDescription() {
		return "Beats fireballs when they fly at you.";
	}

	@Override
	public void onClientTick(ClientTickEvent event) {
		updateTarget();
		attackTarget();
		super.onClientTick(event);
	}

	void updateTarget(){
		for (Object object : Utils.getEntityList()) {
			if(object instanceof EntityFireball) {
				EntityFireball entity = (EntityFireball) object;
				if(isInAttackRange(entity) && !entity.isDead && !entity.onGround && entity.canBeAttackedWithItem()) {
					target = entity;
				}
			}
		}
	}
	
	void attackTarget() {
		if(target == null) {
			return;
		}
		Utils.assistFaceEntity(this.target, this.yaw.getValue().floatValue(), this.pitch.getValue().floatValue());
		int currentCPS = Utils.random(4, 7);
		if(timer.isDelay(1000 / currentCPS)) {
			RobotUtils.clickMouse(0);
			timer.setLastMS();
			target = null;
		}
	}
	
	public boolean isInAttackRange(EntityFireball entity) {
        return entity.getDistance(Wrapper.INSTANCE.player()) <= this.range.getValue().floatValue();
    }
	
}
