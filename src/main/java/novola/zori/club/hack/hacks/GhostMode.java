package novola.zori.club.hack.hacks;

import novola.zori.club.Main;
import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.system.Connection;
import novola.zori.club.utils.system.Connection.Side;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.value.Mode;
import novola.zori.club.value.ModeValue;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;

public class GhostMode extends Hack{
	
	public static boolean enabled = false;
	
	public GhostMode() {
		super("GhostMode", HackCategory.ANOTHER);
	}
	
	@Override
	public String getDescription() {
		return "Disable all hacks.";
	}
	
	@Override
	public void onEnable() {
		if(this.getKey() == -1) 
			return;
		enabled = true;
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		enabled = false;
		super.onDisable();
	}
	
}
