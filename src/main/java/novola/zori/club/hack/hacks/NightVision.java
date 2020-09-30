package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.Utils;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.value.Mode;
import novola.zori.club.value.ModeValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.Utils;
import novola.zori.club.value.Mode;
import novola.zori.club.value.ModeValue;
import novola.zori.club.wrappers.Wrapper;

public class NightVision extends Hack{

	public ModeValue mode;
	
	public NightVision() {
		super("NightVision", HackCategory.VISUAL);
		
		this.mode = new ModeValue("Mode", new Mode("Brightness", true), new Mode("Effect", false));
		this.addValue(mode);
	}
	
	@Override
	public String getDescription() {
		return "Gets you night vision.";
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
	@Override
	public void onDisable() {
		if(this.mode.getMode("Brightness").isToggled())
			Wrapper.INSTANCE.mcSettings().gammaSetting = 1;
		else
			Utils.removeEffect(16);
		super.onDisable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(this.mode.getMode("Brightness").isToggled())
			Wrapper.INSTANCE.mcSettings().gammaSetting = 10;
		else
			Utils.addEffect(16, 1000, 3);
		super.onClientTick(event);
	}
}
