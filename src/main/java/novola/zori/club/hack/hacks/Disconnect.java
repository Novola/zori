package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;

import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;

public class Disconnect extends Hack{

	public NumberValue leaveHealth;
	
	public Disconnect() {
		super("Disconnect", HackCategory.COMBAT);
		
		leaveHealth = new NumberValue("LeaveHealth", 4.0D, 0D, 20D);
		
		this.addValue(leaveHealth);
	}
	
	@Override
	public String getDescription() {
		return "Automatically leaves the server when your health is low.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(Wrapper.INSTANCE.player().getHealth() <= leaveHealth.getValue().floatValue()) {
			
			boolean flag = Wrapper.INSTANCE.mc().isIntegratedServerRunning();
			Wrapper.INSTANCE.world().sendQuittingDisconnectingPacket();
			Wrapper.INSTANCE.mc().loadWorld((WorldClient)null);
			
            if (flag)
            	Wrapper.INSTANCE.mc().displayGuiScreen(new GuiMainMenu()); else
            	Wrapper.INSTANCE.mc().displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
            this.setToggled(false);
		}
		super.onClientTick(event);
	}
}
