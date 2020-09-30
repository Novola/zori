package novola.zori.club.hack.hacks;

import java.lang.reflect.Field;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;

import novola.zori.club.utils.system.Mapping;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.wrappers.Wrapper;

public class Spider extends Hack{
	
	public Spider() {
		super("Spider", HackCategory.PLAYER);
	}
	
	@Override
	public String getDescription() {
		return "Allows you to climb up walls like a spider.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
        if(!Wrapper.INSTANCE.player().isOnLadder()
        		&& Wrapper.INSTANCE.player().collidedHorizontally 
        		&& Wrapper.INSTANCE.player().motionY < 0.2) {
        	Wrapper.INSTANCE.player().motionY = 0.2;
        }
		super.onClientTick(event);
	}
	
}
