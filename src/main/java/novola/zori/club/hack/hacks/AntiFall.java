package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;

import novola.zori.club.wrappers.Wrapper;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.wrappers.Wrapper;

public class AntiFall extends Hack{

	public AntiFall() {
		super("AntiFall", HackCategory.PLAYER);
	}
	
	@Override
	public String getDescription() {
		return "Gives you zero damage on fall.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) { 
		if(Wrapper.INSTANCE.player().fallDistance > 2)
			Wrapper.INSTANCE.sendPacket(new CPacketPlayer(true)); 
		super.onClientTick(event); 
	}
}
