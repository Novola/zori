package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.RobotUtils;
import novola.zori.club.utils.TimerUtils;
import novola.zori.club.utils.Utils;
import novola.zori.club.value.Mode;
import novola.zori.club.value.ModeValue;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.utils.system.Connection.Side;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.wrappers.Wrapper;

public class Rage extends Hack{
	
	public TimerUtils timer;
	
	public NumberValue delay;
	
	public Rage() {
		super("Rage", HackCategory.PLAYER);
		
		this.timer = new TimerUtils();
		delay = new NumberValue("Delay", 0.0D, 0.0D, 1000.0D);
		
		this.addValue(delay);
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(timer.isDelay(delay.getValue().longValue())) {
			Wrapper.INSTANCE.sendPacket(new CPacketPlayer.Rotation(Utils.random(-160, 160), Utils.random(-160, 160), true));
			timer.setLastMS();
		}
		super.onClientTick(event);
	}
	
//	@Override // TODO Added camera fix
//	public void onCameraSetup(CameraSetup event) {
//		// TODO Auto-generated method stub
//		super.onCameraSetup(event);
//	}
}
