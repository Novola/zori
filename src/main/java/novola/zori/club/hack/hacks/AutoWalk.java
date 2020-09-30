package novola.zori.club.hack.hacks;

import java.lang.reflect.Field;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.system.Mapping;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.wrappers.Wrapper;

public class AutoWalk extends Hack{
	
	public AutoWalk() {
		super("AutoWalk", HackCategory.PLAYER);
	}
	
	@Override
	public String getDescription() {
		return "Automatic walking.";
	}
	
	@Override
	public void onDisable() {
		KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindForward.getKeyCode(), false);
		super.onDisable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindForward.getKeyCode(), true);
		super.onClientTick(event);
	}
	
}
