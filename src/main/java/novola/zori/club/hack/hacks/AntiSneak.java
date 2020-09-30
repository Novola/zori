package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;

import novola.zori.club.utils.Utils;
import novola.zori.club.utils.system.Connection.Side;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.system.Connection;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.wrappers.Wrapper;

public class AntiSneak extends Hack{
	
	public BooleanValue fullSprint;
	public AntiSneak() {
		super("AntiSneak", HackCategory.PLAYER);
		fullSprint = new BooleanValue("FullSprint", true);
		this.addValue(fullSprint);
	}
	
	@Override
	public String getDescription() {
		return "Does not change walking speed when sneak.";
	}
	
	@Override
	public boolean onPacket(Object packet, Connection.Side side) {
		if(side == Connection.Side.OUT && packet instanceof CPacketEntityAction) {
			CPacketEntityAction p = (CPacketEntityAction) packet;
			if(p.getAction() == CPacketEntityAction.Action.START_SNEAKING) {
				return false;
			}
		}
		return true;
	}
	@Override
	public void onClientTick(ClientTickEvent event) {
		EntityPlayerSP player = Wrapper.INSTANCE.player();
		GameSettings settings = Wrapper.INSTANCE.mcSettings();
		if(player.onGround && settings.keyBindSneak.isKeyDown()) {
			if(!fullSprint.getValue() && settings.keyBindForward.isKeyDown()) {
				player.setSprinting(Utils.isMoving(player));
			} 
			else if(fullSprint.getValue()) {
				player.setSprinting(Utils.isMoving(player));
			}
			if(settings.keyBindRight.isKeyDown() 
					|| settings.keyBindLeft.isKeyDown()
					|| settings.keyBindBack.isKeyDown()) {
				if(settings.keyBindBack.isKeyDown()) {
					player.motionX *= 1.268;
					player.motionZ *= 1.268;
				} else {
					player.motionX *= 1.252;
					player.motionZ *= 1.252;
				}
			} else {
				player.motionX *= 1.2848;
				player.motionZ *= 1.2848;
			}
		}
		super.onClientTick(event);
	}
}
