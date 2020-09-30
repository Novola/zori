package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.Utils;
import novola.zori.club.value.Mode;
import novola.zori.club.value.ModeValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.utils.system.Connection.Side;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.wrappers.Wrapper;

public class SelfKick extends Hack{
	
	public SelfKick() {
		super("SelfKick", HackCategory.ANOTHER);
	}
	
	@Override
	public String getDescription() {
		return "Kick you from Server.";
	}
	
	@Override
	public void onEnable() {
		Wrapper.INSTANCE.sendPacket(new CPacketPlayer.Rotation(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, false));
		super.onEnable();
	}
}
