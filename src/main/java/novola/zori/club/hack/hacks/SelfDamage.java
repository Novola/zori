package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
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
import novola.zori.club.utils.Utils;
import novola.zori.club.value.NumberValue;

public class SelfDamage extends Hack{
	
	public NumberValue damage;
	
	public SelfDamage() {
		super("SelfDamage", HackCategory.COMBAT);
		
		damage = new NumberValue("Damage", 0.0625D, 0.0125D, 0.35D);
		
		this.addValue(damage);
	}
	
	@Override
	public String getDescription() {
		return "Deals damage to you (useful for bypassing AC).";
	}
	
	@Override
	public void onEnable() {
		Utils.selfDamage(damage.getValue().doubleValue());
		this.toggle();
		super.onEnable();
	}
}
