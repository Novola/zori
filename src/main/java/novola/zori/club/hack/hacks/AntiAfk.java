package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.TimerUtils;
import novola.zori.club.utils.Utils;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.TimerUtils;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;

public class AntiAfk extends Hack{
	
	public NumberValue delay;
	public TimerUtils timer;
	
	public AntiAfk() {
		super("AntiAfk", HackCategory.ANOTHER);
		
		this.timer = new TimerUtils();
		delay = new NumberValue("DelaySec", 10.0D, 1.0D, 100.0D);
		
		this.addValue(delay);
	}
	
	@Override
	public String getDescription() {
		return "Prevents from being kicked for AFK.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) { 
		if(timer.isDelay((long)(1000 * delay.getValue()))) {
			Wrapper.INSTANCE.player().jump();
			timer.setLastMS();
		}
		super.onClientTick(event); 
	}
}
