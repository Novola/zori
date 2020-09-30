package novola.zori.club.hack.hacks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;

import novola.zori.club.utils.TimerUtils;
import novola.zori.club.utils.system.Connection.Side;
import novola.zori.club.utils.visual.ChatUtils;
import novola.zori.club.wrappers.Wrapper;
import joptsimple.internal.Strings;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.system.Connection;
import novola.zori.club.utils.visual.ChatUtils;
import novola.zori.club.wrappers.Wrapper;

public class PluginsGetter extends Hack{
    
	public PluginsGetter() {
		super("PluginsGetter", HackCategory.ANOTHER);
	}
	
	@Override
	public String getDescription() {
		return "Show all plugins on current server.";
	}
	
	@Override
	public void onEnable() {
		if(Wrapper.INSTANCE.player() == null) {
            return;
		}
        Wrapper.INSTANCE.sendPacket(new CPacketTabComplete("/", null, false));
		super.onEnable();
	}
	
	@Override
	public boolean onPacket(Object packet, Connection.Side side) {
		 if(packet instanceof SPacketTabComplete) {
	            SPacketTabComplete s3APacketTabComplete = (SPacketTabComplete) packet;
	 
	            List<String> plugins = new ArrayList<String>();
	            String[] commands = s3APacketTabComplete.getMatches();
	 
	            for(int i = 0; i < commands.length; i++) {
	                String[] command = commands[i].split(":");
	 
	                if(command.length > 1) {
	                    String pluginName = command[0].replace("/", "");
	 
	                    if(!plugins.contains(pluginName)) {
	                        plugins.add(pluginName);
	                    }
	                }
	            }
	            
	            Collections.sort(plugins);
	            
	            if(!plugins.isEmpty()) {
	                ChatUtils.message("Plugins \u00a77(\u00a78" + plugins.size() + "\u00a77): \u00a79" + Strings.join(plugins.toArray(new String[0]), "\u00a77, \u00a79"));
	            }
	            else
	            {
	                ChatUtils.error("No plugins found.");
	            }
	            this.setToggled(false);   
	        }
		return true;
	}
}
