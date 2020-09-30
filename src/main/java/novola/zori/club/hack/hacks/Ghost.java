package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;

import novola.zori.club.utils.system.Connection;
import novola.zori.club.utils.system.Connection.Side;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.system.Connection;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.wrappers.Wrapper;

public class Ghost extends Hack{
	
	public BooleanValue noWalls;
	
	public Ghost() {
		super("Ghost", HackCategory.PLAYER);
		
		noWalls = new BooleanValue("NoWalls", true);
		
		this.addValue(noWalls);
	}
	
	@Override
	public String getDescription() {
		return "Allows you to pass through walls.";
	}
	
	@Override
	public boolean onPacket(Object packet, Connection.Side side) {
		boolean skip = true;
		if(noWalls.getValue()) {
			if(side == Connection.Side.OUT && packet instanceof CPacketPlayer) {
				skip = false;
			}
		}
		return skip;
	}
	
	@Override
	public void onDisable() {
		Wrapper.INSTANCE.player().noClip = false;
		
		if(noWalls.getValue()) {
		Wrapper.INSTANCE.sendPacket(new CPacketPlayer.PositionRotation(
				Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().getEntityBoundingBox().minY,
				Wrapper.INSTANCE.player().posZ, Wrapper.INSTANCE.player().cameraYaw, Wrapper.INSTANCE.player().cameraPitch,
				Wrapper.INSTANCE.player().onGround));
		}
		
		super.onDisable();
	}

	@Override
	public void onLivingUpdate(LivingUpdateEvent event) {
		Wrapper.INSTANCE.player().noClip = true;
		Wrapper.INSTANCE.player().fallDistance = 0;
		Wrapper.INSTANCE.player().onGround = true;
		
		Wrapper.INSTANCE.player().capabilities.isFlying = false;
		Wrapper.INSTANCE.player().motionX = 0;
		Wrapper.INSTANCE.player().motionY = 0;
		Wrapper.INSTANCE.player().motionZ = 0;
		
		float speed = 0.2f;
		Wrapper.INSTANCE.player().jumpMovementFactor = speed;
		if(Wrapper.INSTANCE.mcSettings().keyBindJump.isKeyDown()) {
			Wrapper.INSTANCE.player().motionY += speed;
		}
		if(Wrapper.INSTANCE.mcSettings().keyBindSneak.isKeyDown()) {
			Wrapper.INSTANCE.player().motionY -= speed;
		}
		
		super.onLivingUpdate(event);
	}
}
