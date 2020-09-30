package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;

import novola.zori.club.utils.TimerUtils;
import novola.zori.club.utils.system.Connection.Side;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.value.Mode;
import novola.zori.club.value.ModeValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketUseEntity.Action;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.wrappers.Wrapper;

public class Criticals extends Hack{

	public ModeValue mode;
	TimerUtils timer;
	boolean cancelSomePackets;
	
	public Criticals() {
		super("Criticals", HackCategory.COMBAT);
		this.mode = new ModeValue("Mode", new Mode("Packet", true), new Mode("Jump", false));
		
		this.addValue(mode);
		
		this.timer = new TimerUtils();
	}
	
	@Override
	public String getDescription() {
		return "Changes all your hits to critical hits.";
	}
	
	@Override
	public boolean onPacket(Object packet, Side side) {
		if(Wrapper.INSTANCE.player().onGround) {
			if(side == Side.OUT) {
				if(packet instanceof CPacketUseEntity) {
					CPacketUseEntity attack = (CPacketUseEntity) packet;
					if(attack.getAction() == Action.ATTACK) {
						if(mode.getMode("Packet").isToggled()) {
							if(Wrapper.INSTANCE.player().collidedVertically && this.timer.isDelay(500)) {
								Wrapper.INSTANCE.sendPacket(new CPacketPlayer.Position(
										Wrapper.INSTANCE.player().posX,
										Wrapper.INSTANCE.player().posY + 0.0627,
										Wrapper.INSTANCE.player().posZ, false));
								Wrapper.INSTANCE.sendPacket(new CPacketPlayer.Position(
										Wrapper.INSTANCE.player().posX,
										Wrapper.INSTANCE.player().posY,
										Wrapper.INSTANCE.player().posZ, false));
								Entity entity = attack.getEntityFromWorld(Wrapper.INSTANCE.world());
								if(entity != null) {
									Wrapper.INSTANCE.player().onCriticalHit(entity);
								}
								this.timer.setLastMS();
								this.cancelSomePackets = true;
							}
						}
						else if(mode.getMode("Jump").isToggled())
						{
							if(canJump()) {
								Wrapper.INSTANCE.player().jump();
							}
						}
					}
				} else if (mode.getMode("Packet").isToggled() && packet instanceof CPacketPlayer) {
	                if (cancelSomePackets) {
	                    cancelSomePackets = false;
	                    return false;
	                }
	            }
			}
		}
		return true;
	}
	
	boolean canJump() {
		if(Wrapper.INSTANCE.player().isOnLadder()) {
			return false;
		}
		if(Wrapper.INSTANCE.player().isInWater()) {
			return false;
		}
		if(Wrapper.INSTANCE.player().isInLava()) {
			return false;
		}
		if(Wrapper.INSTANCE.player().isSneaking()) {
			return false;
		}
		if(Wrapper.INSTANCE.player().isRiding()) {
			return false;
		}
		if(Wrapper.INSTANCE.player().isPotionActive(MobEffects.BLINDNESS)) {
			return false;
		}
        return true;
    }
}
